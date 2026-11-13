package codecrafters.dao;

import java.util.List;

import codecrafters.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import codecrafters.config.ConexionBD;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();

        String sql = """
            SELECT p.id_persona, p.nombre, p.apellido,
                   p.dni, p.email, p.telefono
            FROM persona p
            INNER JOIN cliente c ON p.id_persona = c.id_persona
            ORDER BY p.id_persona
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Cliente cliente = new Cliente(
                    resultado.getInt("id_persona"),
                    resultado.getString("nombre"),
                    resultado.getString("apellido"),
                    resultado.getString("dni"),
                    resultado.getString("email"),
                    resultado.getString("telefono")
                );

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Error al listar los clientes", e);
        }

        return clientes;
    }

    @Override
    public void guardar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        String sqlPersona = """
            INSERT INTO persona (nombre, apellido, dni, email, telefono)
            VALUES (?, ?, ?, ?, ?)
            """;

        String sqlCliente = """
            INSERT INTO cliente (id_persona)
            VALUES (?)
            """;

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            conexion.setAutoCommit(false);

            try {
                int idGenerado;

                try (PreparedStatement sentencia = conexion.prepareStatement(
                        sqlPersona, java.sql.Statement.RETURN_GENERATED_KEYS)) {

                    sentencia.setString(1, cliente.getNombre());
                    sentencia.setString(2, cliente.getApellido());
                    sentencia.setString(3, cliente.getDni());
                    sentencia.setString(4, cliente.getEmail());
                    sentencia.setString(5, cliente.getTelefono());

                    sentencia.executeUpdate();

                    try (ResultSet claves = sentencia.getGeneratedKeys()) {
                        if (!claves.next()) {
                            throw new SQLException("No se ha generado el ID de la persona");
                        }

                        idGenerado = claves.getInt(1);
                    }
                }

            try (PreparedStatement sentencia = conexion.prepareStatement(sqlCliente)) {
                sentencia.setInt(1, idGenerado);
                sentencia.executeUpdate();
            }

            conexion.commit();
            cliente.setIdPersona(idGenerado);

        } catch (SQLException | RuntimeException e) {
            try {
                conexion.rollback();
            } catch (SQLException errorRollback) {
                e.addSuppressed(errorRollback);
            }

            throw e;
        }

    } catch (SQLException e) {
        throw new IllegalStateException("Error al guardar el cliente", e);
    }
}

    @Override
    public List<Cliente> buscar(String nombreOApellido) {
        List<Cliente> clientes = new ArrayList<>();

        String sql = """
            SELECT p.id_persona, p.nombre, p.apellido,
                   p.dni, p.email, p.telefono
            FROM persona p
            INNER JOIN cliente c ON p.id_persona = c.id_persona
            WHERE p.nombre LIKE ? OR p.apellido LIKE ?
            ORDER BY p.id_persona
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            String busqueda = "%" + nombreOApellido + "%";

            sentencia.setString(1, busqueda);
            sentencia.setString(2, busqueda);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    Cliente cliente = new Cliente(
                        resultado.getInt("id_persona"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("dni"),
                        resultado.getString("email"),
                        resultado.getString("telefono")
                    );

                    clientes.add(cliente);
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Error al buscar clientes", e);
        }

        return clientes;
    }

    @Override
    public void modificar(Cliente cliente) {
        if (cliente == null || cliente.getIdPersona() <= 0) {
            throw new IllegalArgumentException("El cliente debe tener un ID válido");
        }

        String sql = """
            UPDATE persona
            SET nombre = ?, apellido = ?, dni = ?, email = ?, telefono = ?
            WHERE id_persona = ?
              AND EXISTS (
                  SELECT 1 FROM cliente
                  WHERE cliente.id_persona = persona.id_persona
              )
              """;

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getApellido());
            sentencia.setString(3, cliente.getDni());
            sentencia.setString(4, cliente.getEmail());
            sentencia.setString(5, cliente.getTelefono());
            sentencia.setInt(6, cliente.getIdPersona());

            int filasModificadas = sentencia.executeUpdate();

            if (filasModificadas == 0) {
            throw new IllegalStateException("No se ha encontrado el cliente");
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Error al modificar el cliente", e);
        }
    }

    @Override
    public void eliminar(Cliente cliente) {
        if (cliente == null || cliente.getIdPersona() <= 0) {
            throw new IllegalArgumentException("El cliente debe tener un ID válido");
        }

        String sqlCliente = "DELETE FROM cliente WHERE id_persona = ?";
        String sqlPersona = "DELETE FROM persona WHERE id_persona = ?";

        try (Connection conexion = ConexionBD.obtenerConexion()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement eliminarCliente = conexion.prepareStatement(sqlCliente);
                PreparedStatement eliminarPersona = conexion.prepareStatement(sqlPersona)) {

                eliminarCliente.setInt(1, cliente.getIdPersona());

                int filasEliminadas = eliminarCliente.executeUpdate();

                if (filasEliminadas == 0) {
                    throw new IllegalStateException("No se ha encontrado el cliente");
                }

                eliminarPersona.setInt(1, cliente.getIdPersona());
                eliminarPersona.executeUpdate();

                conexion.commit();

            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                throw new IllegalStateException(
                    "No se puede eliminar el cliente porque tiene registros asociados", e);
            }

            throw new IllegalStateException("Error al eliminar el cliente", e);
        }
    }
    
}
