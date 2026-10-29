package codecrafters.controlador;

import codecrafters.dao.ClienteDAO;
import codecrafters.dao.DAOFactory;
import codecrafters.modelo.Cliente;

import java.util.List;

public class ClienteController {

    private final ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = DAOFactory.crearClienteDAO();
    }

    public void agregarCliente(Cliente cliente) {
        clienteDAO.guardar(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listar();
    }

    public List<Cliente> buscarClientes(String texto) {
        return clienteDAO.buscar(texto);
    }
}