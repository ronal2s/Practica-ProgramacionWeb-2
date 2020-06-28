package com.renysdelacruz.practica2.models;

public class User {
    String user;
    String name;
    String password;

    public User(String usuario, String nombre, String password) {
        this.user = usuario;
        this.name = nombre;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
