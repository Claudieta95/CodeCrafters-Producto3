package codecrafters.config;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            System.out.println("CONEXIÓN CORRECTA CON MYSQL");
            System.out.println("Base de datos: " + conexion.getCatalog());
        } catch (SQLException e) {
            System.out.println("ERROR DE CONEXIÓN: " + e.getMessage());
        }
    }
}