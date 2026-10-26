package codecrafters.modelo;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Datos {

    private List<Cliente> clientes;
    private List<Hotel> hoteles;
    private List<Reserva> reservas;

    public Datos() {
        this.clientes = new ArrayList<>();
        this.hoteles = new ArrayList<>();
        this.reservas = new ArrayList<>();

        // Hoteles y habitaciones de ejemplo para probar el programa.
        List<Habitacion> habitacionesBarcelona = new ArrayList<>();
        habitacionesBarcelona.add(new Habitacion(1, 101, "Individual", 1, 60.0));
        habitacionesBarcelona.add(new Habitacion(2, 102, "Doble", 2, 90.0));

        List<Habitacion> habitacionesMadrid = new ArrayList<>();
        habitacionesMadrid.add(new Habitacion(3, 201, "Doble", 2, 85.0));
        habitacionesMadrid.add(new Habitacion(4, 202, "Familiar", 4, 140.0));

        agregarHotel(new Hotel(1, "Hotel Barcelona", "Barcelona", 4, habitacionesBarcelona));
        agregarHotel(new Hotel(2, "Hotel Madrid", "Madrid", 3, habitacionesMadrid));
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente != null) {
            clientes.add(cliente);
        }
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }

    public List<Cliente> buscarClientes(String texto) {
        List<Cliente> resultados = new ArrayList<>();
        if (texto == null) {
            return resultados;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(texto.toLowerCase())
                    || cliente.getApellido().toLowerCase().contains(texto.toLowerCase())) {
                resultados.add(cliente);
            }
        }
        return resultados;
    }

    public void agregarHotel(Hotel hotel) {
        if (hotel != null) {
            hoteles.add(hotel);
        }
    }

    public List<Hotel> listarHoteles() {
        return hoteles;
    }

    public List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        List<Hotel> resultados = new ArrayList<>();
        if (ciudad == null) {
            return resultados;
        }

        for (Hotel hotel : hoteles) {
            if (hotel.getCiudad().toLowerCase().contains(ciudad.toLowerCase())) {
                resultados.add(hotel);
            }
        }
        return resultados;
    }

    public List<Habitacion> listarHabitacionesPorHotel(Hotel hotel) {
        List<Habitacion> resultados = new ArrayList<>();

        if (hotel != null && hotel.getHabitaciones() != null) {
            resultados.addAll(hotel.getHabitaciones());
        }
        return resultados;
    }

    public List<Habitacion> filtrarHabitacionesPorCapacidad(Hotel hotel, int numeroPersonas) {
        if (numeroPersonas <= 0) {
            throw new CapacidadInvalidaException(
                    "El número de personas debe ser mayor que cero.");
        }

        List<Habitacion> resultados = new ArrayList<>();

        if (hotel == null) {
            return resultados;
        }

        for (Habitacion habitacion : listarHabitacionesPorHotel(hotel)) {
            if (habitacion.getCapacidad() >= numeroPersonas) {
                resultados.add(habitacion);
            }
        }
        return resultados;
    }

    public List<Habitacion> buscarHabitacionesDisponibles(
            Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida, int numeroPersonas) {

        List<Habitacion> resultados = new ArrayList<>();

        if (hotel == null || hotel.getHabitaciones() == null
                || fechaEntrada == null || fechaSalida == null
                || !fechaEntrada.isBefore(fechaSalida)) {
            return resultados;
        }

        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getCapacidad() >= numeroPersonas
                    && habitacion.estaDisponible(fechaEntrada, fechaSalida)) {
                resultados.add(habitacion);
            }
        }
        return resultados;
    }

    public void agregarReserva(Reserva reserva) {
        if (reserva == null || !reserva.validarFechas()) {
            throw new IllegalArgumentException("La reserva no tiene fechas válidas.");
        }

        if (reserva.getLineasReserva().isEmpty()) {
            throw new IllegalArgumentException(
                    "La reserva debe tener al menos una habitación.");
        }

        if (!reservas.contains(reserva)) {
            reservas.add(reserva);
            reserva.calcularImporte();
        }
    }

    public List<Reserva> listarReservas() {
        return reservas;
    }

    public List<Reserva> buscarReservasPorCliente(Cliente cliente) {
        List<Reserva> resultados = new ArrayList<>();

        for (Reserva reserva : reservas) {
            if (reserva.getCliente() == cliente) {
                resultados.add(reserva);
            }
        }
        return resultados;
    }

    public List<Reserva> buscarReservasPorHotel(Hotel hotel) {
        List<Reserva> resultados = new ArrayList<>();

        for (Reserva reserva : reservas) {
            for (LineaReserva linea : reserva.getLineasReserva()) {
                if (linea.getHabitacion().getHotel() == hotel) {
                    resultados.add(reserva);
                    break;
                }
            }
        }
        return resultados;
    }

    public boolean cancelarReserva(int idReserva) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == idReserva) {
                reserva.cancelar();
                return true;
            }
        }
        return false;
    }
}
