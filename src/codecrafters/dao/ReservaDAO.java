
package codecrafters.dao;

import codecrafters.modelo.Cliente;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Reserva;
import java.time.LocalDate;
import java.util.List;

public interface ReservaDAO {

    void guardar(Reserva reserva);

    List<Reserva> listar();

    List<Reserva> buscarPorCliente(Cliente cliente);

    List<Reserva> listarReservasPorCliente(int idCliente);

    List<Reserva> buscarPorHotel(Hotel hotel);

    List<Reserva> buscarPorFechaEntrada(LocalDate fecha);

    List<Reserva> buscarPorFechaSalida(LocalDate fecha);

    List<Reserva> buscarPorEstado(String estado);

    List<Habitacion> buscarHabitacionesDisponibles(
        int idHotel,
        LocalDate fechaEntrada,
        LocalDate fechaSalida,
        int numeroPersonas
    );

    void modificar(Reserva reserva, String nuevoEstado);

    boolean cancelar(int idReserva);
}
