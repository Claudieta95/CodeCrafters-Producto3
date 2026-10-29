package codecrafters.dao;

import codecrafters.modelo.Cliente;

public class PruebaClienteDAO {

    public static void main(String[] args) {

        ClienteDAO dao = new ClienteDAOImpl();
        Cliente clientePrueba = new Cliente(
            0,
            "Prueba",
            "Cliente",
            "99999999R",
            "prueba@example.com",
            "600000000"
        );

        // dao.guardar(clientePrueba);
        // System.out.println("Cliente guardado con ID: " + clientePrueba.getIdPersona());

        System.out.println("=== LISTADO DE CLIENTES ===");

        for (Cliente cliente : dao.listar()) {
            System.out.println(
                cliente.getIdPersona() + " - " +
                cliente.getNombre() + " " +
                cliente.getApellido()
            );
        }

        System.out.println("=== FIN DE LA PRUEBA ===");

        System.out.println("=== BÚSQUEDA DE CLIENTES ===");

        for (Cliente cliente : dao.buscar("Prueba")) {
            System.out.println(
                cliente.getIdPersona() + " - " +
                cliente.getNombre() + " " +
                cliente.getApellido()
            );
        }

        Cliente clienteModificar = new Cliente(
            1,
            "Prueba",
            "Modificado",
            "99999999R",
            "prueba@example.com",
            "600000000"
        );

        // dao.modificar(clienteModificar);

        System.out.println("=== CLIENTE MODIFICADO ===");

        for (Cliente cliente : dao.listar()) {
            System.out.println(
                cliente.getIdPersona() + " - " +
                cliente.getNombre() + " " +
                cliente.getApellido()
            );
        }

        System.out.println("=== ELIMINAR CLIENTE ===");

        // dao.eliminar(clienteModificar);

        System.out.println("=== LISTADO DESPUÉS DE ELIMINAR ===");

        for (Cliente cliente : dao.listar()) {
            System.out.println(
                cliente.getIdPersona() + " - " +
                cliente.getNombre() + " " +
                cliente.getApellido()
            );
        }
    }    
}
