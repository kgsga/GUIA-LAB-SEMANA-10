package DAO;

import BEAN.Persona;
import UTIL.ConexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PersonaDAO")
public class PersonaDAO extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        listarPersonas(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        try {
            if ("agregar".equals(accion)) {
                guardarDatos(request);
            } else if ("modificar".equals(accion)) {
                modificarDatos(request);
            } else if ("eliminar".equals(accion)) {
                eliminarDatos(request);
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la operación", e);
        }

        listarPersonas(request, response);
    }

    private void listarPersonas(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Persona> lista = new ArrayList<>();

        try {
            ConexionBD objC = new ConexionBD();

            try (Connection cn = objC.getConexionBD();
                 PreparedStatement pt = cn.prepareStatement(
                     "SELECT * FROM persona");
                 ResultSet rs = pt.executeQuery()) {

                while (rs.next()) {
                    Persona p = new Persona();
                    p.setCodigo(rs.getInt(1));
                    p.setNombre(rs.getString(2));
                    p.setApellido(rs.getString(3));
                    lista.add(p);
                }
            }

            request.setAttribute("lista", lista);
            request.getRequestDispatcher("/VentanaMantenimiento.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error al listar personas", e);
        }
    }

    private void guardarDatos(HttpServletRequest request) throws Exception {
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");

        ConexionBD objC = new ConexionBD();

        try (Connection cn = objC.getConexionBD();
             PreparedStatement pt = cn.prepareStatement(
                 "INSERT INTO persona (codigo, nombre, apellido) VALUES (?, ?, ?)")) {

            pt.setInt(1, codigo);
            pt.setString(2, nombre);
            pt.setString(3, apellido);
            pt.executeUpdate();
        }
    }

    private void modificarDatos(HttpServletRequest request) throws Exception {
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");

        ConexionBD objC = new ConexionBD();

        try (Connection cn = objC.getConexionBD();
             PreparedStatement pt = cn.prepareStatement(
                 "UPDATE persona SET nombre = ?, apellido = ? WHERE codigo = ?")) {

            pt.setString(1, nombre);
            pt.setString(2, apellido);
            pt.setInt(3, codigo);
            pt.executeUpdate();
        }
    }

    private void eliminarDatos(HttpServletRequest request) throws Exception {
        int codigo = Integer.parseInt(request.getParameter("codigo"));

        ConexionBD objC = new ConexionBD();

        try (Connection cn = objC.getConexionBD();
             PreparedStatement pt = cn.prepareStatement(
                 "DELETE FROM persona WHERE codigo = ?")) {

            pt.setInt(1, codigo);
            pt.executeUpdate();
        }
    }
}
