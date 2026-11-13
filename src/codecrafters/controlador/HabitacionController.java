package codecrafters.controlador;

import codecrafters.dao.DAOFactory;
import codecrafters.dao.HabitacionDAO;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import java.time.LocalDate;
import java.util.List;

public class HabitacionController {

    private final HabitacionDAO habitacionDAO;

    public HabitacionController() {
        this.habitacionDAO = DAOFactory.crearHabitacionDAO();
    }

    // Opción 6: listar habitaciones de un hotel
    public List<Habitacion> listarHabitacionesPorHotel(Hotel hotel) {

        if (hotel == null) {
            throw new IllegalArgumentException("El hotel no puede ser nulo.");
        }

        return habitacionDAO.listarPorHotel(hotel.getIdHotel());
    }

    // Opción 7: filtrar habitaciones por capacidad
    public List<Habitacion> filtrarHabitacionesPorCapacidad(
            Hotel hotel, int numeroPersonas) {

        if (hotel == null) {
            throw new IllegalArgumentException("El hotel no puede ser nulo.");
        }

        if (numeroPersonas <= 0) {
            throw new IllegalArgumentException(
                "El número de personas debe ser mayor que cero."
            );
        }

        return habitacionDAO.buscarPorCapacidad(
            hotel.getIdHotel(), numeroPersonas
        );
    }

    // La disponibilidad se implementará al integrar las reservas con JDBC.
    public List<Habitacion> buscarHabitacionesDisponibles(
            Hotel hotel,
            LocalDate fechaEntrada,
            LocalDate fechaSalida,
            int numeroPersonas) {

        throw new UnsupportedOperationException(
            "La búsqueda de disponibilidad mediante MySQL está pendiente."
        );
    }
}