package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CommentServices extends EntityManagement{
    private static CommentServices instance;

    private CommentServices() { super(Comment.class); }

    public static CommentServices getInstance(){
        if(instance == null) {
            instance = new CommentServices();
        }
        return instance;
    }

    public List<Comment> findByProductoId(int idProducto) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT * FROM COMMENT WHERE PRODUCTO_ID = :prod");
        query.setParameter("prod", idProducto);
        List<Comment> lista = query.getResultList();
        return lista;
    }

    public List<Comment> findById(int commentId) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM Comment c WHERE c.id = :id");
        query.setParameter("id", commentId);
        List<Comment> lista = query.getResultList();
        return lista;
    }
}