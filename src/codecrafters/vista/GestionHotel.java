package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.Hotel;
import java.util.List;
import java.util.Scanner;

public class GestionHotel {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            Controlador controlador = new Controlador();
            int opcion;

            do {
                MenuView.mostrarMenu();

                if (!scanner.hasNextInt()) {
                    System.out.println("Introduce un número válido.");
                    scanner.nextLine();
                    opcion = 0;
                    continue;
                }

                opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {

                    case 1 -> ClienteView.registrarCliente(scanner, controlador);

                    case 2 -> ClienteView.listarClientes(controlador);

                    case 3 -> ClienteView.buscarClientes(scanner, controlador);

                    case 4 -> HotelView.listarHoteles(controlador);

                    case 5 -> HotelView.buscarHotelesPorCiudad(scanner, controlador);

                    case 6 -> HabitacionView.listarHabitaciones(scanner, controlador);

                    case 7 -> HabitacionView.filtrarHabitaciones(scanner, controlador);

                    case 8 -> ReservasView.crearReserva(scanner, controlador);

                    case 9 -> ReservasView.listarReservas(controlador);

                    case 10 -> ReservasView.cancelarReserva(scanner, controlador);

                    case 11 -> System.out.println("Saliendo del programa.");

                    default -> System.out.println("Opción no válida.");
                }

            } while (opcion != 11);
        }
    }

    public static Hotel seleccionarHotel(
        Scanner scanner, Controlador controlador) {

                        List<Hotel> hoteles = controlador.listarHoteles();

                        if (hoteles.isEmpty()) {
                            System.out.println("No hay hoteles registrados.");
                            return null;
                        }

                        for (int i = 0; i < hoteles.size(); i++) {
                            System.out.println((i + 1) + ". " + hoteles.get(i));
                        }

                        System.out.print("Selecciona el número del hotel: ");

                        if (!scanner.hasNextInt()) {
                            System.out.println("Introduce un número válido.");
                            scanner.nextLine();
                            return null;
                        }

                        int numero = scanner.nextInt();
                        scanner.nextLine();

                        if (numero < 1 || numero > hoteles.size()) {
                            System.out.println("Hotel no válido.");
                            return null;
                        }

                        return hoteles.get(numero - 1);
    }
}
