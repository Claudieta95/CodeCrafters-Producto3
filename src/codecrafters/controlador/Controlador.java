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

    public Controlador() {
        this.datos = new Datos();
    }

    // Obtener los datos de la aplicación
    public Datos getDatos() {
        return datos;
    }

    // Gestión de clientes
    public void agregarCliente(Cliente cliente) {
        datos.agregarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return datos.listarClientes();
    }

    public List<Cliente> buscarClientes(String texto) {
        return datos.buscarClientes(texto);
    }

    // Gestión de hoteles
    public void agregarHotel(Hotel hotel) {
        datos.agregarHotel(hotel);
    }

    public List<Hotel> listarHoteles() {
        return datos.listarHoteles();
    }

    public List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        return datos.buscarHotelesPorCiudad(ciudad);
    }

    // Gestión de habitaciones
    public List<Habitacion> listarHabitacionesPorHotel(Hotel hotel) {
        return datos.listarHabitacionesPorHotel(hotel);
    }

    public List<Habitacion> filtrarHabitacionesPorCapacidad(
            Hotel hotel, int numeroPersonas) {
        return datos.filtrarHabitacionesPorCapacidad(hotel, numeroPersonas);
    }

    public List<Habitacion> buscarHabitacionesDisponibles(
            Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida,
            int numeroPersonas) {

        return datos.buscarHabitacionesDisponibles(
                hotel, fechaEntrada, fechaSalida, numeroPersonas);
    }

    // Gestión de reservas
    public void agregarReserva(Reserva reserva) {
        datos.agregarReserva(reserva);
    }

    public List<Reserva> listarReservas() {
        return datos.listarReservas();
    }

    public List<Reserva> buscarReservasPorCliente(Cliente cliente) {
        return datos.buscarReservasPorCliente(cliente);
    }

    public List<Reserva> buscarReservasPorHotel(Hotel hotel) {
        return datos.buscarReservasPorHotel(hotel);
    }

    public boolean cancelarReserva(int idReserva) {
        return datos.cancelarReserva(idReserva);
    }
}
