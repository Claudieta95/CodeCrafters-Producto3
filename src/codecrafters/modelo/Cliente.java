package codecrafters.modelo;
public class Cliente extends Persona {

    public Cliente(int idPersona, String nombre, String apellido, String dni, String email, String telefono) {
        super(nombre, apellido, dni, email, telefono);
        setIdPersona(idPersona);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idPersona=" + getIdPersona() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", dni='" + getDni() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                '}';
    }
}