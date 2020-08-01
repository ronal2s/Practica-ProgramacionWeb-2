package com.renysdelacruz.practica2.models;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Product")
@Table(name = "product")
@NaturalIdCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NaturalId(mutable = true)
    private String name;

    private BigDecimal price;
    private int quantity;
    private String description;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<SellProduct> listaVentas = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> listaComments;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Picture> listaFotos;

    //Constructors
    public Product() { }

    //Constructor sin cantidad (para agregar al listado)
    public Product(int id, String nombre, BigDecimal precio) {
        this.id = id;
        this.name = nombre;
        this.price = precio;
    }

    public Product(String nombre, BigDecimal precio, String descripcion) {
        this.name = nombre;
        this.price = precio;
        this.description = descripcion;
    }

    //Constructor con cantidad (para agregar a carrito y posteriormente a ventas)
    public Product(int id, String nombre, BigDecimal precio, int cantidad) {
        this.id = id;
        this.name = nombre;
        this.price = precio;
        this.quantity = cantidad;
    }

    //Getters and setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String nombre) { this.name = nombre; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal precio) { this.price = precio; }

    public void setQuantity(int cantidad) { this.quantity = cantidad; }

    public int getQuantity() { return quantity; }

    public String getDescription() { return description; }

    public void setDescription(String descripcion) { this.description = descripcion; }

    public List<SellProduct> getListaVentas() { return listaVentas; }

    public void setListaVentas(List<SellProduct> listaVentas) { this.listaVentas = listaVentas; }

    public Set<Comment> getListaComments() { return listaComments; }

    public void setListaComments(Set<Comment> listaComments) { this.listaComments = listaComments; }

    public Set<Picture> getListaFotos() { return listaFotos; }

    public void setListaFotos(Set<Picture> listaFotos) { this.listaFotos = listaFotos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product producto = (Product) o;
        return Objects.equals(name, producto.name) &&
                Objects.equals(price, producto.price) &&
                Objects.equals(quantity, producto.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity);
    }
}