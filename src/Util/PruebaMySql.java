package Util;

/*
 * PruebaMySQL.java
 *
 * Programa de prueba para conexi�n a una base de datos de MySQL.
 * Presupone que el servidor de base de datos est� arrancado, disponible,
 * en el puerto por defecto.
 * El usuario y password de conexi�n con la base de datos debe cambiarse.
 * En la base de datos se supone que hay una base de datos llamada prueba y que
 * tiene una tabla persona con tres campos, de esta manera:
 * mysql> create database prueba;
 * mysql> use prueba;
 * mysql> create table persona (id smallint auto_increment, nombre varchar(60), 
 *      nacimiento date, primary key(id)); 
 */


import java.sql.*;

/**
 * Clase de prueba de conexi�n con una base de datos MySQL
 */
public class PruebaMySql {
    
    /** 
     * Crea una instancia de la clase MySQL y realiza todo el c�digo 
     * de conexi�n, consulta y muestra de resultados.
     */
    public PruebaMySql() 
    {
        // Se mete todo en un try por los posibles errores de MySQL
        try
        {
            // Se registra el Driver de MySQL
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            
            // Se obtiene una conexi�n con la base de datos. Hay que
            // cambiar el usuario "root" y la clave "la_clave" por las
            // adecuadas a la base de datos que estemos usando.
            Connection conexion = DriverManager.getConnection (
                "jdbc:mysql://localhost/segadental","root", "123456");
            
            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement();
            
            // Se realiza la consulta. Los resultados se guardan en el 
            // ResultSet rs
            ResultSet rs = s.executeQuery ("select * from user");
            
            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (rs.next())
            {
                System.out.println (rs.getInt ("Id") + " " + rs.getString (2)+ 
                    " " + rs.getDate(3));
            }
            
            // Se cierra la conexi�n con la base de datos.
            conexion.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * M�todo principal, instancia una clase PruebaMySQL
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new PruebaMySql();
    }
    
}