package com.renysdelacruz.practica2.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Sale")
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String clientName;
    java.util.Date saleDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SellProduct> sellList = new ArrayList<>();

    @Transient
    List<Product> productList = new ArrayList<Product>();

    //Constructors
    public Sale() { }

    public Sale(Date fechaCompra, String nombreCliente){
        this.saleDate = fechaCompra;
        this.clientName = nombreCliente;
    }

    public Sale(Date fechaCompra, String nombreCliente, List<SellProduct> listaProductos) {
        this.saleDate = fechaCompra;
        this.clientName = nombreCliente;
        this.sellList = listaProductos;
    }

    //Getters and setters
    public void setId(long id) { this.id = id; }

    public long getId() { return id; }

    public void setSaleDate(Date fechaCompra) { this.saleDate = fechaCompra; }

    public Date getSaleDate() { return saleDate; }

    public void setClientName(String nombreCliente) { this.clientName = nombreCliente; }

    public String getClientName() { return clientName; }

    public void setSellList(List<SellProduct> listaProductos) { this.sellList = listaProductos; }

    public List<SellProduct> getSellList() { return sellList; }

    public void setProductList(List<Product> lista) { this.productList = lista; }

    public List<Product> getProductList() { return productList; }

    public void agregarProducto(Product producto) {
        SellProduct prodVenta = new SellProduct(this, producto);
        sellList.add(prodVenta);
        producto.getListaVentas().add(prodVenta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale venta = (Sale) o;
        return Objects.equals(clientName, venta.clientName)
                && Objects.equals(saleDate, venta.saleDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientName, saleDate);
    }
}