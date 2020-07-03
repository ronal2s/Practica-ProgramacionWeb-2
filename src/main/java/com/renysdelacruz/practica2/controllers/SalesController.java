package com.renysdelacruz.practica2.controllers;

import com.renysdelacruz.practica2.models.Sale;
import com.renysdelacruz.practica2.models.SellProduct;
import com.renysdelacruz.practica2.services.FakeServices;
import io.javalin.Javalin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesController {

    Javalin app;
    FakeServices fakeServices = FakeServices.getInstancia();

    public SalesController(Javalin app) {
        this.app = app;
    }

    public void getRoutes() {
        app.before("/sales", ctx -> {
            if(!fakeServices.isUserLogged()) {
                ctx.redirect("/login");
            }
        });
        app.get("/sales", ctx -> {
            List<Sale> sales = fakeServices.listSales();
            Map<String, Object> model = new HashMap<>();
            model.put("sales", sales);
//            System.out.println("Total 0: " + sales.get(0).getTotal());
            ctx.render("/public/sales/index.html", model);
        });


    }
}
