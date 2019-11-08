package dao;

import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Venta;
import util.AdministradorBaseDeDatos;

public class VentaDAO {
    private static final String INSERT_VENTA = "INSERT INTO venta (id_empleado, id_cliente, fecha, cancelado) VALUES (?, ?, ?, ?);";
    private static final String SELECT_VENTA = "SELECT * from venta";
    private static final String SELECT_VENTA_ID = "SELECT * from venta where id = ?";
    private static final String UPDATE_VENTA = "UPDATE venta SET cancelado = ? WHERE id = ?";
    private static final String DELETE_VENTA = "UPDATE venta SET cancelado = ? WHERE id = ?";

    private VentaDAO() throws ClassNotFoundException,
            IOException, SQLException {
    }
    private static VentaDAO INSTANCE = null;

    public static VentaDAO getInstance() throws ClassNotFoundException,
            IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new VentaDAO();
        }
        return INSTANCE;
    }

    public Venta obtener(int id) throws ClassNotFoundException, IOException {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VENTA_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first()) {
                return new Venta(
                        rs.getInt("id"),
                        EmpleadoDAO.getInstance().obtener(rs.getInt("id_empleado")),
                        ClienteDAO.getInstance().obtener(rs.getInt("id_cliente")),
                        fechaCadena(rs.getDate("fecha")),
                        rs.getBoolean("cancelado")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Venta> obtenerTodos() throws ClassNotFoundException, IOException {
        try (Connection c = AdministradorBaseDeDatos.obtenerConexion();
                PreparedStatement ps = c.prepareStatement(SELECT_VENTA)) {
            ResultSet rs = ps.executeQuery();
            List<Venta> ventas = new ArrayList<>();
            while (rs.next()) {
                ventas.add(new Venta(
                        rs.getInt("id"),
                        EmpleadoDAO.getInstance().obtener(rs.getInt("id_empleado")),
                        ClienteDAO.getInstance().obtener(rs.getInt("id_cliente")),
                        fechaCadena(rs.getDate("fecha")),
                        rs.getBoolean("cancelado"))
                );
            }
            return ventas;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public int crear(Venta t) {
        try (Connection connection = AdministradorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VENTA, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, t.getEmpleado().getId());
            preparedStatement.setInt(2, t.getCliente().getId());
            preparedStatement.setDate(3, (Date) normalizarFecha(t.getFecha()));
            preparedStatement.setBoolean(4, true);
            preparedStatement.executeUpdate();
            ResultSet id = preparedStatement.getGeneratedKeys();
            if (id.next()) {
                return id.getInt(1);
            }else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return 0;
        }
    }

    public void borrar(int id) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(DELETE_VENTA);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void actualizar(int id) {
        try (Connection conexion = AdministradorBaseDeDatos.obtenerConexion()) {
            PreparedStatement preparedStatement = conexion.prepareStatement(UPDATE_VENTA);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public java.util.Date normalizarFecha(String fecha){
        java.sql.Date fecFormatoDate = null;
        try {
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            fecFormatoDate = new java.sql.Date(sdf.parse(fecha).getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        return fecFormatoDate;
    }
    
    public String fechaCadena(java.sql.Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaCadena = sdf.format(fecha);
        
        return fechaCadena;
    }
}
