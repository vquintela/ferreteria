/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dao.CategoriaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Categoria;

/**
 *
 * @author vitor
 */
@WebServlet(name = "CategoriaServlet", urlPatterns = {"/CategoriaServlet"})
public class CategoriaServlet extends HttpServlet {
    final static Gson CONVERTIR = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        try {
            String texto = request.getReader().readLine();
            List<Categoria> listado = CategoriaDAO.getInstance().obtenerTodos();
            String listJSON = CONVERTIR.toJson(listado);
            out.println(listJSON); 
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
            Categoria categoria = CONVERTIR.fromJson(texto, Categoria.class);
            CategoriaDAO.getInstance().crear(categoria);
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
            CategoriaDAO.getInstance().borrar(Integer.parseInt(request.getParameter("q")));
            out.println(CONVERTIR.toJson("OK"));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
