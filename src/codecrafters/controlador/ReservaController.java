
package codecrafters.controlador;

import codecrafters.dao.DAOFactory;
import codecrafters.dao.ReservaDAO;
import codecrafters.modelo.Cliente;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Reserva;
import java.time.LocalDate;
import java.util.List;

public class ReservaController {

    private final ReservaDAO reservaDAO;

    public ReservaController() {
        this.reservaDAO = DAOFactory.crearReservaDAO();
    }

    public void agregarReserva(Reserva reserva) {
        reservaDAO.guardar(reserva);
    }

    public List<Reserva> listarReservas() {
        return reservaDAO.listar();
    }

    public List<Reserva> buscarReservasPorCliente(Cliente cliente) {
        return reservaDAO.buscarPorCliente(cliente);
    }

    public List<Reserva> listarReservasPorCliente(int idCliente) {
        return reservaDAO.listarReservasPorCliente(idCliente);
    }

    public List<Reserva> buscarReservasPorHotel(Hotel hotel) {
        return reservaDAO.buscarPorHotel(hotel);
    }

    public List<Habitacion> buscarHabitacionesDisponibles(
            Hotel hotel,
            LocalDate entrada,
            LocalDate salida,
            int numeroPersonas) {

        if (hotel == null) {
            throw new IllegalArgumentException("El hotel no puede ser nulo.");
        }

        if (entrada == null || salida == null || !entrada.isBefore(salida)) {
            throw new IllegalArgumentException("Las fechas de la reserva no son válidas.");
        }

        if (numeroPersonas <= 0) {
            throw new IllegalArgumentException("El número de personas debe ser mayor que cero.");
        }

        return reservaDAO.buscarHabitacionesDisponibles(
                hotel.getIdHotel(), entrada, salida, numeroPersonas);
    }

    public List<Reserva> buscarPorFechaEntrada(LocalDate fecha) {
        return reservaDAO.buscarPorFechaEntrada(fecha);
    }

    public List<Reserva> buscarPorFechaSalida(LocalDate fecha) {
        return reservaDAO.buscarPorFechaSalida(fecha);
    }

    public List<Reserva> buscarPorEstado(String estado) {
        return reservaDAO.buscarPorEstado(estado);
    }

    public void modificarEstado(Reserva reserva, String nuevoEstado) {
        reservaDAO.modificar(reserva, nuevoEstado);
    }

    public boolean cancelarReserva(int idReserva) {
        return reservaDAO.cancelar(idReserva);
    }
}
