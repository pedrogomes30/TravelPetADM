package com.example.travelpetadm.Model;

import java.io.Serializable;

public class Motorista extends Usuario implements Serializable {
    private String registroCnh,
            statusCadastro,
            fotoCnhUrl,
            fotoPerfilUrl;

    //getters and setters

    public String getRegistroCnh() {
        return registroCnh;
    }

    public void setRegistroCnh(String registroCnh) {
        this.registroCnh = registroCnh;
    }

    public String getStatusCadastro() {
        return statusCadastro;
    }

    public void setStatusCadastro(String statusCadastro) {
        this.statusCadastro = statusCadastro;
    }

    public String getFotoCnhUrl() {
        return fotoCnhUrl;
    }

    public void setFotoCnhUrl(String fotoCnhUrl) {
        this.fotoCnhUrl = fotoCnhUrl;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}
