package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdministradorBaseDeDatos {
    private static Connection connection;
    
    private static final String URL = "jdbc:mysql://localhost:3306/Ferreteria";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    public static Connection obtenerConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.err);
        }
        // TODO Auto-generated catch block
        return connection;
    }
}
