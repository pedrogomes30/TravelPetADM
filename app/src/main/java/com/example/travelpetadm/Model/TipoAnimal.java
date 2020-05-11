package com.example.travelpetadm.Model;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.helper.Encriptador;
import com.google.firebase.database.DatabaseReference;

public class TipoAnimal {
    private String especie;
    private String nomeRacaAnimal;
    private String descricao;

    public TipoAnimal() {
    }

    //salva o tipo animal no banco de dados -- importar em uma classe DAO
    public void salvar(){

        DatabaseReference reference = Conexao.getFirebaseDatabase();
        reference.child("racaAnimal")
              .child(especie)
              .child(nomeRacaAnimal)
              .setValue(this);
    }

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
}
