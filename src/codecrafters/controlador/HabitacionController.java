package codecrafters.controlador;

import codecrafters.modelo.Datos;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;

import java.time.LocalDate;
import java.util.List;

public class HabitacionController {

    private Datos datos;

    public HabitacionController(Datos datos) {
        this.datos = datos;
    }

    public List<Habitacion> listarHabitacionesPorHotel(Hotel hotel) {
        return datos.listarHabitacionesPorHotel(hotel);
    }

    public List<Habitacion> filtrarHabitacionesPorCapacidad(
            Hotel hotel, int numeroPersonas) {
        return datos.filtrarHabitacionesPorCapacidad(hotel, numeroPersonas);
    }

    public List<Habitacion> buscarHabitacionesDisponibles(
            Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida,
            int numeroPersonas) {

        return datos.buscarHabitacionesDisponibles(
                hotel, fechaEntrada, fechaSalida, numeroPersonas);
    }
}