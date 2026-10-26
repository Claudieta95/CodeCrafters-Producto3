package codecrafters.modelo;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Habitacion {
    private int idHabitacion;
    private int numero;
    private String categoria;
    private int capacidad;
    private double precioNoche;
    private Hotel hotel;
    private List<LineaReserva> lineasReserva;

    // Constructor
    public Habitacion(int idHabitacion, int numero, String categoria, int capacidad, double precioNoche) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.categoria = categoria;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
        this.hotel = null;
        this.lineasReserva = new ArrayList<>();
    }   

    public void agregarLineaReserva(LineaReserva linea) {
        if (linea != null && !lineasReserva.contains(linea)) {
            lineasReserva.add(linea);
        }
    }
    // Método disponibilidad habitacio
    public boolean estaDisponible(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (fechaEntrada == null || fechaSalida == null || !fechaEntrada.isBefore(fechaSalida)) {
            return false; // Fechas inválidas
        }

        for (LineaReserva linea : this.lineasReserva) {
            Reserva reserva = linea.getReserva();

            if (reserva != null && !"Cancelada".equalsIgnoreCase(reserva.getEstado())) {
                // Comprobación de solapamiento de fechas
                boolean seSolapan = fechaEntrada.isBefore(reserva.getFechaSalida()) && fechaSalida.isAfter(reserva.getFechaEntrada());
                if (seSolapan) {
                    return false; // Ocupada en esas fechas
                }
            }
        }
        
        return true;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "idHabitacion=" + idHabitacion +
                ", numero=" + numero +
                ", categoria='" + categoria + '\'' +
                ", capacidad=" + capacidad +
                ", precioNoche=" + precioNoche +
                ", hotel=" + (hotel != null ? hotel.getNombre() : "null") +
                '}';
    }
}
