package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.DetalleVenta;
import util.AdministradorBaseDeDatos;

public class DetalleVentaDAO {
    private static final String INSERT_DETALLE_VENTA = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio) VALUES (?, ?, ?, ?);";
    private static final String SELECT_DETALLE_VENTA = "SELECT * from detalle_venta";
    private static final String SELECT_DETALLE_VENTA_ID = "SELECT * from detalle_venta where id = ?";
    private static final String UPDATE_DETALLE_VENTA = "UPDATE detalle_venta SET id_venta = ?, id_producto = ?, cantidad = ?, precio = ? WHERE id = ?";
    private static final String DELETE_DETALLE_VENTA = "DELETE FROM detalle_venta WHERE id = ?";

    private DetalleVentaDAO() throws ClassNotFoundException,
            IOException, SQLException {
    }
    private static DetalleVentaDAO INSTANCE = null;

    public static DetalleVentaDAO getInstance() throws ClassNotFoundException,
            IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new DetalleVentaDAO();
        }
        return INSTANCE;
    }

    public DetalleVenta obtener(int id) throws ClassNotFoundException, IOException {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DETALLE_VENTA_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first()) {
                return new DetalleVenta(
                        rs.getInt("id"),
                        VentaDAO.getInstance().obtener(rs.getInt("id_venta")),
                        ProductoDAO.getInstance().obtener(rs.getInt("id_producto")),
                        rs.getInt("cantidad"),
                        rs.getInt("precio")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<DetalleVenta> obtenerTodos() throws ClassNotFoundException, IOException {
        try (Connection c = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement ps = c.prepareStatement(SELECT_DETALLE_VENTA)) {
            ResultSet rs = ps.executeQuery();
            List<DetalleVenta> detalleVentas = new ArrayList<>();
            while (rs.next()) {
                detalleVentas.add(new DetalleVenta(
                        rs.getInt("id"),
                        VentaDAO.getInstance().obtener(rs.getInt("id_venta")),
                        ProductoDAO.getInstance().obtener(rs.getInt("id_producto")),
                        rs.getInt("cantidad"),
                        rs.getInt("precio"))
                );
            }
            return detalleVentas;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public void crear(DetalleVenta[] listado) {
        for (DetalleVenta t : listado) {
            try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DETALLE_VENTA)) {
                preparedStatement.setInt(1, t.getVenta().getId());
                preparedStatement.setInt(2, t.getProducto().getId());
                preparedStatement.setInt(3, t.getCantidad());
                preparedStatement.setInt(4, t.getPrecio());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    public void borrar(int id) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(DELETE_DETALLE_VENTA);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void actualizar(DetalleVenta t) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(UPDATE_DETALLE_VENTA);
            preparedStatement.setInt(1, t.getVenta().getId());
            preparedStatement.setInt(2, t.getProducto().getId());
            preparedStatement.setInt(3, t.getCantidad());
            preparedStatement.setInt(4, t.getPrecio());
            preparedStatement.setInt(5, t.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
