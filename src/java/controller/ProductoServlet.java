package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dao.ProductoDAO;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Producto;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {
    final static Gson CONVERTIR = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        try {
            if (request.getParameter("q") == null){
                String texto = request.getReader().readLine();
                List<Producto> listado = ProductoDAO.getInstance().obtenerTodos();
                String listJSON = CONVERTIR.toJson(listado);
                out.println(listJSON);
            } else {
                Producto producto = ProductoDAO.getInstance().obtener(Integer.parseInt(request.getParameter("q")));
                out.println(CONVERTIR.toJson(producto));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MarcaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String texto = request.getReader().readLine();
            Producto producto = CONVERTIR.fromJson(texto, Producto.class);
            ProductoDAO.getInstance().crear(producto);
            out.println(CONVERTIR.toJson("OK"));
        } catch (ClassNotFoundException ex) {
            out.println("Verificar1: " + ex.getMessage());
        } catch (SQLException ex) {
            out.println("Verificar2:" + ex.getMessage());
        } catch (JsonSyntaxException | IOException ex) {
            out.println("Verificar3:" + ex.getMessage());
        } finally {
            out.close();
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            ProductoDAO.getInstance().borrar(Integer.parseInt(request.getParameter("q")));
            out.println(CONVERTIR.toJson("OK"));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
                String texto = request.getReader().readLine();
                Producto productoParametro = CONVERTIR.fromJson(texto, Producto.class);
                ProductoDAO.getInstance().actualizar(productoParametro);
                out.println(CONVERTIR.toJson("OK"));    
        } catch (ClassNotFoundException ex) {
            out.println("Verificar1: " + ex.getMessage());
        } catch (SQLException ex) {
            out.println("Verificar2:" + ex.getMessage());
        } catch (JsonSyntaxException | IOException ex) {
            out.println("Verificar3:" + ex.getMessage());
        } finally {
            out.close();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
