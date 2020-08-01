package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.Picture;

public class PictureServices extends EntityManagement<Picture> {
    private static PictureServices instance;

    public PictureServices() { super(Picture.class); }

    public static PictureServices getInstance() {
        if(instance == null){
            instance = new PictureServices();
        }
        return instance;
    }
}
