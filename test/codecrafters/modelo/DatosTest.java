package codecrafters.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DatosTest {

    @Test
    void comprobarFiltradoHabitacionesPorCapacidad() {
        Datos datos = new Datos();

        List<Habitacion> habitacionesHotel = new ArrayList<>();
        habitacionesHotel.add(new Habitacion(1, 101, "Individual", 1, 60.0));
        habitacionesHotel.add(new Habitacion(2, 102, "Doble", 2, 90.0));
        habitacionesHotel.add(new Habitacion(3, 103, "Familiar", 4, 150.0));

        Hotel hotel = new Hotel(
                1,
                "Hotel Barcelona",
                "Barcelona",
                4,
                habitacionesHotel
        );

        datos.agregarHotel(hotel);

        List<Habitacion> habitaciones =
                datos.filtrarHabitacionesPorCapacidad(hotel, 3);

        assertEquals(1, habitaciones.size());
        assertEquals(103, habitaciones.get(0).getNumero());

        assertThrows(
                CapacidadInvalidaException.class,
                () -> datos.filtrarHabitacionesPorCapacidad(hotel, 0)
        );
    }
}
