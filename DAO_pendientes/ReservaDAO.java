import java.time.LocalDate;
import java.util.List;

public interface ReservaDAO {
    void guardar(Reserva reserva);
    List<Reserva> buscarPorCliente(Cliente cliente);
    List<Reserva> buscarPorHotel(Hotel hotel);
    List<Reserva> buscarPorFechaEntrada(LocalDate fecha);
    List<Reserva> buscarPorFechaSalida(LocalDate fecha);
    List<Reserva> buscarPorEstado(String estado);
    void modificar(Reserva reserva, String nuevoEstado);
    void cancelar(Reserva reserva);
}
