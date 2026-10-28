package codecrafters.controlador;

import codecrafters.modelo.Cliente;
import codecrafters.modelo.Datos;
import java.util.List;

public class ClienteController {

    private Datos datos;

    public ClienteController(Datos datos) {
        this.datos = datos;
    }

    public void agregarCliente(Cliente cliente) {
        datos.agregarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return datos.listarClientes();
    }

    public List<Cliente> buscarClientes(String texto) {
        return datos.buscarClientes(texto);
    }
}