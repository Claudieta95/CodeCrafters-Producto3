package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.Cliente;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Reserva;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReservasView {

    public static void listarReservas(Controlador controlador) {
        List<Reserva> reservas = controlador.listarReservas();

        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            for (Reserva r : reservas) {
                System.out.println(r);
            }
        }
    }
    public static void cancelarReserva(Scanner scanner, Controlador controlador) {
    System.out.print("Introduce el ID de la reserva a cancelar: ");

    if (scanner.hasNextInt()) {
        int idReserva = scanner.nextInt();
        scanner.nextLine();

        boolean cancelada = controlador.cancelarReserva(idReserva);

        if (cancelada) {
            System.out.println("Reserva cancelada con éxito.");
        } else {
            System.out.println("No se encontró ninguna reserva con ese ID.");
        }
    } else {
        System.out.println("ID no válido.");
        scanner.nextLine();
    }
}
public static void crearReserva(Scanner scanner, Controlador controlador) {

    Hotel hotel = GestionHotel.seleccionarHotel(scanner, controlador);

    if (hotel == null) {
        return;
    }

    List<Cliente> clientes = controlador.listarClientes();

    if (clientes.isEmpty()) {
        System.out.println("Primero debes registrar un cliente.");
        return;
    }

    Cliente cliente = clientes.get(0);

    System.out.print("Fecha de entrada (AAAA-MM-DD): ");
    LocalDate entrada = LocalDate.parse(scanner.nextLine());

    System.out.print("Fecha de salida (AAAA-MM-DD): ");
    LocalDate salida = LocalDate.parse(scanner.nextLine()); 

    try {
        System.out.print("Número de personas: ");
        int numPersonas = scanner.nextInt();
        scanner.nextLine();

        List<Habitacion> disponibles =
            controlador.buscarHabitacionesDisponibles(hotel, entrada, salida, numPersonas);

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles para esas fechas y capacidad.");
        } else {
            System.out.println("Habitaciones disponibles:");

            for (Habitacion h : disponibles) {
                System.out.println(h);
            }

            System.out.print("Selecciona el ID de la habitación que deseas reservar: ");
            int idHabitacion = scanner.nextInt();
            scanner.nextLine();

            Habitacion habitacionSeleccionada = null;

            for (Habitacion h : disponibles) {
                if (h.getIdHabitacion() == idHabitacion) {
                    habitacionSeleccionada = h;
                    break;
                }
            }
           

            if (habitacionSeleccionada == null) {
                System.out.println("ID de habitación no válido o no disponible.");
            } else {
                // Generar el ID de la reserva
                int idReserva = controlador.listarReservas().size() + 1;

                // Crear la reserva
                Reserva nuevaReserva = new Reserva(
                    idReserva, entrada, salida, numPersonas, "Activa");

                nuevaReserva.setCliente(cliente);

                // Añadir la habitación seleccionada
                nuevaReserva.agregarLineaReserva(
                    habitacionSeleccionada,
                    habitacionSeleccionada.getPrecioNoche());

                // Guardar la reserva
                controlador.agregarReserva(nuevaReserva);

                System.out.println(
                        "¡Reserva creada con éxito! Importe total: "
                        + nuevaReserva.getImporte() + " €");
            }
        }
    

    } catch (Exception e) {
        System.out.println("Error al procesar la reserva: " + e.getMessage());
    }

}
}