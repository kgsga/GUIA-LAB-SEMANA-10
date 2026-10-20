
package UTIL;
import java.sql.*;
public class ConexionBD {
    public static void main (String[] args){
        ConexionBD OBJ=new ConexionBD();
        OBJ.getConexionBD();
    }
    public Connection getConexionBD(){
        Connection cn=null;
        try{
        Class.forName("com.mysql.jdbc.Driver");
        cn=DriverManager.getConnection("jdbc:mysql://localhost/bdmant","root","");
        System.out.println("SE CONECTO");     
    }catch (Exception e){
    System.out.println("NO SE CONECTO");
}
    return cn;
}
    
}
