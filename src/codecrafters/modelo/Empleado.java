package codecrafters.modelo;
public class Empleado extends Persona {
    private String puesto;

  // Constructor para crear un nuevo empleado (sin idPersona, para registros nuevos)
    public Empleado(String nombre, String apellido, String dni, String email, String telefono, String puesto) {
        super(nombre, apellido, dni, email, telefono);
        this.puesto = puesto;
    }

    // Constructor para crear un empleado existente (con idPersona, para registros existentes)
    public Empleado(int idPersona, String nombre, String apellido, String dni, String email, String telefono, String puesto) {
         this(nombre, apellido, dni, email, telefono, puesto);
         setIdPersona(idPersona);
    }

    //  getter y setter
    public String getPuesto() {
        return puesto;   
    }   
    
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "idPersona=" + getIdPersona() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", dni='" + getDni() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", puesto='" + getPuesto() + '\'' +
                '}';
    }
}