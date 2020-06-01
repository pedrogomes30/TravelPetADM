package com.example.travelpetadm.Model;

import java.io.Serializable;

public class DonoAnimal  extends Usuario implements Serializable {
      private String fotoUsuarioUrl;

    //CONSTRUTOR -----------------------------------------------------------

    public DonoAnimal() {
    }

    //GETTERS AND SETTERS ----------------------------------------------------

    public String getFotoUsuarioUrl() {
        return fotoUsuarioUrl;
    }

    public void setFotoUsuarioUrl(String fotoUsuarioUrl) {
        this.fotoUsuarioUrl = fotoUsuarioUrl;
    }
}
