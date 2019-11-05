package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Marca;
import util.AdministradorBaseDeDatos;

public class MarcaDAO {
    private static final String INSERT_MARCA = "INSERT INTO marca (nombre_marca) VALUES (?);";
    private static final String SELECT_MARCA = "SELECT * from marca";
    private static final String SELECT_MARCA_ID = "SELECT * from marca where id = ?";
    private static final String UPDATE_MARCA = "UPDATE marca SET marca = ? WHERE id = ?";
    private static final String DELETE_MARCA = "DELETE FROM marca WHERE id = ?";

    private MarcaDAO() throws ClassNotFoundException,
            IOException, SQLException {
    }
    private static MarcaDAO INSTANCE = null;

    public static MarcaDAO getInstance() throws ClassNotFoundException,
            IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new MarcaDAO();
        }
        return INSTANCE;
    }

    public Marca obtener(int id) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MARCA_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first()) {
                return new Marca(
                        rs.getInt("id"),
                        rs.getString("nombre_marca")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Marca> obtenerTodos() {
        try (Connection c = AdministradorBaseDeDatos.obtenerConexion();
                PreparedStatement ps = c.prepareStatement(SELECT_MARCA)) {
            ResultSet rs = ps.executeQuery();
            List<Marca> marcas = new ArrayList<>();
            while (rs.next()) {
                marcas.add(new Marca(
                        rs.getInt("id"),
                        rs.getString("nombre_marca"))
                );
            }
            return marcas;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public void crear(Marca t) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MARCA)) {
            preparedStatement.setString(1, t.getMarca());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void borrar(int id) {

        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(DELETE_MARCA);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void actualizar(Marca t) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(UPDATE_MARCA);
            preparedStatement.setString(1, t.getMarca());
            preparedStatement.setInt(2, t.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
