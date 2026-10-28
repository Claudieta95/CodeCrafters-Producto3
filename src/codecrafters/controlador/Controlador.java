package codecrafters.controlador;

import codecrafters.modelo.Cliente;
import codecrafters.modelo.Datos;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Reserva;

import java.time.LocalDate;
import java.util.List;

public class Controlador {

    private Datos datos;

    private ClienteController clienteController;
    private HotelController hotelController;
    private HabitacionController habitacionController;
    private ReservaController reservaController;

    public Controlador() {
        this.datos = new Datos();

        this.clienteController = new ClienteController(datos);
        this.hotelController = new HotelController(datos);
        this.habitacionController = new HabitacionController(datos);
        this.reservaController = new ReservaController(datos);
    }

    // Obtener los datos de la aplicación
    public Datos getDatos() {
        return datos;
    }

    // Gestión de clientes
    public void agregarCliente(Cliente cliente) {
        clienteController.agregarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteController.listarClientes();
    }    

    public List<Cliente> buscarClientes(String texto) {
        return clienteController.buscarClientes(texto);
    }

    // Gestión de hoteles
    public void agregarHotel(Hotel hotel) {
        hotelController.agregarHotel(hotel);
    }

    public List<Hotel> listarHoteles() {
        return hotelController.listarHoteles();
    }

    public List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        return hotelController.buscarHotelesPorCiudad(ciudad);
    }

    // Gestión de habitaciones
    public List<Habitacion> listarHabitacionesPorHotel(Hotel hotel) {
        return habitacionController.listarHabitacionesPorHotel(hotel);
    }

    public List<Habitacion> filtrarHabitacionesPorCapacidad(
            Hotel hotel, int numeroPersonas) {
        return habitacionController.filtrarHabitacionesPorCapacidad(hotel, numeroPersonas);
    }

    public List<Habitacion> buscarHabitacionesDisponibles(
            Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida,
            int numeroPersonas) {

        return habitacionController.buscarHabitacionesDisponibles(
                hotel, fechaEntrada, fechaSalida, numeroPersonas);
    }

    // Gestión de reservas
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
