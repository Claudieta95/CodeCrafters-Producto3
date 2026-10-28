package codecrafters.modelo;

public class Habitacion {
    private int idHabitacion;
    private int numero;
    private String categoria;
    private int capacidad;
    private double precioNoche;

    // Constructor
    public Habitacion(int idHabitacion, int numero, String categoria, int capacidad, double precioNoche) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.categoria = categoria;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
    }   

    // Getters y Setters
    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "idHabitacion=" + idHabitacion +
                ", numero=" + numero +
                ", categoria='" + categoria + '\'' +
                ", capacidad=" + capacidad +
                ", precioNoche=" + precioNoche +
                '}';
}
}
