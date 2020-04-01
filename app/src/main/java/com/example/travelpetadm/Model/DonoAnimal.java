package com.example.travelpetadm.Model;

public class DonoAnimal  extends Pessoa{
    private Animal animal;
    private String fotoPerfilUrl;

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

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}
