package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.Hotel;
import java.util.List;
import java.util.Scanner;

public class HotelView {

    public static void listarHoteles(Controlador controlador) {
        List<Hotel> hoteles = controlador.listarHoteles();

        if (hoteles.isEmpty()) {
            System.out.println("No hay hoteles registrados.");
        } else {
            for (Hotel hotel : hoteles) {
                System.out.println(hotel);
            }
        }
    }

    public static void buscarHotelesPorCiudad(Scanner scanner, Controlador controlador) {
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();

        List<Hotel> hoteles = controlador.buscarHotelesPorCiudad(ciudad);

        if (hoteles.isEmpty()) {
            System.out.println("No se encontraron hoteles.");
        } else {
            for (Hotel hotel : hoteles) {
                System.out.println(hotel);
            }
        }
    }
}