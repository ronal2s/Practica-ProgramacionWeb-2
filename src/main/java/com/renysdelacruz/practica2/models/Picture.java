package com.renysdelacruz.practica2.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Picture")
@Table(name = "picture")
public class Picture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se genera el ID automatico
    private int id;

    @ManyToOne
    private Product product;

    private String mimeType;

    @Lob
    private String pictureBase64;

    public Picture() { }

    public Picture(Product product, String mimeType, String pictureBase64) {
        this.product = product;
        this.mimeType = mimeType;
        this.pictureBase64 = pictureBase64;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Product getProduct() { return product; }

    public void setProduct(Product producto) { this.product = producto; }

    public String getMimeType() { return mimeType; }

    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getPictureBase64() { return pictureBase64; }

    public void setPictureBase64(String fotoBase64) { this.pictureBase64 = fotoBase64; }
}