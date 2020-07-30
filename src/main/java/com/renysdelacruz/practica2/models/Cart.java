package com.renysdelacruz.practica2.models;

import java.util.List;

public class Cart {
    long id;
    List<Product> productList;

    //Constructors
    public Cart() { }

    public Cart(long id, List<Product> productList) {
        this.id = id;
        this.productList = productList;
    }

    //Getters and setters
    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public List<Product> getProductList() { return productList; }

    public void setProductList(List<Product> productList) { this.productList = productList; }

    public boolean borrarProducto(Product producto){ return productList.remove(producto); }
}