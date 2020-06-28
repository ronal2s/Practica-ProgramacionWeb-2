package com.renysdelacruz.practica2.controllers;

import com.renysdelacruz.practica2.models.Sale;
import com.renysdelacruz.practica2.models.SellProduct;
import com.renysdelacruz.practica2.services.FakeServices;
import io.javalin.Javalin;

import java.util.*;
import java.util.stream.Collectors;

public class CartController {

    Javalin app;
    FakeServices fakeServices = FakeServices.getInstancia();

    public CartController(Javalin app) {
        this.app = app;
    }

    public void getRoutes() {
        app.before("/cart", ctx -> {
            if(!fakeServices.isUserLogged()) {
                ctx.redirect("/login");
            }
        });
        app.get("/cart", ctx -> {
            List<SellProduct> listProducts = fakeServices.listCartProducts();
            Map<String, Object> model = new HashMap<>();
            model.put("sales", listProducts);
            ctx.render("/public/cart/index.html", model);
        });

        app.post("/cart/delete", ctx -> {
           int id = Integer.parseInt(ctx.formParam("id"));
           fakeServices.deleteCart(id);
           ctx.redirect("/cart");
        });

        app.post("/cart/confirm", ctx -> {
            String customer = ctx.formParam("customer");
            int id = fakeServices.getLastListSalesId();
            List<SellProduct> listProducts = fakeServices.listCartProducts();
            int total = 0;
            for (SellProduct listProduct : listProducts) {
                total += listProduct.getQuantity() * listProduct.getProduct().getPrice();
            }
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Sale sale = new Sale(id, listProducts, sqlDate, customer, total);
            fakeServices.newSale(sale);
            fakeServices.emptyCart();
            ctx.redirect("/buy");
        });

        app.post("/cart/clear", ctx -> {
            fakeServices.emptyCart();
            ctx.redirect("/cart");
        });
    }
}
