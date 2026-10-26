package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.CapacidadInvalidaException;
import codecrafters.modelo.Cliente;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Reserva;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GestionHotel {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            Controlador controlador = new Controlador();
            int opcion;

            do {
                System.out.println("\n--- GESTIÓN DE HOTEL ---");
                System.out.println("1. Registrar nuevo cliente");
                System.out.println("2. Listar clientes");
                System.out.println("3. Buscar cliente por nombre o apellido");
                System.out.println("4. Listar hoteles");
                System.out.println("5. Buscar hoteles por ciudad");
                System.out.println("6. Listar habitaciones de un hotel");
                System.out.println("7. Filtrar habitaciones por número de personas");
                System.out.println("8. Reservar habitación");
                System.out.println("9. Listar reservas");
                System.out.println("10. Cancelar reserva");
                System.out.println("11. Salir");
                System.out.print("Elige una opción: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Introduce un número válido.");
                    scanner.nextLine();
                    opcion = 0;
                    continue;
                }

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {

                    case 1 -> {
                        int id = controlador.listarClientes().stream()
                                .mapToInt(Cliente::getIdPersona)
                                .max().orElse(0) + 1;

                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();

                        System.out.print("DNI: ");
                        String dni = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        System.out.print("Teléfono: ");
                        String telefono = scanner.nextLine();

                        Cliente cliente = new Cliente(
                                id, nombre, apellido, dni, email, telefono);

                        controlador.agregarCliente(cliente);
                        System.out.println("Cliente registrado correctamente.");
                    }

                    case 2 -> {
                        List<Cliente> clientes = controlador.listarClientes();

                        if (clientes.isEmpty()) {
                            System.out.println("No hay clientes registrados.");
                        } else {
                            for (Cliente cliente : clientes) {
                                System.out.println(cliente);
                            }
                        }
                    }

                    case 3 -> {
                        System.out.print("Nombre o apellido: ");
                        String texto = scanner.nextLine();

                        List<Cliente> clientes =
                                controlador.buscarClientes(texto);

                        if (clientes.isEmpty()) {
                            System.out.println("No se encontraron clientes.");
                        } else {
                            for (Cliente cliente : clientes) {
                                System.out.println(cliente);
                            }
                        }
                    }

                    case 4 -> {
                        List<Hotel> hoteles = controlador.listarHoteles();

                        if (hoteles.isEmpty()) {
                            System.out.println("No hay hoteles registrados.");
                        } else {
                            for (Hotel hotel : hoteles) {
                                System.out.println(hotel);
                            }
                        }
                    }

                    case 5 -> {
                        System.out.print("Ciudad: ");
                        String ciudad = scanner.nextLine();

                        List<Hotel> hoteles =
                                controlador.buscarHotelesPorCiudad(ciudad);

                        if (hoteles.isEmpty()) {
                            System.out.println("No se encontraron hoteles.");
                        } else {
                            for (Hotel hotel : hoteles) {
                                System.out.println(hotel);
                            }
                        }
                    }

                    case 6 -> {
                        Hotel hotel = seleccionarHotel(scanner, controlador);

                        if (hotel != null) {
                            List<Habitacion> habitaciones =
                                    controlador.listarHabitacionesPorHotel(hotel);

                            if (habitaciones.isEmpty()) {
                                System.out.println(
                                        "El hotel no tiene habitaciones.");
                            } else {
                                for (Habitacion habitacion : habitaciones) {
                                    System.out.println(habitacion);
                                }
                            }
                        }
                    }

                    case 7 -> {
                        Hotel hotel = seleccionarHotel(scanner, controlador);

                        if (hotel != null) {
                            System.out.print("Número de personas: ");

                            if (scanner.hasNextInt()) {
                                int personas = scanner.nextInt();
                                scanner.nextLine();

                                try {
                                    List<Habitacion> habitaciones =
                                            controlador.filtrarHabitacionesPorCapacidad(
                                                    hotel, personas);

                                    if (habitaciones.isEmpty()) {
                                        System.out.println(
                                                "No hay habitaciones con esa capacidad.");
                                    } else {
                                        for (Habitacion habitacion : habitaciones) {
                                            System.out.println(habitacion);
                                        }
                                    }
                                } catch (CapacidadInvalidaException e) {
                                    System.out.println(
                                            "Error: " + e.getMessage());
                                }

                            } else {
                                System.out.println("Introduce un número válido.");
                                scanner.nextLine();
                            }
                        }
                    }

                     case 8 -> {
                            Hotel hotel = seleccionarHotel(scanner, controlador);

                            if (hotel == null) {
                                return;
                            }

                            List<Cliente> clientes = controlador.listarClientes();
                            if (clientes.isEmpty()) {
                                System.out.println("Primero debes registrar un cliente.");
                                return; // 'return' sustituye al break en los switch con flecha (->)
                            }

                            Cliente cliente = clientes.get(0); // Seleccionamos el primer cliente de ejemplo

                            System.out.print("Fecha de entrada (AAAA-MM-DD): ");
                            LocalDate entrada = LocalDate.parse(scanner.nextLine());
                            System.out.print("Fecha de salida (AAAA-MM-DD): ");
                            LocalDate salida = LocalDate.parse(scanner.nextLine());

                            try {
                                System.out.print("Número de personas: ");
                                int numPersonas = scanner.nextInt();
                                scanner.nextLine(); // Limpiar el buffer del scanner

                                List<Habitacion> disponibles = controlador.buscarHabitacionesDisponibles(hotel, entrada, salida, numPersonas);
                                if (disponibles.isEmpty()) {
                                    System.out.println("No hay habitaciones disponibles para esas fechas y capacidad.");
                                } else {
                                    System.out.println("Habitaciones disponibles:");
                                    for (Habitacion h : disponibles) {
                                        System.out.println(h);
                                    }
                                    
                                    System.out.print("Selecciona el ID de la habitación que deseas reservar: ");
                                    int idHabitacion = scanner.nextInt();
                                    scanner.nextLine(); // Limpiar el buffer

                                    // Buscar la habitación seleccionada dentro de las disponibles
                                    Habitacion habitacionSeleccionada = null;
                                    for (Habitacion h : disponibles) {
                                        if (h.getIdHabitacion() == idHabitacion) {
                                            habitacionSeleccionada = h;
                                            break;
                                        }
                                    }

                                    if (habitacionSeleccionada != null) {
                                        // Generar ID único para la reserva
                                        int idReserva = controlador.listarReservas().size() + 1;
                                        
                                        // Crear la reserva
                                        Reserva nuevaReserva = new Reserva(idReserva, entrada, salida, numPersonas, "Activa");
                                        nuevaReserva.setCliente(cliente);
                                        
                                        // Añadir la línea de reserva (esto calcula automáticamente el importe)
                                        nuevaReserva.agregarLineaReserva(habitacionSeleccionada, habitacionSeleccionada.getPrecioNoche());
                                        
                                        // Guardar en el controlador/datos
                                        controlador.agregarReserva(nuevaReserva);
                                        
                                        System.out.println("¡Reserva creada con éxito! Importe total: " + nuevaReserva.getImporte() + " €");
                                    } else {
                                        System.out.println("ID de habitación no válido o no disponible.");
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Error al procesar la reserva: " + e.getMessage());
                            }
                        }

                    case 9 -> {
                        List<Reserva> reservas = controlador.listarReservas();
                        if (reservas.isEmpty()) {
                            System.out.println("No hay reservas registradas.");
                        } else {
                            for (Reserva r : reservas) {
                                System.out.println(r);
                            }
                        }
                    }

                    case 10 -> {
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

                    case 11 -> System.out.println("Saliendo del programa.");

                                    default -> System.out.println("Opción no válida.");
                                }

                            } while (opcion != 11);
                        }
                    }

                    private static Hotel seleccionarHotel(
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
