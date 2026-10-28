package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.Cliente;
import java.util.List;
import java.util.Scanner;

public class ClienteView {

    public static void registrarCliente(Scanner scanner, Controlador controlador) {
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

    public static void listarClientes(Controlador controlador) {
        List<Cliente> clientes = controlador.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    public static void buscarClientes(Scanner scanner, Controlador controlador) {
        System.out.print("Nombre o apellido: ");
        String texto = scanner.nextLine();

        List<Cliente> clientes = controlador.buscarClientes(texto);

        if (clientes.isEmpty()) {
            System.out.println("No se encontraron clientes.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }
}