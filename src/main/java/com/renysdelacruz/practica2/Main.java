package com.renysdelacruz.practica2;

//import edu.pucmm.eict.controladores.*;
import com.renysdelacruz.practica2.services.FakeServices;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import com.renysdelacruz.practica2.controllers.*;

public class Main {


    public static void main(String[] args) {
        FakeServices fakeServices = FakeServices.getInstancia();

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
