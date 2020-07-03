package com.renysdelacruz.practica2.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Sale {
    int id;
    LocalDate date;
    String customer_name;
    List<SellProduct> products;
    int total;

    public Sale(int id, List<SellProduct> products, LocalDate date, String customer_name, int total) {
        this.id = id;
        this.products = products;
        this.date = date;
        this.customer_name = customer_name;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SellProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SellProduct> products) {
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
