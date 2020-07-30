package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserServices extends EntityManagement<User> {
    private static UserServices instance;

    private UserServices() {
        super(User.class);
    }

    public static UserServices getInstance(){
        if(instance==null){
            instance = new UserServices();
        }
        return instance;
    }

    public List<User> findByUsername(String user){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM User u where u.id like :user");
        query.setParameter("user", user);
        List<User> lista = query.getResultList();
        return lista;
    }
}