package com.renysdelacruz.practica2.controllers;

import com.renysdelacruz.practica2.models.*;
import com.renysdelacruz.practica2.services.ShopServices;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class TemplateController {
    private Javalin app;
    public TemplateController(Javalin app){
        this.app = app;
    }

    //Obtencion de instacia de tienda
    ShopServices tienda = ShopServices.getInstance();

    //Registro de sistemas de plantillas
    private void registroPlantillas() {
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".html");
    }

    //Variables usadas en varias rutas
    Cart carrito;
    User usuarioSesion;

    //Uso de rutas para mostrar templates
    public void aplicarRutas() {
        app.routes(() -> {

            // Verificar si el carrito existe en la sesion antes de cargar
            before(ctx -> {
                usuarioSesion = (User) tienda.getSessions().get(ctx.req.getSession().getId());
                carrito = ctx.sessionAttribute("carrito");
                if(carrito == null) {
                    List<Product> productosIniciales = new ArrayList<Product>();
                    ctx.sessionAttribute("carrito", new Cart(1, productosIniciales));
                }
                if(usuarioSesion != null && usuarioSesion.getUser().equals("admin")){
                    tienda.setAdm(true);
                    tienda.setUsr(false);
                } else if(usuarioSesion != null && tienda.getUsuarioPorNombreUsuario(usuarioSesion.getUser()) != null){
                    tienda.setAdm(false);
                    tienda.setUsr(true);
                } else {
                    tienda.setAdm(false);
                    tienda.setUsr(false);
                }
            });

            path("/", () -> {

                get("products/new", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "New Product");
                    contexto.put("productos", tienda.getListaProductos());
                    contexto.put("cantidad", carrito.getProductList().size());
                    contexto.put("usr", tienda.isUsr());
                    contexto.put("admin", tienda.isAdm());
                    contexto.put("usuario", usuarioSesion);
                    ctx.render("/public/products/new.ftl", contexto);
                });

                post("products/new", ctx -> {
                    //Obtener informacion de la form
                    String nombre = ctx.formParam("name");
                    BigDecimal precio = new BigDecimal(ctx.formParam("price"));
                    String descripcion = ctx.formParam("description");
                    Product tmp = new Product(nombre, precio, descripcion);
                    Product producto = tienda.insertarProductoDB(tmp);
                    ctx.uploadedFiles("pictures").forEach(uploadedFile -> {
                        try{
                            byte[] bytes = uploadedFile.getContent().readAllBytes();
                            String encondedString = Base64.getEncoder().encodeToString(bytes);
                            tienda.insertarFotoProducto(new Picture(producto, uploadedFile.getContentType(), encondedString));
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    });
                    ctx.redirect("/products");
                });

                get("/products/edit/:id", ctx -> {
                    Product tmp = tienda.getProductoPorID(ctx.pathParam("id", Integer.class).get());
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Edit Product");
                    contexto.put("product", tmp);
                    contexto.put("cantidad", carrito.getProductList().size());
                    contexto.put("usr", tienda.isUsr());
                    contexto.put("admin", tienda.isAdm());
                    contexto.put("usuario", usuarioSesion);
                    ctx.render("/public/products/edit.ftl", contexto);
                });

                post("/products/edit/:id", ctx -> {
                    int id = ctx.pathParam("id", Integer.class).get();
                    String nombre = ctx.formParam("name");
                    BigDecimal precio = new BigDecimal(ctx.formParam("price"));
                    Product producto = new Product(id, nombre, precio);
                    tienda.actualizarProducto(producto);
                    ctx.redirect("/products");
                });

                get("/products/delete/:id", ctx -> {
                    Product tmp = tienda.getProductoPorID(ctx.pathParam("id", Integer.class).get());
                    tienda.eliminarProducto(tmp);
                    ctx.redirect("/products");
                });

                get("/products/view/:id", ctx -> {
                    Product prod = tienda.getProductoPorID(ctx.pathParam("id", Integer.class).get());
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("product", prod);
                    contexto.put("comentarios", prod.getListaComments());
                    contexto.put("pictures", prod.getListaFotos());
                    contexto.put("title", "View Product");
                    contexto.put("cantidad", carrito.getProductList().size());
                    contexto.put("usr", tienda.isUsr());
                    contexto.put("admin", tienda.isAdm());
                    contexto.put("usuario", usuarioSesion);
                    ctx.render("public/products/view.ftl", contexto);
                });

                post("/products/comment/:id", ctx -> {
                    Product prod = tienda.getProductoPorID(ctx.pathParam("id", Integer.class).get());
                    Comment comment = new Comment(ctx.formParam("comment"), usuarioSesion.getUser(), prod);
                    tienda.insertarComentario(comment);
                    ctx.redirect("/products/view/" + ctx.pathParam("id", Integer.class).get());
                });

                get("/products/comment/delete/:id", ctx -> {
                    Comment comentario = tienda.getComentarioPorID(ctx.pathParam("id", Integer.class).get());
                    tienda.eliminarComentario(comentario);
                    ctx.redirect("/products");
                });

                get("/products", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Product List");
                    contexto.put("products", tienda.getListaProductos());
                    contexto.put("cantidad", carrito.getProductList().size());
                    contexto.put("usr", tienda.isUsr());
                    contexto.put("admin", tienda.isAdm());
                    contexto.put("usuario", usuarioSesion);
                    ctx.render("/public/products/index.ftl", contexto);
                });

                get("/buy", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Product List");
                    contexto.put("products", tienda.getListaProductos());
                    contexto.put("cantidad", carrito.getProductList().size());
                    contexto.put("usr", tienda.isUsr());
                    contexto.put("admin", tienda.isAdm());
                    contexto.put("usuario", usuarioSesion);
                    ctx.render("/public/buy/index.ftl", contexto);
                });

                get("/cart/", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Shopping Cart");
                    contexto.put("carrito", carrito);
                    contexto.put("cantidad", carrito.getProductList().size());
                    contexto.put("usr", tienda.isUsr());
                    contexto.put("admin", tienda.isAdm());
                    contexto.put("usuario", usuarioSesion);
                    ctx.render("/public/cart/index.ftl", contexto);
                });

                post("/cart/new/:id", ctx -> {
                    Product preprod = tienda.getProductoPorID(ctx.pathParam("id", Integer.class).get());
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    Product producto = new Product(preprod.getId(), preprod.getName(), preprod.getPrice(), cantidad);
                    carrito.getProductList().add(producto);
                    ctx.redirect("/buy");
                });

                get("/cart/clean", ctx -> {
                    tienda.setCarrito(carrito);
                    tienda.limpiarCarrito();
                    ctx.sessionAttribute("carrito", tienda.getCarrito());
                    ctx.redirect("/cart");
                });

                get("/cart/delete/:id", ctx -> {
                    tienda.setCarrito(carrito);
                    Product tmp = tienda.getProductoEnCarrito(ctx.pathParam("id", Integer.class).get());
                    tienda.getCarrito().borrarProducto(tmp);
                    ctx.sessionAttribute("carrito", tienda.getCarrito());
                    ctx.redirect("/cart");
                });

                post("/cart/sale/", ctx -> {
                    tienda.setCarrito(carrito);
                    String nombreCliente = ctx.formParam("name");
                    Sale venta = new Sale(new Date(), nombreCliente);
                    tienda.procesarVenta(venta, tienda.getCarrito().getProductList());
                    tienda.limpiarCarrito();
                    ctx.sessionAttribute("carrito", tienda.getCarrito());
                    ctx.redirect("/cart");
                });

                get("/sales", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Sales List");
                    contexto.put("sales", tienda.getListaVentas());
                    contexto.put("cantidad", carrito.getProductList().size());
                    contexto.put("usr", tienda.isUsr());
                    contexto.put("admin", tienda.isAdm());
                    contexto.put("usuario", usuarioSesion);
                    ctx.render("/public/sales/index.ftl", contexto);
                });
            });
        });
    }
}
