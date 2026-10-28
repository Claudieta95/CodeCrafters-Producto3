package codecrafters.controlador;

import codecrafters.modelo.Datos;
import codecrafters.modelo.Hotel;
import java.util.List;

public class HotelController {

    private Datos datos;

    public HotelController(Datos datos) {
        this.datos = datos;
    }

    public void agregarHotel(Hotel hotel) {
        datos.agregarHotel(hotel);
    }

    public List<Hotel> listarHoteles() {
        return datos.listarHoteles();
    }

    public List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        return datos.buscarHotelesPorCiudad(ciudad);
    }
}