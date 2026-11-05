package codecrafters.config;

import codecrafters.controlador.HabitacionController;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import java.util.ArrayList;
import java.util.List;

public class PruebaHabitaciones {

    public static void main(String[] args) {

        HabitacionController controlador = new HabitacionController();

        Hotel hotel = new Hotel(
            1,
            "Hotel Prueba Barcelona",
            "Barcelona",
            4,
            new ArrayList<>()
        );

        System.out.println("HABITACIONES DEL HOTEL");

        List<Habitacion> habitaciones =
            controlador.listarHabitacionesPorHotel(hotel);

        for (Habitacion habitacion : habitaciones) {
            System.out.println(habitacion);
        }

        System.out.println("\nHABITACIONES PARA 2 PERSONAS");

        List<Habitacion> filtradas =
            controlador.filtrarHabitacionesPorCapacidad(hotel, 2);

        for (Habitacion habitacion : filtradas) {
            System.out.println(habitacion);
        }
    }
}