package com.example.demo2.modelos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    static private String DB = "restaurantec";
    static private String USER = "admin";
    static private String PWD = "123";
    static private String HOST = "localhost";
    static private String PORT = "3306";

    public static Connection connection;

    public static void createConnection(){

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+HOST + ":"+ PORT + "/" + DB, USER, PWD);
            //socket: mecanismo de icomunicacion entre 2 aplicaciones

            System.out.println("Conexión establecida, krnl");

        } catch (Exception e){

            e.printStackTrace();
        }
    }
}
