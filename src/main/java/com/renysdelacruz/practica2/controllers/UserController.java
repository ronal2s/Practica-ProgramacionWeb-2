package com.renysdelacruz.practica2.controllers;

import com.renysdelacruz.practica2.models.Cart;
import com.renysdelacruz.practica2.models.User;
import com.renysdelacruz.practica2.services.ShopServices;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    private Javalin app;
    public UserController(Javalin app){
        this.app = app;
    }

    //Registro de sistemas de plantillas
    private void registroPlantillas() {
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }

    //Obtencion de instacia de tienda
    ShopServices shop = ShopServices.getInstance();

    public void aplicarRutas() {
        app.routes(() -> {

            before(ctx -> {
                if(ctx.cookie("remember") != null){
                    User usrCookie = shop.revisarCookie(ctx.cookie("remember"));
                    if(usrCookie != null){
                        shop.loginUsuario(usrCookie.getUser(), usrCookie.getPassword(), ctx.req.getSession().getId(), ctx.cookie("remember"));
                    }
                }
            });

            path("/", () -> {

                get("login/", ctx -> {
                    if(shop.isUsr() || shop.isAdm()) {
                        ctx.redirect("/");
                    }
                    Cart carrito = ctx.sessionAttribute("carrito");
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("usr", shop.isUsr());
                    contexto.put("admin", shop.isAdm());
                    contexto.put("title", "Login de Usuario");
                    contexto.put("cantidad", carrito.getProductList().size());
                    ctx.render("/public/login/index.ftl", contexto);
                });

                post("login/", ctx -> {
                    String usr = ctx.formParam("username");
                    String passw = ctx.formParam("password");
                    String check = ctx.formParam("remember");
                    String encryptedPassword = null;
                    ctx.req.changeSessionId();
                    if(check != null && check.equals("yes")){
                        String key = "s@f3w0rd" + ctx.req.getSession().getId();
                        StrongPasswordEncryptor pwEncryptor = new StrongPasswordEncryptor();
                        encryptedPassword = pwEncryptor.encryptPassword(key);
                        ctx.cookie("remember", encryptedPassword, 604800);
                    }
                    User tmp = shop.loginUsuario(usr, passw, ctx.req.getSession().getId(), encryptedPassword);
                    ctx.redirect("/");
                });

                get("logout/", ctx -> {
                    shop.logoutUsuario(ctx.req.getSession().getId(), ctx.cookie("remember"));
                    ctx.req.getSession().invalidate();
                    ctx.removeCookie("remember");
                    ctx.redirect("/login/");
                });
            });
        });
    }
}