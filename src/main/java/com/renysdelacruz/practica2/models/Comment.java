package com.renysdelacruz.practica2.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "Comment")
@Table(name = "comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private Date creationDate;
    private String userComment;

    @ManyToOne
    private Product product;

    public Comment() { }

    public Comment(String content, String userComment, Product prod) {
        this.content = content;
        this.userComment = userComment;
        this.creationDate = new Date();
        this.product = prod;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getContent() { return content; }

    public void setContent(String contenido) { this.content = contenido; }

    public Date getCreationDate() { return creationDate; }

    public void setCreationDate(Date fechaCreado) { this.creationDate = fechaCreado; }

    public String getUserComment() { return userComment; }

    public void setUserComment(String usuarioComentario) { this.userComment = usuarioComentario; }
}
