
package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.Cliente;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Reserva;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ReservasView {

    // -----------------------------------------
    // LISTAR RESERVAS
    // -----------------------------------------

    public static void listarReservas(Controlador controlador) {

        try {
            List<Reserva> reservas = controlador.listarReservas();

            if (reservas.isEmpty()) {
                System.out.println("No hay reservas registradas.");
                return;
            }

            System.out.println("\n--- LISTADO DE RESERVAS ---");

            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }

        } catch (RuntimeException e) {
            System.out.println("Error al consultar las reservas: "
                    + e.getMessage());
        }
    }

    // -----------------------------------------
    // CANCELAR RESERVA
    // -----------------------------------------

    public static void cancelarReserva(
            Scanner scanner, Controlador controlador) {

        System.out.print("Introduce el ID de la reserva a cancelar: ");

        if (!scanner.hasNextInt()) {
            System.out.println("ID no válido.");
            scanner.nextLine();
            return;
        }

        int idReserva = scanner.nextInt();
        scanner.nextLine();

        try {
            boolean cancelada = controlador.cancelarReserva(idReserva);

            if (cancelada) {
                System.out.println("Reserva cancelada con éxito.");
            } else {
                System.out.println(
                    "No se encontró la reserva o ya estaba cancelada."
                );
            }

        } catch (RuntimeException e) {
            System.out.println("Error al cancelar la reserva: "
                    + e.getMessage());
        }
    }

    // -----------------------------------------
    // CREAR RESERVA
    // -----------------------------------------

    public static void crearReserva(
            Scanner scanner, Controlador controlador) {

        System.out.println("\n--- CREAR RESERVA ---");

        // 1. Seleccionar hotel.

        Hotel hotel = GestionHotel.seleccionarHotel(
            scanner, controlador
        );

        if (hotel == null) {
            return;
        }

        // 2. Seleccionar cliente.

        List<Cliente> clientes = controlador.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println(
                "Primero debes registrar un cliente."
            );
            return;
        }

        System.out.println("\n--- SELECCIONAR CLIENTE ---");

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);

            System.out.println(
                (i + 1) + ". "
                + cliente.getNombre() + " "
                + cliente.getApellido()
                + " (ID: " + cliente.getIdPersona() + ")"
            );
        }

        System.out.print("Selecciona el número del cliente: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Selección no válida.");
            scanner.nextLine();
            return;
        }

        int seleccionCliente = scanner.nextInt();
        scanner.nextLine();

        if (seleccionCliente < 1
                || seleccionCliente > clientes.size()) {
            System.out.println("Cliente no válido.");
            return;
        }

        Cliente cliente = clientes.get(seleccionCliente - 1);

        // 3. Introducir fechas.

        LocalDate entrada;
        LocalDate salida;

        try {
            System.out.print("Fecha de entrada (AAAA-MM-DD): ");
            entrada = LocalDate.parse(scanner.nextLine());

            System.out.print("Fecha de salida (AAAA-MM-DD): ");
            salida = LocalDate.parse(scanner.nextLine());

            if (!salida.isAfter(entrada)) {
                System.out.println(
                    "La fecha de salida debe ser posterior a la entrada."
                );
                return;
            }

        } catch (DateTimeParseException e) {
            System.out.println(
                "Formato de fecha incorrecto. Utiliza AAAA-MM-DD."
            );
            return;
        }

        // 4. Introducir número de personas.

        System.out.print("Número de personas: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Número de personas no válido.");
            scanner.nextLine();
            return;
        }

        int numeroPersonas = scanner.nextInt();
        scanner.nextLine();

        if (numeroPersonas <= 0) {
            System.out.println(
                "El número de personas debe ser mayor que cero."
            );
            return;
        }

        // 5. Buscar habitaciones disponibles.

        try {
            List<Habitacion> disponibles =
                controlador.buscarHabitacionesDisponibles(
                    hotel, entrada, salida, numeroPersonas
                );

            if (disponibles.isEmpty()) {
                System.out.println(
                    "No hay habitaciones disponibles para esas fechas."
                );
                return;
            }

            System.out.println("\n--- HABITACIONES DISPONIBLES ---");

            for (Habitacion habitacion : disponibles) {
                System.out.println(habitacion);
            }

            // 6. Seleccionar habitación.

            System.out.print(
                "Introduce el ID de la habitación que deseas reservar: "
            );

            if (!scanner.hasNextInt()) {
                System.out.println("ID de habitación no válido.");
                scanner.nextLine();
                return;
            }

            int idHabitacion = scanner.nextInt();
            scanner.nextLine();

            Habitacion seleccionada = null;

            for (Habitacion habitacion : disponibles) {
                if (habitacion.getIdHabitacion() == idHabitacion) {
                    seleccionada = habitacion;
                    break;
                }
            }

            if (seleccionada == null) {
                System.out.println(
                    "La habitación seleccionada no está disponible."
                );
                return;
            }

            // 7. Crear la reserva.
            // El ID se genera automáticamente en MySQL.

            Reserva nuevaReserva = new Reserva(
                0,
                entrada,
                salida,
                numeroPersonas,
                "Confirmada"
            );

            nuevaReserva.setCliente(cliente);

            nuevaReserva.agregarLineaReserva(
                seleccionada,
                seleccionada.getPrecioNoche()
            );

            // 8. Guardar en MySQL.

            controlador.agregarReserva(nuevaReserva);

            System.out.println("\nReserva creada correctamente.");
            System.out.println("ID de reserva: "
                    + nuevaReserva.getIdReserva());
            System.out.println("Cliente: "
                    + cliente.getNombre() + " "
                    + cliente.getApellido());
            System.out.println("Hotel: " + hotel.getNombre());
            System.out.println("Habitación: "
                    + seleccionada.getNumero());
            System.out.println("Entrada: " + entrada);
            System.out.println("Salida: " + salida);
            System.out.println("Importe total: "
                    + nuevaReserva.getImporte() + " €");

        } catch (RuntimeException e) {
            System.out.println(
                "Error al procesar la reserva: " + e.getMessage()
            );
        }
    }
}
