package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.Sale;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class SaleServices extends EntityManagement<Sale> {
    private static SaleServices instance;

    private SaleServices(){ super(Sale.class); }

    public static SaleServices getInstance(){
        if(instance == null){
            instance = new SaleServices();
        }
        return instance;
    }

    public List<Sale> findByID(int id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT e FROM Sale where e.id = :id");
        query.setParameter("id", id);
        List<Sale> lista = query.getResultList();
        return lista;
    }
}