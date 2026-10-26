package codecrafters.modelo;
import java.util.List;

public class Hotel {
    private int idHotel;
    private String nombre;
    private String ciudad;
    private int estrellas;
    private List<Habitacion> habitaciones;

    // Constructor
    public Hotel(int idHotel, String nombre, String ciudad, int estrellas, List<Habitacion> habitaciones) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estrellas = estrellas;
        this.habitaciones = habitaciones;

        if (habitaciones != null) {
            for (Habitacion habitacion : habitaciones) {
                habitacion.setHotel(this);
            }
        }
    }

    // Getters y Setters
    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;

        if (habitaciones != null) {
            for (Habitacion habitacion : habitaciones) {
                habitacion.setHotel(this);
            }
        }
    }


@Override
public String toString() {
        return "Hotel{" +
                "idHotel=" + idHotel +
                ", nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", estrellas=" + estrellas +
                ", habitaciones=" + habitaciones +
                '}';
    }
}