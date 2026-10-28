package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.CapacidadInvalidaException;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import java.util.List;
import java.util.Scanner;

public class HabitacionView {

    public static void listarHabitaciones(Scanner scanner, Controlador controlador) {
        Hotel hotel = GestionHotel.seleccionarHotel(scanner, controlador);

        if (hotel != null) {
            List<Habitacion> habitaciones =
                    controlador.listarHabitacionesPorHotel(hotel);

            if (habitaciones.isEmpty()) {
                System.out.println("El hotel no tiene habitaciones.");
            } else {
                for (Habitacion habitacion : habitaciones) {
                    System.out.println(habitacion);
                }
            }
        }
    }

    public static void filtrarHabitaciones(Scanner scanner, Controlador controlador) {
        Hotel hotel = GestionHotel.seleccionarHotel(scanner, controlador);

        if (hotel != null) {
            System.out.print("Número de personas: ");

            if (scanner.hasNextInt()) {
                int personas = scanner.nextInt();
                scanner.nextLine();

                try {
                    List<Habitacion> habitaciones =
                            controlador.filtrarHabitacionesPorCapacidad(hotel, personas);

                    if (habitaciones.isEmpty()) {
                        System.out.println("No hay habitaciones con esa capacidad.");
                    } else {
                        for (Habitacion habitacion : habitaciones) {
                            System.out.println(habitacion);
                        }
                    }
                } catch (CapacidadInvalidaException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else {
                System.out.println("Introduce un número válido.");
                scanner.nextLine();
            }
        }
    }
}