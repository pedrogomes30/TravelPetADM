package com.example.travelpetadm.Model;

import java.io.Serializable;

public class DonoAnimal  extends Usuario implements Serializable {
    private Animal animal;
    private String fotoUsuarioUrl;

    //CONSTRUTOR -----------------------------------------------------------

    public DonoAnimal() {
    }

    //GETTERS AND SETTERS ----------------------------------------------------

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getFotoUsuarioUrl() {
        return fotoUsuarioUrl;
    }

    public void setFotoUsuarioUrl(String fotoUsuarioUrl) {
        this.fotoUsuarioUrl = fotoUsuarioUrl;
    }
}
