package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.Product;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ProductServices extends EntityManagement<Product> {
    private static ProductServices instance;

    private ProductServices() {
        super(Product.class);
    }

    public static ProductServices getInstance(){
        if(instance==null){
            instance = new ProductServices();
        }
        return instance;
    }

    public List<Product> findByID(int id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT p FROM Product p where p.id = :id");
        query.setParameter("id", id);
        List<Product> lista = query.getResultList();
        return lista;
    }
}