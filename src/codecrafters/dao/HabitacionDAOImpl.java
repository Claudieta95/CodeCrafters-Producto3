
package codecrafters.dao;

import codecrafters.config.ConexionBD;
import codecrafters.modelo.Habitacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAOImpl implements HabitacionDAO {

    @Override
    public List<Habitacion> listarPorHotel(int idHotel) {

        if (idHotel <= 0) {
            throw new IllegalArgumentException("El ID del hotel debe ser válido");
        }

        List<Habitacion> habitaciones = new ArrayList<>();

        String sql = """
            SELECT id_habitacion, numero, categoria,
                   capacidad, precio_noche
            FROM habitacion
            WHERE id_hotel = ?
            ORDER BY numero
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idHotel);

            try (ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {

                    Habitacion habitacion = new Habitacion(
                        resultado.getInt("id_habitacion"),
                        resultado.getInt("numero"),
                        resultado.getString("categoria"),
                        resultado.getInt("capacidad"),
                        resultado.getDouble("precio_noche")
                    );

                    habitaciones.add(habitacion);
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al listar las habitaciones del hotel", e);
        }

        return habitaciones;
    }

    @Override
    public List<Habitacion> buscarPorCapacidad(
            int idHotel, int numeroPersonas) {

        if (idHotel <= 0) {
            throw new IllegalArgumentException("El ID del hotel debe ser válido");
        }

        if (numeroPersonas <= 0) {
            throw new IllegalArgumentException(
                "El número de personas debe ser mayor que cero");
        }

        List<Habitacion> habitaciones = new ArrayList<>();

        String sql = """
            SELECT id_habitacion, numero, categoria,
                   capacidad, precio_noche
            FROM habitacion
            WHERE id_hotel = ?
              AND capacidad >= ?
            ORDER BY capacidad, numero
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idHotel);
            sentencia.setInt(2, numeroPersonas);

            try (ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {

                    Habitacion habitacion = new Habitacion(
                        resultado.getInt("id_habitacion"),
                        resultado.getInt("numero"),
                        resultado.getString("categoria"),
                        resultado.getInt("capacidad"),
                        resultado.getDouble("precio_noche")
                    );

                    habitaciones.add(habitacion);
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al buscar habitaciones por capacidad", e);
        }

        return habitaciones;
    }
}
