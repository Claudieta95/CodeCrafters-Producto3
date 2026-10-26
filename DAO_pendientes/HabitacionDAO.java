import java.time.LocalDate;
import java.util.List;

public interface HabitacionDAO {
    List<Habitacion> buscarPorCategoria(Hotel hotel, String categoria);
    List<Habitacion> buscarDisponibles(LocalDate fechaEntrada, LocalDate fechaSalida);
    List<Habitacion> filtrarPorCapacidad(int capacidad);
}