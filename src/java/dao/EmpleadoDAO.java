package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Empleado;
import util.AdministradorBaseDeDatos;
import util.AdministradorContrasenas;

public class EmpleadoDAO {
    private static final String INSERT_USUARIO = "INSERT INTO empleado (nombre, apellido, dni, telefono, usuario, password, direccion, mail, salario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USUARIO = "SELECT * from empleado";
    private static final String SELECT_USUARIO_ID = "SELECT * from empleado where id = ?";
    private static final String UPDATE_USUARIO = "UPDATE empleado SET nombre = ?, apellido = ?, dni = ?, telefono = ?, usuario = ?, password = ?, direccion = ?, mail = ?, salario = ? WHERE id = ?";
    private static final String DELETE_USUARIO = "DELETE FROM empleado WHERE id = ?";
    private static final String SELECT_USUARIO_NOMBRE_USUARIO_CONTRASENA = "SELECT * from empleado where empleado = ?";

    private EmpleadoDAO() throws ClassNotFoundException,
            IOException, SQLException {
    }
    private static EmpleadoDAO INSTANCE = null;

    public static EmpleadoDAO getInstance() throws ClassNotFoundException,
            IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new EmpleadoDAO();
        }
        return INSTANCE;
    }

    public Empleado obtener(int id) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USUARIO_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first()) {
                return new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("usuario"),
                        rs.getString("password"),
                        rs.getString("direccion"),
                        rs.getString("mail"),
                        rs.getInt("salario")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Empleado> obtenerTodos() {
        try (Connection c = AdministradorBaseDeDatos.obtenerConexion();
                PreparedStatement ps = c.prepareStatement(SELECT_USUARIO)) {
            ResultSet rs = ps.executeQuery();
            List<Empleado> empleados = new ArrayList<>();
            while (rs.next()) {
                empleados.add(new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("usuario"),
                        rs.getString("password"),
                        rs.getString("direccion"),
                        rs.getString("mail"),
                        rs.getInt("salario"))
                );
            }
            return empleados;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public void crear(Empleado t) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USUARIO)) {
            preparedStatement.setString(1, t.getNombre());
            preparedStatement.setString(2, t.getApellido());
            preparedStatement.setString(3, t.getDni());
            preparedStatement.setString(4, t.getTelefono());
            preparedStatement.setString(5, t.getUsuario());
            preparedStatement.setString(6, AdministradorContrasenas.encrypt(t.getPassword()));
            preparedStatement.setString(7, t.getDireccion());
            preparedStatement.setString(8, t.getMail());
            preparedStatement.setInt(9, t.getSalario());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void borrar(int id) {

        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(DELETE_USUARIO);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void actualizar(Empleado t) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(UPDATE_USUARIO);
            preparedStatement.setString(1, t.getNombre());
            preparedStatement.setString(2, t.getApellido());
            preparedStatement.setString(3, t.getDni());
            preparedStatement.setString(4, t.getTelefono());
            preparedStatement.setString(5, t.getUsuario());
            preparedStatement.setString(6, AdministradorContrasenas.encrypt(t.getPassword()));
            preparedStatement.setString(7, t.getDireccion());
            preparedStatement.setString(8, t.getMail());
            preparedStatement.setInt(9, t.getSalario());
            preparedStatement.setInt(10, t.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public Empleado identificar(Empleado u) {
        try (Connection c = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement ps = c.prepareStatement(SELECT_USUARIO_NOMBRE_USUARIO_CONTRASENA);
            ps.setString(1, u.getNombre());
            ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                if (AdministradorContrasenas.validarContrasena(u.getPassword(), rs.getString("contrasena")) == true) {
                    return new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("usuario"),
                        rs.getString("password"),
                        rs.getString("direccion"),
                        rs.getString("mail"),
                        rs.getInt("salario")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
