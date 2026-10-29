package codecrafters.controlador;

import codecrafters.modelo.Cliente;
import codecrafters.modelo.Datos;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Reserva;

import java.time.LocalDate;
import java.util.List;

public class Controlador {

    private final Datos datos;

    private final ClienteController clienteController;
    private final HotelController hotelController;
    private final HabitacionController habitacionController;
    private final ReservaController reservaController;

    public Controlador() {

        // Temporalmente, Datos sigue siendo utilizado
        // por habitaciones y reservas del Producto 2.
        this.datos = new Datos();

        // Clientes y hoteles utilizan MySQL mediante DAO.
        this.clienteController = new ClienteController();
        this.hotelController = new HotelController();

        // Pendiente de integrar los DAO de Daniella.
        this.habitacionController = new HabitacionController(datos);
        this.reservaController = new ReservaController(datos);
    }

    public Datos getDatos() {
        return datos;
    }

    // CLIENTES

    public void agregarCliente(Cliente cliente) {
        clienteController.agregarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteController.listarClientes();
    }

    public List<Cliente> buscarClientes(String texto) {
        return clienteController.buscarClientes(texto);
    }

    // HOTELES

    public List<Hotel> listarHoteles() {
        return hotelController.listarHoteles();
    }

    public List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        return hotelController.buscarHotelesPorCiudad(ciudad);
    }

    // HABITACIONES
    // Pendiente de integración con MySQL.

    public List<Habitacion> listarHabitacionesPorHotel(Hotel hotel) {
        return habitacionController.listarHabitacionesPorHotel(hotel);
    }

    public List<Habitacion> filtrarHabitacionesPorCapacidad(
            Hotel hotel, int numeroPersonas) {

        return habitacionController.filtrarHabitacionesPorCapacidad(
                hotel, numeroPersonas);
    }

    public List<Habitacion> buscarHabitacionesDisponibles(
            Hotel hotel,
            LocalDate fechaEntrada,
            LocalDate fechaSalida,
            int numeroPersonas) {

        return habitacionController.buscarHabitacionesDisponibles(
                hotel, fechaEntrada, fechaSalida, numeroPersonas);
    }

    // RESERVAS
    // Pendiente de integración con MySQL.

    public void agregarReserva(Reserva reserva) {
        reservaController.agregarReserva(reserva);
    }

    public List<Reserva> listarReservas() {
        return reservaController.listarReservas();
    }

    public List<Reserva> buscarReservasPorCliente(Cliente cliente) {
        return reservaController.buscarReservasPorCliente(cliente);
    }

    public List<Reserva> buscarReservasPorHotel(Hotel hotel) {
        return reservaController.buscarReservasPorHotel(hotel);
    }

    public boolean cancelarReserva(int idReserva) {
        return reservaController.cancelarReserva(idReserva);
    }
}