package com.renysdelacruz.practica2;

import com.renysdelacruz.practica2.controllers.TemplateController;
import com.renysdelacruz.practica2.controllers.UserController;
import com.renysdelacruz.practica2.models.Cart;
import com.renysdelacruz.practica2.models.Product;
import com.renysdelacruz.practica2.models.User;
import com.renysdelacruz.practica2.services.DbConnection;
import com.renysdelacruz.practica2.services.DbManage;
import com.renysdelacruz.practica2.services.UserServices;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Se inicia la base de datos
        DbManage.getInstance().startDB();

        // Se prueba la conexion con la DB
        DbConnection.getInstance().testConn();

        // Creacion de usuarios predeterminados
        User usr1 = new User("renys", "Renys De La Cruz", "renys");
        User usr2 = new User("admin", "Administrator", "admin");
        UserServices.getInstance().crear(usr1);
        UserServices.getInstance().crear(usr2);

        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public"); //desde la carpeta de resources
            config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
        }).start(4001);

        System.out.println("Server running on: http://localhost:4001/");

        //Creacion del manejador
        app.get("/", ctx -> {
            List<Product> productosIniciales = new ArrayList<Product>();
            ctx.sessionAttribute("carrito", new Cart(1, productosIniciales));
            ctx.redirect("/buy/");
        });

        //Manejadores de rutas
        new UserController(app).aplicarRutas();
        new TemplateController(app).aplicarRutas();
    }
}