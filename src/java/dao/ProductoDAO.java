package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Producto;
import util.AdministradorBaseDeDatos;

public class ProductoDAO {
    private static final String INSERT_PRODUCTO = "INSERT INTO producto (id_categoria, id_marca, nombre, costo, precio_unitario, stock) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_PRODUCTO = "SELECT * from producto";
    private static final String SELECT_PRODUCTO_ID = "SELECT * from producto where id = ?";
    private static final String UPDATE_PRODUCTO = "UPDATE producto SET id_categoria = ?, id_marca = ?, nombre = ?, costo = ?, precio_unitario = ?, stock = ? WHERE id = ?";
    private static final String DELETE_PRODUCTO = "DELETE FROM producto WHERE id = ?";

    private ProductoDAO() throws ClassNotFoundException,
            IOException, SQLException {
    }
    private static ProductoDAO INSTANCE = null;

    public static ProductoDAO getInstance() throws ClassNotFoundException,
            IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new ProductoDAO();
        }
        return INSTANCE;
    }

    public Producto obtener(int id) throws ClassNotFoundException, IOException {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTO_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first()) {
                return new Producto(
                        rs.getInt("id"),
                        CategoriaDAO.getInstance().obtener(rs.getInt("id_categoria")),
                        MarcaDAO.getInstance().obtener(rs.getInt("id_marca")),
                        rs.getString("nombre"),
                        rs.getInt("costo"),
                        rs.getInt("precio_unitario"),
                        rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Producto> obtenerTodos() throws ClassNotFoundException, IOException {
        try (Connection c = AdministradorBaseDeDatos.obtenerConexion();
                PreparedStatement ps = c.prepareStatement(SELECT_PRODUCTO)) {
            ResultSet rs = ps.executeQuery();
            List<Producto> productos = new ArrayList<>();
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id"),
                        CategoriaDAO.getInstance().obtener(rs.getInt("id_categoria")),
                        MarcaDAO.getInstance().obtener(rs.getInt("id_marca")),
                        rs.getString("nombre"),
                        rs.getInt("costo"),
                        rs.getInt("precio_unitario"),
                        rs.getInt("stock"))
                );
            }
            return productos;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public void crear(Producto t) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTO)) {
            preparedStatement.setInt(1, t.getCategoria().getId());
            preparedStatement.setInt(2, t.getMarca().getId());
            preparedStatement.setString(3, t.getNombre());
            preparedStatement.setInt(4, t.getCosto());
            preparedStatement.setInt(5, t.getPrecioUnitario());
            preparedStatement.setInt(6, t.getStock());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void borrar(int id) {

        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(DELETE_PRODUCTO);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void actualizar(Producto t) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(UPDATE_PRODUCTO);
            preparedStatement.setInt(1, t.getCategoria().getId());
            preparedStatement.setInt(2, t.getMarca().getId());
            preparedStatement.setString(3, t.getNombre());
            preparedStatement.setInt(4, t.getCosto());
            preparedStatement.setInt(5, t.getPrecioUnitario());
            preparedStatement.setInt(6, t.getStock());
            preparedStatement.setInt(7, t.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
