package com.example.travelpetadm.Model;

import java.io.Serializable;

public class Avaliacao implements Serializable {
    String avaliado,
            avaliador,
            observacao,
            tipoPerfil,
            data;
    Double notaAvaliacao;

    public String getAvaliado() {
        return avaliado;
    }

    public void setAvaliado(String avaliado) {
        this.avaliado = avaliado;
    }

    public String getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(String avaliador) {
        this.avaliador = avaliador;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }


    public Double getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(Double notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
