package codecrafters.dao;

import codecrafters.modelo.Hotel;

public class PruebaHotelDAO {

    public static void main(String[] args) {

        HotelDAO dao = DAOFactory.crearHotelDAO();

        System.out.println("=== TODOS LOS HOTELES ===");
        for (Hotel hotel : dao.listarHoteles()) {
            System.out.println(hotel);
        }

        System.out.println("\n=== HOTELES EN BARCELONA ===");
        for (Hotel hotel : dao.listarHotelesPorCiudad("Barcelona")) {
            System.out.println(hotel);
        }

        System.out.println("\n=== HOTELES DE 4 ESTRELLAS ===");
        for (Hotel hotel : dao.listarHotelesPorEstrellas(4)) {
            System.out.println(hotel);
        }
    }
}