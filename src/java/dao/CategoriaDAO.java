package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import util.AdministradorBaseDeDatos;

public class CategoriaDAO {
    private static final String INSERT_CATEGORIA = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?);";
    private static final String SELECT_CATEGORIA = "SELECT * from categoria";
    private static final String SELECT_CATEGORIA_ID = "SELECT * from categoria where id = ?";
    private static final String UPDATE_CATEGORIA = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
    private static final String DELETE_CATEGORIA = "DELETE FROM categoria WHERE id = ?";

    private CategoriaDAO() throws ClassNotFoundException,
            IOException, SQLException {
    }
    private static CategoriaDAO INSTANCE = null;

    public static CategoriaDAO getInstance() throws ClassNotFoundException,
            IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new CategoriaDAO();
        }
        return INSTANCE;
    }

    public Categoria obtener(int id) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORIA_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first()) {
                return new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Categoria> obtenerTodos() {
        try (Connection c = AdministradorBaseDeDatos.obtenerConexion();
                PreparedStatement ps = c.prepareStatement(SELECT_CATEGORIA)) {
            ResultSet rs = ps.executeQuery();
            List<Categoria> categorias = new ArrayList<>();
            while (rs.next()) {
                categorias.add(new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"))
                );
            }
            return categorias;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public void crear(Categoria t) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORIA)) {
            preparedStatement.setString(1, t.getNombre());
            preparedStatement.setString(2, t.getDescripcion());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void borrar(int id) {

        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(DELETE_CATEGORIA);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void actualizar(Categoria t) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(UPDATE_CATEGORIA);
            preparedStatement.setString(1, t.getNombre());
            preparedStatement.setString(2, t.getDescripcion());
            preparedStatement.setInt(3, t.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
