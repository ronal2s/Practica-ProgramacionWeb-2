package com.renysdelacruz.practica2.models;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "SellProduct")
@Table(name = "sell_product")
public class SellProduct {

    @EmbeddedId
    private SellProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("saleId")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    protected SellProduct() {}

    public SellProduct(Sale sale, Product product) {
        this.sale = sale;
        this.product = product;
        this.quantity = product.getQuantity();
        this.id = new SellProductId(sale.getId(), product.getId());
    }

    // Getters and Setters

    public SellProductId getId() {
        return id;
    }

    public void setId(SellProductId id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale venta) {
        this.sale = venta;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        SellProduct that = (SellProduct) o;
        return Objects.equals(sale, that.sale) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, product);
    }
}