package com.example.travelpetadm.Model;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.DAO.TipoAnimalDAO;
import com.example.travelpetadm.helper.Encriptador;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class TipoAnimal implements Serializable {
    private String especie;
    private String nomeRacaAnimal;
    private String descricao;
    private String nomeCientificoRacaAnimal;
    private  String iconeEspecieUrl;


    public TipoAnimal() {
    }

   //chama a classe DAO para salvar no BD


    //getters and setters
    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNomeRacaAnimal() {
        return nomeRacaAnimal;
    }

    public void setRaca(String nomeRacaAnimal) {
        this.nomeRacaAnimal = nomeRacaAnimal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNomeRacaAnimal(String nomeRacaAnimal) {
        this.nomeRacaAnimal = nomeRacaAnimal;
    }

    public String getNomeCientificoRacaAnimal() {
        return nomeCientificoRacaAnimal;
    }

    public void setNomeCientificoRacaAnimal(String nomeCientificoRacaAnimal) {
        this.nomeCientificoRacaAnimal = nomeCientificoRacaAnimal;
    }

    public String getIconeEspecieUrl() {
        return iconeEspecieUrl;
    }

    public void seticoneUrl(String iconeEspecieUrl) {
        this.iconeEspecieUrl = iconeEspecieUrl;
    }
}
