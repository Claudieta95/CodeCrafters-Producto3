package codecrafters.dao;

import codecrafters.config.ConexionBD;
import codecrafters.modelo.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;

import java.util.ArrayList;
import java.util.List;

public class HotelDAOImpl implements HotelDAO {

    @Override
    public List<Hotel> listarHoteles() {

        List<Hotel> hoteles = new ArrayList<>();

        String sql = """
            SELECT id_hotel, nombre, ciudad, estrellas
            FROM hotel
            ORDER BY id_hotel
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                hoteles.add(crearHotel(resultado));
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al listar los hoteles", e
            );
        }

        return hoteles;
    }

    @Override
    public List<Hotel> listarHotelesPorCiudad(String ciudad) {

        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException(
                "Debes indicar una ciudad"
            );
        }

        List<Hotel> hoteles = new ArrayList<>();

        String sql = "{CALL buscar_hoteles_por_ciudad(?)}";

        try (Connection conexion = ConexionBD.obtenerConexion();
             CallableStatement sentencia = conexion.prepareCall(sql)) {

            sentencia.setString(1, ciudad.trim());

            try (ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {
                    hoteles.add(crearHotel(resultado));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al buscar hoteles por ciudad", e
            );
        }

        return hoteles;
    }

    @Override
    public List<Hotel> listarHotelesPorEstrellas(int estrellas) {

        if (estrellas < 1 || estrellas > 5) {
            throw new IllegalArgumentException(
                "Las estrellas deben estar entre 1 y 5"
            );
        }

        List<Hotel> hoteles = new ArrayList<>();

        String sql = """
            SELECT id_hotel, nombre, ciudad, estrellas
            FROM hotel
            WHERE estrellas = ?
            ORDER BY nombre
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, estrellas);

            try (ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {
                    hoteles.add(crearHotel(resultado));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                "Error al buscar hoteles por estrellas", e
            );
        }

        return hoteles;
    }

    private Hotel crearHotel(ResultSet resultado) throws SQLException {

        return new Hotel(
            resultado.getInt("id_hotel"),
            resultado.getString("nombre"),
            resultado.getString("ciudad"),
            resultado.getInt("estrellas"),
            new ArrayList<>()
        );
    }
}