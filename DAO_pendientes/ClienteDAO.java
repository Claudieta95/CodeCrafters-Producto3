import java.util.List;

public interface ClienteDAO {
    List<Cliente> listar();
    void guardar(Cliente cliente);
    List<Cliente> buscar(String nombreOApellido);
    void modificar(Cliente cliente);
    void eliminar(Cliente cliente);
}