package codecrafters.modelo;
public class LineaReserva {
    private double precioNoche;
    private Reserva reserva;
    private Habitacion habitacion;

    public LineaReserva(Habitacion habitacion, double precioNoche, Reserva reserva) {
        this.habitacion = habitacion;
        this.precioNoche = precioNoche;
        this.reserva = reserva;
    
        
        if (reserva != null && reserva.getLineasReserva() != null && !reserva.getLineasReserva().contains(this)) {
            reserva.getLineasReserva().add(this);
        }

        if (habitacion != null) {
            habitacion.agregarLineaReserva(this);
        }

    }
            
        public double getPrecioNoche() {
            return precioNoche;
        }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString() {
        return "LineaReserva{" +
                "precioNoche=" + precioNoche +
                ", reserva=" + (reserva != null ? reserva.getIdReserva() : "N/A") +
                ", habitacion=" + (habitacion != null ? habitacion.toString() : "N/A") +
                '}';
    }
}