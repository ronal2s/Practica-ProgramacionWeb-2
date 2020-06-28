package com.renysdelacruz.practica2.models;

import java.util.Date;
import java.util.List;

public class SellProduct implements Cloneable {

    Product product;
    int quantity;
    int saleId;

    public SellProduct(int quantity, Product product) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
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
