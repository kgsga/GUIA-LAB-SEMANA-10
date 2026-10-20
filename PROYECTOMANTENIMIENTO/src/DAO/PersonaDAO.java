package DAO;

import java.sql.*;
import BEAN.*;
import java.util.*;
import UTIL.*;

public class PersonaDAO {

    ArrayList<Persona> lista = null;
    Persona objPersona = null;
    Connection cn = null;
    PreparedStatement pt = null;
    ResultSet rs = null;

    public ArrayList<Persona> cargarLista() {
        try {
            ConexionBD objC = new ConexionBD();
            cn = objC.getConexionBD();
            pt = cn.prepareStatement("select * from persona");
            rs = pt.executeQuery();
            lista = new ArrayList<Persona>();

            while (rs.next()) {
                objPersona = new Persona();
                objPersona.setCodigo(rs.getInt(1));
                objPersona.setNombre(rs.getString(2));
                objPersona.setApellido(rs.getString(3));
                lista.add(objPersona);
            }
        } catch (Exception e) {
        }

        return lista;
    }

    public int guardarDatos(Persona objPersona) {
        int i = 0;

        try {
            ConexionBD objC = new ConexionBD();
            cn = objC.getConexionBD();

            pt = cn.prepareStatement(
                "insert into persona values(?,?,?)"
            );

            pt.setInt(1, objPersona.getCodigo());
            pt.setString(2, objPersona.getNombre());
            pt.setString(3, objPersona.getApellido());

            i = pt.executeUpdate();

            pt.close();
            cn.close();

        } catch (Exception e) {
            i = 0;
        }

        return i;
    }

    public int eliminarDatos(Persona objPersona) {
        int i = 0;

        try {
            ConexionBD objC = new ConexionBD();
            cn = objC.getConexionBD();

            pt = cn.prepareStatement(
                "delete from persona where codigo=?"
            );

            pt.setInt(1, objPersona.getCodigo());
            i = pt.executeUpdate();

            pt.close();
            cn.close();

        } catch (Exception e) {
            i = 0;
        }

        return i;
    }
    public int modificarDatos(Persona objPersona) {
        int i = 0;

        try {
            ConexionBD objC = new ConexionBD();
            cn = objC.getConexionBD();
            pt = cn.prepareStatement(
                "update persona set nombre=?,apellido=? where codigo=?"
            );

            pt.setString(1, objPersona.getNombre());
            pt.setString(2, objPersona.getApellido());
            pt.setInt(3, objPersona.getCodigo());
            i = pt.executeUpdate();

            pt.close();
            cn.close();

        } catch (Exception e) {
            i = 0;
        }

        return i;
    }
}