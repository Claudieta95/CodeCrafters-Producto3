package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.Cliente;

import java.util.List;
import java.util.Scanner;

public class ClienteView {

    public static void registrarCliente(
            Scanner scanner, Controlador controlador) {

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

        // MySQL generará el ID automáticamente.
        Cliente cliente = new Cliente(
                0, nombre, apellido, dni, email, telefono);

        try {
            controlador.agregarCliente(cliente);

            System.out.println(
                    "Cliente registrado correctamente. ID: "
                    + cliente.getIdPersona());

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(
                    "No se pudo registrar el cliente: " + e.getMessage());
        }
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

    public static void buscarClientes(
            Scanner scanner, Controlador controlador) {

        System.out.print("Nombre o apellido: ");
        String texto = scanner.nextLine();

        try {
            List<Cliente> clientes = controlador.buscarClientes(texto);

            if (clientes.isEmpty()) {
                System.out.println("No se encontraron clientes.");
            } else {
                for (Cliente cliente : clientes) {
                    System.out.println(cliente);
                }
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(
                    "No se pudo realizar la búsqueda: " + e.getMessage());
        }
    }
}