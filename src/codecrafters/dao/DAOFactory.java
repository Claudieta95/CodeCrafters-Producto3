package codecrafters.dao;

public class DAOFactory {

    private DAOFactory() {
        // Evita crear instancias de esta clase.
    }

    public static ClienteDAO crearClienteDAO() {
        return new ClienteDAOImpl();
    }

    public static HotelDAO crearHotelDAO() {
        return new HotelDAOImpl();
    }

    public static HabitacionDAO crearHabitacionDAO() {
        return new HabitacionDAOImpl();
    }

    public static ReservaDAO crearReservaDAO() {
        return new ReservaDAOImpl();
    }
}