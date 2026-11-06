package codecrafters.controlador;

import codecrafters.dao.DAOFactory;
import codecrafters.dao.HotelDAO;
import codecrafters.modelo.Hotel;

import java.util.List;

public class HotelController {

    private final HotelDAO hotelDAO;

    public HotelController() {
        this.hotelDAO = DAOFactory.crearHotelDAO();
    }

    public List<Hotel> listarHoteles() {
        return hotelDAO.listarHoteles();
    }

    public List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        return hotelDAO.listarHotelesPorCiudad(ciudad);
    }
}