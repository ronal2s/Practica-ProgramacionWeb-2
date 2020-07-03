package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.Product;
import com.renysdelacruz.practica2.models.Sale;
import com.renysdelacruz.practica2.models.SellProduct;
import com.renysdelacruz.practica2.models.User;

import java.util.*;

/**
 * Ejemplo de servicio patron Singleton
 */
public class FakeServices {

    private static FakeServices instancia;
    private List<Product> listProducts = new ArrayList<>();
    private List<SellProduct> listSellsProducts = new ArrayList<>();
    private List<User> listUsers = new ArrayList<>();
    private List<Sale> listSales = new ArrayList<>();
    private User currentUser;


    /**
     * Constructor privado.
     */
    private FakeServices() {

    }
    private FakeServices(int id, String name, int price){
        listProducts.add(new Product(id, name, price));
    }

    public static FakeServices getInstancia(){
        if(instancia==null){
            instancia = new FakeServices();
        }
        return instancia;
    }



    public List<Product> listProducts(){
        return listProducts;
    }

    public List<SellProduct> listSellsProducts(){
        return listSellsProducts;
    }

    public int getLastListProductId() {
        if(listProducts.size()>0) {
            return listProducts.get(listProducts.size()-1).getId();
        }
        return -1;
    }

    public int getLastListSalesId() {
        if(listSales.size()>0) {
            return listSales.get(listSales.size()-1).getId();
        }
        return -1;
    }

    public Product getProductById(int id){
        return listProducts.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public Product newProduct(Product product){
        if(getProductById(product.getId())!=null){
            System.out.println("Producto previamente registrado...");
            return null; //generar una excepcion...
        }
        listProducts.add(product);
        return product;
    }

    public SellProduct addSellProduct(SellProduct product) {
        listSellsProducts.add(product);
        return product;
    }

    public void deleteSellProduct(int id) {
        listSellsProducts.remove(getIndexSellsProducts(id));
    }

    public void clearListSellProducts() {
        listSellsProducts.clear();
    }

    public List<Sale> listSales() {
        return listSales;
    }

    public Sale newSale(Sale sale) {
        int total = 0;
        List<SellProduct> aux = new ArrayList<>();
        //Tengo que hacer esto porque ya intenté igualando aux = sale.getProducts() y al eliminar listSellProducts también se elimina dentro de Sale, no entiendo
        listSellsProducts.forEach(value -> {
            aux.add(value);
        });
        Sale auxSale = sale;
        auxSale.setProducts(aux);
        for (SellProduct product : aux) {
            total += product.getQuantity() * product.getProduct().getPrice();
//            total += 100;
        }
        auxSale.setTotal(total);
        listSales.add(auxSale);
        clearListSellProducts();
        System.out.println("Size of list: " + aux.size());
        return auxSale;
    }

    public List<Sale> getListSales() {
        return listSales;
    }



    public Product updateProduct(Product product){
        Product tmp = getProductById(product.getId());
        if(tmp == null){//no existe, no puede se actualizado
            throw new RuntimeException("No puedo actualizar");
        }
        tmp.update(product);
        return tmp;
    }

    public boolean deleteProduct(int id){
        Product tmp = new Product();
        tmp.setId(id);
        listProducts.remove(getIndexListProducts(id));
        return listProducts.remove(tmp);
    }

    private int getIndexListProducts(int id) {
        for(int i = 0; i < listProducts.size(); i++) {
            if(listProducts.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getIndexSellsProducts(int id) {
        for(int i = 0; i < listSellsProducts.size(); i++) {
            if(listSellsProducts.get(i).getProduct().getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public boolean loginUser(String username, String password) {
        if(username.equalsIgnoreCase("admin") && password.equals("admin")) {
            currentUser = new User(username, username, password);
            return true;
        }
        return false;
    }

    public boolean isUserLogged() {
        if(currentUser != null) {
            return  true;
        }
        return false;
    }

}
