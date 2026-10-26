package codecrafters.modelo;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private int idReserva;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int numeroPersonas;
    private String estado;
    private double importe;
    private Cliente cliente;
    private List<LineaReserva> lineasReserva;

    // Constructor 
    public Reserva(int idReserva, LocalDate fechaEntrada, LocalDate fechaSalida, int numeroPersonas, String estado) {
        this.idReserva = idReserva;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.numeroPersonas = numeroPersonas;
        this.estado = estado;
        this.importe = 0.0; 
        this.cliente = null; 
        this.lineasReserva = new ArrayList<>(); 
    }   

    // Método para añadir una línea de reserva
    public void agregarLineaReserva(Habitacion habitacion, double precioNoche) {
        if (habitacion == null) {
            throw new IllegalArgumentException("La habitación no puede ser nula.");
        }

        if (!validarFechas()) {
            throw new IllegalArgumentException("Las fechas de la reserva no son válidas.");
        }

        if (!habitacion.estaDisponible(fechaEntrada, fechaSalida)) {
            throw new IllegalStateException("La habitación no está disponible para esas fechas.");
        }

        for (LineaReserva linea : lineasReserva) {
            if (linea.getHabitacion() == habitacion) {
                throw new IllegalStateException("La habitación ya está incluida en esta reserva.");
            }
        }
    

        new LineaReserva(habitacion, precioNoche, this);
    }

    // Método calculo total reserva
    public double calcularImporte() {
        long dias = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        double totalLineas = 0.0;
        if (lineasReserva != null) {
            for (LineaReserva linea : lineasReserva) {
                totalLineas += linea.getPrecioNoche();
            }
        }
        this.importe = dias > 0 ? dias * totalLineas : 0.0;
        return this.importe;
    }

    public boolean validarFechas() {
        if (fechaEntrada == null || fechaSalida == null) return false;
        return fechaEntrada.isBefore(fechaSalida);
    }

    public void cancelar() {
        this.estado = "Cancelada";
    }

    // Getters y Setters
    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public int getNumeroPersonas() { return numeroPersonas; }
    public void setNumeroPersonas(int numeroPersonas) { this.numeroPersonas = numeroPersonas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; } 

    public List<LineaReserva> getLineasReserva() { return lineasReserva; }  
    public void setLineasReserva(List<LineaReserva> lineasReserva) { this.lineasReserva = lineasReserva; }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                ", numeroPersonas=" + numeroPersonas +
                ", estado='" + estado + '\'' +
                ", importe=" + importe +
                ", cliente=" + (cliente != null ? cliente.getNombre() + " " + cliente.getApellido() : "N/A") +
                ", lineasReserva=" + (lineasReserva != null ? lineasReserva.toString() : "N/A") +        
                '}';
    }
}