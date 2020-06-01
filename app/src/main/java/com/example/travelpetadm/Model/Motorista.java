package com.example.travelpetadm.Model;

import java.io.Serializable;

public class Motorista extends Usuario implements Serializable {
    private String cnh, cnhURL, fotoPerfilURL;

    public Motorista() {    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getCnhURL() {
        return cnhURL;
    }

    public void setCnhURL(String cnhURL) {
        this.cnhURL = cnhURL;
    }

    public String getFotoPerfilURL() {
        return fotoPerfilURL;
    }

    public void setFotoPerfilURL(String fotoPerfilURL) {
        this.fotoPerfilURL = fotoPerfilURL;
    }
}
