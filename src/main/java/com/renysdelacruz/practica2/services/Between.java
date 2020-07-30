package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.SellProduct;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Between extends EntityManagement<SellProduct> {

    private static Between instance;

    private Between() { super(SellProduct.class); }

    public static Between getInstance() {
        if(instance == null){
            instance = new Between();
        }
        return instance;
    }

    public void insertarRelacion(SellProduct entidad){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("INSERT INTO SELL_PRODUCT (QUANTITY, PRODUCT_ID, SALE_ID) VALUES (?, ?, ?)")
                .setParameter(1, entidad.getQuantity())
                .setParameter(2, entidad.getProduct().getId())
                .setParameter(3, entidad.getSale().getId());
        try {

            em.getTransaction().begin();
            query.executeUpdate();
            em.getTransaction().commit();

        }finally {
            em.close();
        }
    }

    public List<SellProduct> findByVenta(long saleId) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT * FROM SELL_PRODUCT WHERE SALE_ID = :id", SellProduct.class);
        query.setParameter("id", saleId);
        List<SellProduct> lista = query.getResultList();
        return lista;
    }
}