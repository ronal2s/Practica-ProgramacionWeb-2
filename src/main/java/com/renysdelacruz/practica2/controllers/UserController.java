package com.renysdelacruz.practica2.controllers;

import com.renysdelacruz.practica2.models.Product;
import com.renysdelacruz.practica2.services.FakeServices;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import io.javalin.plugin.rendering.template.JavalinVelocity;

import java.util.HashMap;
import java.util.List;

public class UserController {

    Javalin app;
    FakeServices fakeServices = FakeServices.getInstancia();


    public UserController(Javalin app) {
        this.app = app;
//        registrandoPlantillas();
    }


    public void aplicarRutas() {

        app.post("/login", ctx -> {
            //Obteniendo la informacion de la petion. Pendiente validar los parametros.
            String nombreUsuario = ctx.formParam("username");
            String password = ctx.formParam("password");
            if(fakeServices.loginUser(nombreUsuario, password)) {
                ctx.redirect("/products");
            } else {
                HashMap<String, Object> model = new HashMap<>();
                model.put("error", "Username or password are wrong");
                ctx.render("/public/login/index.html", model);
            }
            //Autenticando el usuario para nuestro ejemplo siempre da una respuesta correcta.
//            Usuario usuario = FakeServices.getInstancia().autheticarUsuario(nombreUsuario, password);
            //agregando el usuario en la session... se puede validar si existe para solicitar el cambio.-
//            ctx.sessionAttribute("usuario", usuario);
            //redireccionando la vista con autorizacion.
//            ctx.redirect("/products");
//            System.out.format("Usuario %s y Clave %s", nombreUsuario, password);
        });
    }

    private void registrandoPlantillas(){
        //registrando los sistemas de plantilla.
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
        JavalinRenderer.register(JavalinVelocity.INSTANCE, ".vm");
    }
}
