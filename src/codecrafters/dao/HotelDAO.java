package codecrafters.dao;

import codecrafters.modelo.Hotel;
import java.util.List;

public interface HotelDAO {
    List<Hotel> listarHabitaciones();
    List<Hotel> listarHoteles();
    List<Hotel> listarHotelesPorCiudad(String ciudad);
    List<Hotel> listarHotelesPorEstrellas(int estrellas);
}