package codecrafters.controlador;

import codecrafters.modelo.Cliente;
import codecrafters.modelo.Datos;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Reserva;

import java.util.List;

public class ReservaController {

    private Datos datos;

    public ReservaController(Datos datos) {
        this.datos = datos;
    }

    public void agregarReserva(Reserva reserva) {
        datos.agregarReserva(reserva);
    }

    public List<Reserva> listarReservas() {
        return datos.listarReservas();
    }

    public List<Reserva> buscarReservasPorCliente(Cliente cliente) {
        return datos.buscarReservasPorCliente(cliente);
    }

    public List<Reserva> buscarReservasPorHotel(Hotel hotel) {
        return datos.buscarReservasPorHotel(hotel);
    }

    public boolean cancelarReserva(int idReserva) {
        return datos.cancelarReserva(idReserva);
    }
}