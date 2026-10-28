package codecrafters.vista;

public class MenuView {

    public static void mostrarMenu() {
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
    }
}