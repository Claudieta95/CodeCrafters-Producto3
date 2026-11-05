package codecrafters.dao;

import codecrafters.modelo.Habitacion;
import java.util.List;

public interface HabitacionDAO {

    List<Habitacion> listarPorHotel(int idHotel);
    List<Habitacion> buscarPorCapacidad(int idHotel, int numeroPersonas);
} 