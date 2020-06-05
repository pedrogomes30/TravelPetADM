package com.example.travelpetadm.Model;

import java.io.Serializable;

public class DonoAnimal  extends Usuario implements Serializable {
      private String fotoPerfilUrl;

    //CONSTRUTOR -----------------------------------------------------------

    public DonoAnimal() {
    }

    //GETTERS AND SETTERS ----------------------------------------------------


    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}
