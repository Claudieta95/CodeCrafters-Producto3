package codecrafters.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/codecrafters_producto3";

    private static final String USUARIO = "codecrafters_app";

    private static final String PASSWORD =
            System.getenv("MYSQL_PASSWORD");

    public static Connection obtenerConexion() throws SQLException {
        if (PASSWORD == null || PASSWORD.isEmpty()) {
            throw new SQLException("Falta configurar MYSQL_PASSWORD");
        }

        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}