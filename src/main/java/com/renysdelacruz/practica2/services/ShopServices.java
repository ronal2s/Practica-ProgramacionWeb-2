package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopServices {

    private static ShopServices shop;
    private Cart carrito;
    private boolean adm;
    private boolean usr;
    private HashMap<String,Object> sessions = new HashMap<>();
    private HashMap<String,Object> cookies = new HashMap<>();
    private ProductServices products = ProductServices.getInstance();
    private PictureServices pictures = PictureServices.getInstance();
    private UserServices users = UserServices.getInstance();
    private SaleServices sales = SaleServices.getInstance();
    private Between intermediary = Between.getInstance();
    private CommentServices comments = CommentServices.getInstance();

    private ShopServices(){

    }

    public static ShopServices getInstance(){
        if(shop == null){
            shop = new ShopServices();
        }
        return shop;
    }

    public List<Product> getListaProductos(){
        return products.findAll();
    }

    public List<Sale> getListaVentas(){
        List<Sale> sales = this.sales.findAll();
        for(int i = 0; i < sales.size(); i++){
            List<SellProduct> relacion = intermediary.findByVenta(sales.get(i).getId());
            for(int j=0; j < relacion.size(); j++){
                Product prod = products.findByID(relacion.get(j).getProduct().getId()).get(0);
                prod.setQuantity(relacion.get(j).getQuantity());
                sales.get(i).getProductList().add(prod);
            }
        }
        return sales;
    }

    public Cart getCarrito() { return carrito; }

    public void setCarrito(Cart cart) { this.carrito = cart; }

    public HashMap<String,Object> getSessions() { return sessions; }

    public boolean isAdm() { return adm; }

    public void setAdm(boolean adm) { this.adm = adm; }

    public boolean isUsr() { return usr; }

    public void setUsr(boolean usr) { this.usr = usr; }

    public Product getProductoPorID(int id){
        return products.findByID(id).get(0);
    }

    public Product insertarProductoDB(Product producto){
        return products.crear(producto);
    }

    public void actualizarProducto(Product producto) {
        products.editar(producto);
    }

    public void eliminarProducto(Product producto){
        products.eliminar(producto.getId());
    }

    public Comment getComentarioPorID(int id) { return comments.findById(id).get(0); }

    public List<Comment> getComentariosPorProducto(int id) { return comments.findByProductoId(id); }

    public void insertarComentario(Comment comentario) { comments.crear(comentario); }

    public void eliminarComentario(Comment comentario) { comments.eliminar(comentario.getId()); }

    public void insertarFotoProducto(Picture foto) { pictures.crear(foto); }

    public Product getProductoEnCarrito(int id){
        return carrito.getProductList().stream().filter(producto -> producto.getId() == id).findFirst().orElse(null);
    }

    public void limpiarCarrito(){
        List<Product> tmp = new ArrayList<Product>();
        carrito.setProductList(tmp);
    }

    public void procesarVenta(Sale vta, List<Product> listaProductos){
        Sale entidadVenta = sales.crear(vta);
        for(int i=0; i<listaProductos.size(); i++){
            SellProduct relacion = new SellProduct(entidadVenta, listaProductos.get(i));
            intermediary.insertarRelacion(relacion);
        }
    }

    public User getUsuarioPorNombreUsuario(String usr){
        return users.findByUsername(usr).get(0);
    }

    public List<User> getListaUsuarios() {
        return users.findAll();
    }

    public User revisarCookie(String cookie){
        User usuario = null;
        if(cookies.containsKey(cookie)){
            usuario = (User) cookies.get(cookie);
        }
        return usuario;
    }

    public User loginUsuario(String usuario, String passw, String sessionId, String cookie){
        User tmp = getUsuarioPorNombreUsuario(usuario);
        if(tmp == null) {
            throw new RuntimeException("User does not exist!");
        } else if(usuario.equals("admin") && passw.equals("admin")) {
            sessions.put(sessionId, tmp);
            cookies.put(cookie, tmp);
            return tmp;
        } else if(tmp.getUser().equals(usuario) && tmp.getPassword().equals(passw)) {
            sessions.put(sessionId, tmp);
            cookies.put(cookie, tmp);
            return tmp;
        } else throw new RuntimeException("Wrong password!");
    }

    public void logoutUsuario(String sessionId, String cookie) {
        sessions.remove(sessionId);
        cookies.remove(cookie);
    }
}