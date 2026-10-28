package codecrafters.modelo;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservasTest {

    private Reserva reserva;
    private Habitacion habitacion;
    private Datos datos;

    @BeforeEach
    public void setUp() {
        LocalDate fechaEntrada = LocalDate.of(2026, 10, 20);
        LocalDate fechaSalida = LocalDate.of(2026, 10, 25); // 5 noches

        reserva = new Reserva(1, fechaEntrada, fechaSalida, 2, "Activa");
        habitacion = new Habitacion(1, 101, "Doble", 2, 100.0);
        datos = new Datos();
    }

    @Test
    public void testCalcularImporte() {
        reserva.agregarLineaReserva(habitacion, habitacion.getPrecioNoche());

        double importeCalculado = reserva.calcularImporte();
        double importeEsperado = habitacion.getPrecioNoche() * 5; // 5 noches

        assertEquals(importeEsperado, importeCalculado, 0.01);
    }

    @Test
    public void testValidarFechas() {
        assertTrue(reserva.validarFechas(), "Las fechas de la reserva son válidas.");
    }

    @Test
    public void testValidarFechasInvalidas() {
        LocalDate fechaEntradaInvalida = LocalDate.of(2026, 10, 26);
        LocalDate fechaSalidaInvalida = LocalDate.of(2026, 10, 25);

        Reserva reservaInvalida = new Reserva(2, fechaEntradaInvalida, fechaSalidaInvalida, 2, "Activa");

        assertFalse(reservaInvalida.validarFechas(), "Las fechas de la reserva no son válidas.");
    }
    @Test
    public void testHabitacionNoDisponiblePorSolapamiento() {
        reserva.agregarLineaReserva(habitacion, habitacion.getPrecioNoche());

        datos.agregarReserva(reserva);
        
        LocalDate nuevaEntrada = LocalDate.of(2026, 10, 22);
        LocalDate nuevaSalida = LocalDate.of(2026, 10, 26);
        
        assertFalse(datos.estaHabitacionDisponible(habitacion, nuevaEntrada, nuevaSalida),
            "La habitación no debería estar disponible porque las fechas se solapan.");
    }
}