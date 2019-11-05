package controller;

import com.google.gson.Gson;
import dao.EmpleadoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Empleado;

public class EmpleadoServlet extends HttpServlet {
    final static Gson CONVERTIR = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //Empleado user =  (Empleado)request.getSession().getAttribute("empleado");

        try {
            if (request.getParameter("q") == null){
                String texto = request.getReader().readLine();
                List<Empleado> listado = EmpleadoDAO.getInstance().obtenerTodos();
                String listJSON = CONVERTIR.toJson(listado);
                out.println(listJSON);    
            } else {
                Empleado empleado = EmpleadoDAO.getInstance().obtener(Integer.parseInt(request.getParameter("q")));
                out.println(CONVERTIR.toJson(empleado));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //Empleado user =  (Empleado)request.getSession().getAttribute("empleado");
        
        try {
            //if(user != null){
                String texto = request.getReader().readLine();
                Empleado empleadoParametro = CONVERTIR.fromJson(texto, Empleado.class);
                EmpleadoDAO.getInstance().crear(empleadoParametro);
                out.println(CONVERTIR.toJson("OK"));    
            //}else{
              //  out.println(CONVERTIR.toJson("Usted no esta registrado"));
            //}
        } catch (ClassNotFoundException ex) {
            out.println("Verificar1: " + ex.getMessage());
        } catch (SQLException ex) {
            out.println("Verificar2:" + ex.getMessage());
        } catch (Exception ex) {
            out.println("Verificar3:" + ex.getMessage());
        } finally {
            out.close();
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //Empleado user =  (Empleado)request.getSession().getAttribute("empleado");
        
        try {
            //if(user != null){
                String texto = request.getReader().readLine();
                Empleado empleadoParametro = CONVERTIR.fromJson(texto, Empleado.class);
                EmpleadoDAO.getInstance().actualizar(empleadoParametro);
                out.println(CONVERTIR.toJson("OK"));    
            //}else{
              //  out.println(CONVERTIR.toJson("Usted no esta registrado"));
            //}
        } catch (ClassNotFoundException ex) {
            out.println("Verificar1: " + ex.getMessage());
        } catch (SQLException ex) {
            out.println("Verificar2:" + ex.getMessage());
        } catch (Exception ex) {
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
            EmpleadoDAO.getInstance().borrar(Integer.parseInt(request.getParameter("q")));
            out.println(CONVERTIR.toJson("OK"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
