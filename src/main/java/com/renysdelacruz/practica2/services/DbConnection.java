package com.renysdelacruz.practica2.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection conn;
    private String URL = "jdbc:h2:tcp://localhost/~/practica4";

    private DbConnection() { registerDriver(); }

    public static DbConnection getInstance() {
        if(conn == null){
            conn = new DbConnection();
        }
        return conn;
    }

    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void testConn() {
        try {
            getConn().close();
            System.out.println("Conexión exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
