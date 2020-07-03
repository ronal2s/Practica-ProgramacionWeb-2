package com.renysdelacruz.practica2.controllers;

import com.renysdelacruz.practica2.models.Product;
import com.renysdelacruz.practica2.services.FakeServices;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsController {

    Javalin app;
    FakeServices fakeServices = FakeServices.getInstancia();

    public ProductsController(Javalin app) {
        this.app = app;
    }

    public void getRoutes() {
        app.before("/products", ctx -> {
           if(!fakeServices.isUserLogged()) {
               ctx.redirect("/login");
           }
        });
        app.get("/products", ctx -> {
            List<Product> listProducts = fakeServices.listProducts();
            Map<String, Object> model = new HashMap<>();
            model.put("products", listProducts);
            model.put("name", "Renys");
            ctx.render("/public/products/index.html", model);
        });

        app.get("/products/new", ctx -> {
            System.out.println("Get new product");
            ctx.render("/public/products/new.html");
        });

        app.post("/products/new", ctx -> {
            Map<String, Object> model = new HashMap<>();
            int id = fakeServices.getLastListProductId() + 1;
            String name = ctx.formParam("name");
            int price = Integer.parseInt(ctx.formParam("price"));
            Product newProduct = fakeServices.newProduct(new Product(id, name, price));
            System.out.print("Product created: " + newProduct);
            ctx.redirect("/products");
            ctx.render("/public/products/new.html", model);
        });

        app.post( "/products/delete", ctx -> {
//            System.out.println("ID: " + ctx.formParam("id"));
            int id = Integer.parseInt(ctx.formParam("id"));
            fakeServices.deleteProduct(id);
            ctx.redirect("/products");
        });

        app.post("/products/edit", ctx -> {
            int id = Integer.parseInt(ctx.formParam("id"));
            String name = ctx.formParam("name");
            String price = ctx.formParam("price");
            if(name != null && price != null) {
                //Lets update
                Product updateProduct = new Product(id, name, Integer.parseInt(price));
                fakeServices.updateProduct(updateProduct);
                ctx.redirect("/products");
            } else {
                //Lets open edit view
                Product product = fakeServices.getProductById(id);
                Map<String, Object> model = new HashMap<>();
                model.put("product", product);
                ctx.render("/public/products/edit.html", model);
            }
        });
    }
}
