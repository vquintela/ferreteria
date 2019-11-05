package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import util.AdministradorBaseDeDatos;

public class ClienteDAO {
    private static final String INSERT_USUARIO = "INSERT INTO cliente (nombre, apellido, dni, mail, direccion, telefono) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USUARIO = "SELECT * from cliente";
    private static final String SELECT_USUARIO_ID = "SELECT * from cliente where id = ?";
    private static final String UPDATE_USUARIO = "UPDATE cliente SET nombre = ?, apellido = ?, dni = ?, mail = ?, direccion = ?, telefono = ? WHERE id = ?";
    private static final String DELETE_USUARIO = "DELETE FROM cliente WHERE id = ?";

    private ClienteDAO() throws ClassNotFoundException,
            IOException, SQLException {
    }
    private static ClienteDAO INSTANCE = null;

    public static ClienteDAO getInstance() throws ClassNotFoundException,
            IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new ClienteDAO();
        }
        return INSTANCE;
    }

    public Cliente obtener(int id) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USUARIO_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("mail"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Cliente> obtenerTodos() {
        try (Connection c = AdministradorBaseDeDatos.obtenerConexion();
                PreparedStatement ps = c.prepareStatement(SELECT_USUARIO)) {
            ResultSet rs = ps.executeQuery();
            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("mail"),
                        rs.getString("direccion"),
                        rs.getString("telefono"))
                );
            }
            return clientes;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public void crear(Cliente t) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USUARIO)) {
            preparedStatement.setString(1, t.getNombre());
            preparedStatement.setString(2, t.getApellido());
            preparedStatement.setString(3, t.getDni());
            preparedStatement.setString(4, t.getMail());
            preparedStatement.setString(5, t.getDireccion());
            preparedStatement.setString(6, t.getTelefono());
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

    public void actualizar(Cliente t) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(UPDATE_USUARIO);
            preparedStatement.setString(1, t.getNombre());
            preparedStatement.setString(2, t.getApellido());
            preparedStatement.setString(3, t.getDni());
            preparedStatement.setString(4, t.getMail());
            preparedStatement.setString(5, t.getDireccion());
            preparedStatement.setString(6, t.getTelefono());
            preparedStatement.setInt(7, t.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
