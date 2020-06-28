package com.renysdelacruz.practica2;

//import edu.pucmm.eict.controladores.*;
import com.renysdelacruz.practica2.models.SellProduct;
import com.renysdelacruz.practica2.services.FakeServices;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import com.renysdelacruz.practica2.controllers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        FakeServices fakeServices = FakeServices.getInstancia();

        try {
            Connection conn = null;
            Statement stmt = null;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            stmt = conn.createStatement();
            String sql =  "CREATE TABLE IF NOT EXISTS ESTUDIANTE" +
                    "(matricula INTEGER not NULL, " +
                    " nombre VARCHAR(255), " +
                    " carrera VARCHAR(255), " +
                    " PRIMARY KEY ( matricula ))";
            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql =  "CREATE TABLE IF NOT EXISTS USER" +
                    "(user VARCHAR(255), " +
                    "name VARCHAR(255), " +
                    "password VARCHAR(255)) ";
            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql =  "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(id INTEGER not NULL, " +
                    "name VARCHAR(255), " +
                    "price INTEGER ," +
                    "PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql =  "CREATE TABLE IF NOT EXISTS SALE" +
                    "(id INTEGER not NULL, " +
                    "date DATE, " +
                    "total INTEGER, "+
                    "name VARCHAR(255), "+
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(sql);

            // para el historial de compras
            stmt = conn.createStatement();
            sql =  "CREATE TABLE IF NOT EXISTS SELLPRODUCT" +
                    "(quantity INTEGER, " +
                    "product INTEGER, " +
                    "saleId Integer)";
            stmt.executeUpdate(sql);
//                  "FOREIGN KEY(product) REFERENCES PRODUCT(id),"+
//                   "FOREIGN KEY(sale) REFERENCES SALE(id))";


            // para el carro de compras
            stmt = conn.createStatement();
            sql =  "CREATE TABLE IF NOT EXISTS CART" +
                    "(quantity INTEGER, " +
                    "product INTEGER)";


            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/public"); //desde la carpeta de resources
            config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
        }).start(4001);

        //creando el manejador
        app.get("/", ctx -> {
            if(fakeServices.isUserLogged()) {
                ctx.redirect("/products");
            } else {
                ctx.redirect("/login");
            }
        });
        new UserController(app).aplicarRutas();
        new ProductsController(app).getRoutes();
        new BuyProductsController(app).getRoutes();
        new CartController(app).getRoutes();
        new SalesController(app).getRoutes();

    }

}