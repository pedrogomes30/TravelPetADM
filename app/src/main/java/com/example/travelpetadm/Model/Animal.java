package com.example.travelpetadm.Model;

import java.io.Serializable;

public class Animal implements Serializable {
    private String especieAnimal, porteAnimal, racaAnimal, observacaoAnimal, nomeAnimal;
    private String fotoAnimal;
    private String idUsuario, idAnimal;


//construtor

    public Animal() {
    }

//getters and setters

    public String getEspecieAnimal() {
        return especieAnimal;
    }

    public void setEspecieAnimal(String especieAnimal) {
        this.especieAnimal = especieAnimal;
    }

    public String getPorteAnimal() {
        return porteAnimal;
    }

    public void setPorteAnimal(String porteAnimal) {
        this.porteAnimal = porteAnimal;
    }

    public String getRacaAnimal() {
        return racaAnimal;
    }

    public void setRacaAnimal(String racaAnimal) {
        this.racaAnimal = racaAnimal;
    }

    public String getObservacaoAnimal() {
        return observacaoAnimal;
    }

    public void setObservacaoAnimal(String observacaoAnimal) {
        this.observacaoAnimal = observacaoAnimal;
    }

    public String getNomeAnimal() {
        return nomeAnimal;
    }

    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }

    public String getFotoAnimal() {
        return fotoAnimal;
    }

    public void setFotoAnimal(String fotoAnimal) {
        this.fotoAnimal = fotoAnimal;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
    }
}
