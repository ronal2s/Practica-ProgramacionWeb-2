package com.renysdelacruz.practica2.controllers;

import com.renysdelacruz.practica2.models.Product;
import com.renysdelacruz.practica2.models.SellProduct;
import com.renysdelacruz.practica2.services.FakeServices;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyProductsController {

    Javalin app;
    FakeServices fakeServices = FakeServices.getInstancia();

    public BuyProductsController(Javalin app) {
        this.app = app;
    }

    public void getRoutes() {
        app.before("/buy", ctx -> {
            if(!fakeServices.isUserLogged()) {
                ctx.redirect("/login");
            }
        });
        app.get("/buy", ctx -> {
            List<Product> listProducts = fakeServices.listProducts();
            Map<String, Object> model = new HashMap<>();
            model.put("products", listProducts);
            List<SellProduct> listSellProducts = fakeServices.listSellsProducts();
            model.put("sellProducts", listSellProducts);
            ctx.render("/public/buy/index.html", model);
        });

        app.get("/buy/success", ctx -> {
            ctx.render("/public/buy/success.html");
        });

        app.post("/buy/add", ctx -> {
            int id = Integer.parseInt(ctx.formParam("id"));
            int quantity = Integer.parseInt(ctx.formParam("quantity"));
            Product auxProduct = fakeServices.getProductById(id);
            SellProduct product = new SellProduct(quantity, auxProduct);
            fakeServices.addSellProduct(product);

            System.out.println("New sellproduct added, quantity: " + product.getQuantity());
            ctx.redirect("/buy/success");
        });
    }
}
