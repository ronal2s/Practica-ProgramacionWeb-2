package com.renysdelacruz.practica2.models;

import java.util.Date;
import java.util.List;

public class SellProduct implements Cloneable {
    Product product;
    int quantity;

    public SellProduct(int quantity, Product product) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }
}
