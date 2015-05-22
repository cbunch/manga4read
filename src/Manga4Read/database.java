package Manga4Read;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tenshi
 */
public class database {
    public static String nombreDB;
    public static String Direccion;
    public static void crear(){
        Direccion = System.getProperty("user.dir");
        System.out.println(Direccion);
        Direccion=Direccion+"/";
        nombreDB= "mangas.db";
        try {
            Class.forName("org.sqlite.JDBC");//especificamos el driver
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB); //creamos la conexión, si la BD no existe la crea
            java.sql.Statement stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS temporal(Link TEXT, Archivo TEXT, Estado TXT)");
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS cola(Nombre TEXT, Capitulo TEXT,Paginas NUMBER, Estado TEXT, Imagen TEXT, Extension TEXT)");
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS suscritos(Nombre TEXT, Actual TEXT, Ultimo TXT)");
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS configuracion(so TXT,auto TEXT, direccion TEXT)");
            System.out.println("Base de datos creada o actualizada");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido crear la base de datos");
        }
    }
    
    public static void insertar(String consulta){
//        System.out.println(consulta);
        try {
            nombreDB= "mangas.db";
            Class.forName("org.sqlite.JDBC");//especificamos el driver
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB); //creamos la conexión, si la BD no existe la crea
            java.sql.PreparedStatement stat = conn.prepareStatement(consulta);
            stat.execute();
            stat.close();
            //System.out.println("Registro ingresado o modificado con exito");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido modificar la base de datos");
        }
    }
    
    
    public static String consultarUno(String consulta){
        //consulta="select Nombre from ";
//        System.out.println(consulta);
        nombreDB= "mangas.db";
        String out="";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+nombreDB); //creamos la conexión, si la BD no existe la crea
            java.sql.PreparedStatement stat = conn.prepareStatement(consulta);
            ResultSet rs = stat.executeQuery();
            out=rs.getString(1);
            rs.close();
            stat.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }
}

