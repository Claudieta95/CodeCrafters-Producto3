package codecrafters.dao;

import codecrafters.config.ConexionBD;
import codecrafters.modelo.Cliente;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.LineaReserva;
import codecrafters.modelo.Reserva;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ReservaDAOImpl implements ReservaDAO {

    // --------------------------------------------------
    // GUARDAR UNA RESERVA
    // --------------------------------------------------

    @Override
    public void guardar(Reserva reserva) {

        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula.");
        }

        if (reserva.getFechaEntrada() == null
                || reserva.getFechaSalida() == null
                || !reserva.getFechaSalida().isAfter(reserva.getFechaEntrada())) {
            throw new IllegalArgumentException(
                "La fecha de salida debe ser posterior a la fecha de entrada."
            );
        }

        if (reserva.getNumeroPersonas() <= 0) {
            throw new IllegalArgumentException(
                "El número de personas debe ser mayor que cero."
            );
        }

        if (reserva.getCliente() == null
                || reserva.getCliente().getIdPersona() <= 0) {
            throw new IllegalArgumentException(
                "Debes seleccionar un cliente válido."
            );
        }

        if (reserva.getLineasReserva() == null
                || reserva.getLineasReserva().isEmpty()) {
            throw new IllegalArgumentException(
                "La reserva debe contener al menos una habitación."
            );
        }

        String sqlBloquearHabitacion = """
            SELECT capacidad, precio_noche
            FROM habitacion
            WHERE id_habitacion = ?
            FOR UPDATE
            """;

        String sqlDisponibilidad = """
            SELECT COUNT(*)
            FROM linea_reserva lr
            INNER JOIN reserva r
                ON lr.id_reserva = r.id_reserva
            WHERE lr.id_habitacion = ?
              AND r.estado <> 'Cancelada'
              AND r.fecha_entrada < ?
              AND r.fecha_salida > ?
            """;

        String sqlInsertarReserva = """
            INSERT INTO reserva
                (fecha_entrada, fecha_salida, numero_personas,
                 estado, importe, id_cliente)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        String sqlInsertarLinea = """
            INSERT INTO linea_reserva
                (id_reserva, id_habitacion, precio_noche)
            VALUES (?, ?, ?)
            """;

       try (Connection conexion = ConexionBD.obtenerConexion()) {
        conexion.setTransactionIsolation(
        Connection.TRANSACTION_READ_COMMITTED
    );

    conexion.setAutoCommit(false);

            try {
                // Comprobar que el cliente existe.
                comprobarCliente(conexion, reserva.getCliente().getIdPersona());

                // Ordenar los IDs para reducir el riesgo de bloqueos cruzados.
                TreeSet<Integer> idsHabitaciones = new TreeSet<>();

                for (LineaReserva linea : reserva.getLineasReserva()) {
                    if (linea == null || linea.getHabitacion() == null
                            || linea.getHabitacion().getIdHabitacion() <= 0) {
                        throw new IllegalArgumentException(
                            "La reserva contiene una habitación no válida."
                        );
                    }

                    idsHabitaciones.add(
                        linea.getHabitacion().getIdHabitacion()
                    );
                }

                // Bloquear las habitaciones y comprobar su disponibilidad.
                try (PreparedStatement bloquear =
                        conexion.prepareStatement(sqlBloquearHabitacion);
                     PreparedStatement disponibilidad =
                        conexion.prepareStatement(sqlDisponibilidad)) {

                    for (int idHabitacion : idsHabitaciones) {

                        bloquear.setInt(1, idHabitacion);

                        int capacidad;
                        double precioNoche;

                        try (ResultSet resultado = bloquear.executeQuery()) {

                            if (!resultado.next()) {
                                throw new IllegalArgumentException(
                                    "No existe la habitación con ID "
                                    + idHabitacion
                                );
                            }

                            capacidad = resultado.getInt("capacidad");
                            precioNoche = resultado.getDouble("precio_noche");
                        }

                        if (capacidad < reserva.getNumeroPersonas()) {
                            throw new IllegalArgumentException(
                                "La habitación " + idHabitacion
                                + " no tiene capacidad suficiente."
                            );
                        }

                        disponibilidad.setInt(1, idHabitacion);
                        disponibilidad.setDate(
                            2, Date.valueOf(reserva.getFechaSalida())
                        );
                        disponibilidad.setDate(
                            3, Date.valueOf(reserva.getFechaEntrada())
                        );

                        try (ResultSet resultado =
                                disponibilidad.executeQuery()) {

                            resultado.next();

                            if (resultado.getInt(1) > 0) {
                                throw new IllegalStateException(
                                    "La habitación " + idHabitacion
                                    + " ya está reservada para esas fechas."
                                );
                            }
                        }

                        // Utilizar el precio actual de la base de datos.
                        for (LineaReserva linea : reserva.getLineasReserva()) {
                            if (linea.getHabitacion().getIdHabitacion()
                                    == idHabitacion) {
                                linea.setPrecioNoche(precioNoche);
                            }
                        }
                    }
                }

                // Calcular el importe con los precios recuperados de MySQL.
                reserva.calcularImporte();

                // Insertar la reserva y recuperar su ID autogenerado.
                try (PreparedStatement insertar =
                        conexion.prepareStatement(
                            sqlInsertarReserva,
                            Statement.RETURN_GENERATED_KEYS
                        )) {

                    insertar.setDate(
                        1, Date.valueOf(reserva.getFechaEntrada())
                    );
                    insertar.setDate(
                        2, Date.valueOf(reserva.getFechaSalida())
                    );
                    insertar.setInt(3, reserva.getNumeroPersonas());

                    String estado = reserva.getEstado();
                    insertar.setString(
                        4, estado == null ? "Confirmada" : estado
                    );

                    insertar.setDouble(5, reserva.getImporte());
                    insertar.setInt(
                        6, reserva.getCliente().getIdPersona()
                    );

                    insertar.executeUpdate();

                    try (ResultSet claves = insertar.getGeneratedKeys()) {

                        if (!claves.next()) {
                            throw new SQLException(
                                "No se pudo obtener el ID de la reserva."
                            );
                        }

                        reserva.setIdReserva(claves.getInt(1));
                    }
                }

                // Insertar las habitaciones de la reserva.
                try (PreparedStatement insertarLinea =
                        conexion.prepareStatement(sqlInsertarLinea)) {

                    for (LineaReserva linea : reserva.getLineasReserva()) {

                        insertarLinea.setInt(1, reserva.getIdReserva());
                        insertarLinea.setInt(
                            2, linea.getHabitacion().getIdHabitacion()
                        );
                        insertarLinea.setDouble(
                            3, linea.getPrecioNoche()
                        );

                        insertarLinea.executeUpdate();
                    }
                }

                conexion.commit();

            } catch (Exception e) {

                conexion.rollback();

                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }

                throw new IllegalStateException(
                    "Error al guardar la reserva.", e
                );

            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error de conexión al guardar la reserva.", e
            );
        }
    }

    // --------------------------------------------------
    // COMPROBAR QUE EXISTE EL CLIENTE
    // --------------------------------------------------

    private void comprobarCliente(Connection conexion, int idCliente)
            throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM cliente
            WHERE id_persona = ?
            """;

        try (PreparedStatement sentencia =
                conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idCliente);

            try (ResultSet resultado = sentencia.executeQuery()) {
                resultado.next();

                if (resultado.getInt(1) == 0) {
                    throw new IllegalArgumentException(
                        "El cliente seleccionado no existe."
                    );
                }
            }
        }
    }

    // --------------------------------------------------
    // LISTAR TODAS LAS RESERVAS
    // --------------------------------------------------

    @Override
    public List<Reserva> listar() {

        List<Integer> ids = new ArrayList<>();

        String sql = """
            SELECT id_reserva
            FROM reserva
            ORDER BY id_reserva
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                ids.add(resultado.getInt("id_reserva"));
            }

            List<Reserva> reservas = new ArrayList<>();

            for (int id : ids) {
                reservas.add(cargarReserva(conexion, id));
            }

            return reservas;

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al listar las reservas.", e
            );
        }
    }

    // --------------------------------------------------
    // BUSCAR RESERVAS POR CLIENTE
    // --------------------------------------------------

    @Override
    public List<Reserva> buscarPorCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }

        return listarReservasPorCliente(cliente.getIdPersona());
    }

    @Override
    public List<Reserva> listarReservasPorCliente(int idCliente) {

        return buscarPorFiltro(
            "SELECT id_reserva FROM reserva WHERE id_cliente = ? ORDER BY id_reserva",
            idCliente
        );
    }

    // --------------------------------------------------
    // BUSCAR RESERVAS POR HOTEL
    // --------------------------------------------------

    @Override
    public List<Reserva> buscarPorHotel(Hotel hotel) {

        if (hotel == null) {
            throw new IllegalArgumentException("El hotel no puede ser nulo.");
        }

        List<Integer> ids = new ArrayList<>();

        String sql = """
            SELECT DISTINCT r.id_reserva
            FROM reserva r
            INNER JOIN linea_reserva lr
                ON r.id_reserva = lr.id_reserva
            INNER JOIN habitacion h
                ON lr.id_habitacion = h.id_habitacion
            WHERE h.id_hotel = ?
            ORDER BY r.id_reserva
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, hotel.getIdHotel());

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    ids.add(resultado.getInt("id_reserva"));
                }
            }

            return cargarVariasReservas(conexion, ids);

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al buscar reservas por hotel.", e
            );
        }
    }

    // --------------------------------------------------
    // BUSCAR POR FECHA DE ENTRADA
    // --------------------------------------------------

    @Override
    public List<Reserva> buscarPorFechaEntrada(LocalDate fecha) {

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        return buscarPorFecha(
            "fecha_entrada", fecha
        );
    }

    // --------------------------------------------------
    // BUSCAR POR FECHA DE SALIDA
    // --------------------------------------------------

    @Override
    public List<Reserva> buscarPorFechaSalida(LocalDate fecha) {

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        return buscarPorFecha(
            "fecha_salida", fecha
        );
    }

    private List<Reserva> buscarPorFecha(
            String columna, LocalDate fecha) {

        if (!columna.equals("fecha_entrada")
                && !columna.equals("fecha_salida")) {
            throw new IllegalArgumentException("Columna no válida.");
        }

        List<Integer> ids = new ArrayList<>();

        String sql = "SELECT id_reserva FROM reserva WHERE "
                + columna + " = ? ORDER BY id_reserva";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setDate(1, Date.valueOf(fecha));

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    ids.add(resultado.getInt("id_reserva"));
                }
            }

            return cargarVariasReservas(conexion, ids);

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al buscar reservas por fecha.", e
            );
        }
    }

    // --------------------------------------------------
    // BUSCAR POR ESTADO
    // --------------------------------------------------

    @Override
    public List<Reserva> buscarPorEstado(String estado) {

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }

        List<Integer> ids = new ArrayList<>();

        String sql = """
            SELECT id_reserva
            FROM reserva
            WHERE estado = ?
            ORDER BY id_reserva
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, estado);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    ids.add(resultado.getInt("id_reserva"));
                }
            }

            return cargarVariasReservas(conexion, ids);

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al buscar reservas por estado.", e
            );
        }
    }

    // --------------------------------------------------
    // BUSCAR HABITACIONES DISPONIBLES
    // --------------------------------------------------

    @Override
    public List<Habitacion> buscarHabitacionesDisponibles(
            int idHotel,
            LocalDate fechaEntrada,
            LocalDate fechaSalida,
            int numeroPersonas) {

        if (idHotel <= 0 || numeroPersonas <= 0
                || fechaEntrada == null || fechaSalida == null
                || !fechaSalida.isAfter(fechaEntrada)) {
            throw new IllegalArgumentException(
                "Los datos de búsqueda de disponibilidad no son válidos."
            );
        }

        List<Habitacion> habitaciones = new ArrayList<>();

        String sql = """
            SELECT h.id_habitacion, h.numero, h.categoria,
                   h.capacidad, h.precio_noche
            FROM habitacion h
            WHERE h.id_hotel = ?
              AND h.capacidad >= ?
              AND NOT EXISTS (
                  SELECT 1
                  FROM linea_reserva lr
                  INNER JOIN reserva r
                      ON lr.id_reserva = r.id_reserva
                  WHERE lr.id_habitacion = h.id_habitacion
                    AND r.estado <> 'Cancelada'
                    AND r.fecha_entrada < ?
                    AND r.fecha_salida > ?
              )
            ORDER BY h.precio_noche, h.numero
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idHotel);
            sentencia.setInt(2, numeroPersonas);
            sentencia.setDate(3, Date.valueOf(fechaSalida));
            sentencia.setDate(4, Date.valueOf(fechaEntrada));

            try (ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {

                    habitaciones.add(new Habitacion(
                        resultado.getInt("id_habitacion"),
                        resultado.getInt("numero"),
                        resultado.getString("categoria"),
                        resultado.getInt("capacidad"),
                        resultado.getDouble("precio_noche")
                    ));
                }
            }

            return habitaciones;

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al consultar habitaciones disponibles.", e
            );
        }
    }

    // --------------------------------------------------
    // MODIFICAR ESTADO
    // --------------------------------------------------

    @Override
    public void modificar(Reserva reserva, String nuevoEstado) {

        if (reserva == null || nuevoEstado == null
                || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException(
                "La reserva y el nuevo estado son obligatorios."
            );
        }

        String sql = """
            UPDATE reserva
            SET estado = ?
            WHERE id_reserva = ?
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, nuevoEstado);
            sentencia.setInt(2, reserva.getIdReserva());

            if (sentencia.executeUpdate() == 0) {
                throw new IllegalArgumentException(
                    "No existe la reserva indicada."
                );
            }

            reserva.setEstado(nuevoEstado);

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al modificar la reserva.", e
            );
        }
    }

    // --------------------------------------------------
    // CANCELAR RESERVA
    // --------------------------------------------------

    
@Override
public boolean cancelar(int idReserva) {
   String sql = "{CALL cancelar_reserva(?, ?)}";

    try (Connection conexion = ConexionBD.obtenerConexion()) {

        conexion.setAutoCommit(false);

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setInt(1, idReserva);
            cs.registerOutParameter(2, java.sql.Types.INTEGER);

            cs.execute();

            int filasAfectadas = cs.getInt(2);

            conexion.commit();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            conexion.rollback();
            throw new RuntimeException(
                "Error al cancelar la reserva: " + e.getMessage(), e
            );
        }

    } catch (SQLException e) {
        throw new RuntimeException(
            "Error de conexión al cancelar la reserva: " + e.getMessage(), e
        );
    }
}


    // --------------------------------------------------
    // MÉTODOS AUXILIARES PARA CONSULTAS
    // --------------------------------------------------

    private List<Reserva> buscarPorFiltro(String sql, int idCliente) {

        if (idCliente <= 0) {
            throw new IllegalArgumentException("El ID del cliente no es válido.");
        }

        List<Integer> ids = new ArrayList<>();

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idCliente);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    ids.add(resultado.getInt("id_reserva"));
                }
            }

            return cargarVariasReservas(conexion, ids);

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al buscar reservas del cliente.", e
            );
        }
    }

    private List<Reserva> cargarVariasReservas(
            Connection conexion, List<Integer> ids) throws SQLException {

        List<Reserva> reservas = new ArrayList<>();

        for (int id : ids) {
            reservas.add(cargarReserva(conexion, id));
        }

        return reservas;
    }

    private Reserva cargarReserva(Connection conexion, int idReserva)
            throws SQLException {

        String sqlReserva = """
            SELECT r.id_reserva, r.fecha_entrada, r.fecha_salida,
                   r.numero_personas, r.estado, r.importe,
                   p.id_persona, p.nombre, p.apellido,
                   p.dni, p.email, p.telefono
            FROM reserva r
            INNER JOIN cliente c
                ON r.id_cliente = c.id_persona
            INNER JOIN persona p
                ON c.id_persona = p.id_persona
            WHERE r.id_reserva = ?
            """;

        Reserva reserva;

        try (PreparedStatement sentencia =
                conexion.prepareStatement(sqlReserva)) {

            sentencia.setInt(1, idReserva);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (!resultado.next()) {
                    throw new SQLException(
                        "No se encontró la reserva " + idReserva
                    );
                }

                reserva = new Reserva(
                    resultado.getInt("id_reserva"),
                    resultado.getDate("fecha_entrada").toLocalDate(),
                    resultado.getDate("fecha_salida").toLocalDate(),
                    resultado.getInt("numero_personas"),
                    resultado.getString("estado")
                );

                reserva.setImporte(resultado.getDouble("importe"));

                Cliente cliente = new Cliente(
                    resultado.getInt("id_persona"),
                    resultado.getString("nombre"),
                    resultado.getString("apellido"),
                    resultado.getString("dni"),
                    resultado.getString("email"),
                    resultado.getString("telefono")
                );

                reserva.setCliente(cliente);
            }
        }

        String sqlLineas = """
            SELECT h.id_habitacion, h.numero, h.categoria,
                   h.capacidad, lr.precio_noche
            FROM linea_reserva lr
            INNER JOIN habitacion h
                ON lr.id_habitacion = h.id_habitacion
            WHERE lr.id_reserva = ?
            """;

        try (PreparedStatement sentencia =
                conexion.prepareStatement(sqlLineas)) {

            sentencia.setInt(1, idReserva);

            try (ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {

                    Habitacion habitacion = new Habitacion(
                        resultado.getInt("id_habitacion"),
                        resultado.getInt("numero"),
                        resultado.getString("categoria"),
                        resultado.getInt("capacidad"),
                        resultado.getDouble("precio_noche")
                    );

                    reserva.agregarLineaReserva(
                        habitacion,
                        resultado.getDouble("precio_noche")
                    );
                }
            }
        }

        return reserva;
    }
}
