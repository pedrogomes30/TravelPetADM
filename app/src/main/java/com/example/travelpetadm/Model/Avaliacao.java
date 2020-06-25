package com.example.travelpetadm.Model;

import java.io.Serializable;

public class Avaliacao implements Serializable {
    String  idAvaliado,
            idAvaliador,
            observacao,
            tipoAvaliacao,
            idViagem,
            iddaavaliacao,
            data;
    Double notaAvaliacao;

    public String getIdAvaliado() {
        return idAvaliado;
    }

    public void setIdAvaliado(String idAvaliado) {
        this.idAvaliado = idAvaliado;
    }

    public String getIdAvaliador() {
        return idAvaliador;
    }

    public void setIdAvaliador(String idAvaliador) {
        this.idAvaliador = idAvaliador;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public String getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(String idViagem) {
        this.idViagem = idViagem;
    }

    public String getIddaavaliacao() {
        return iddaavaliacao;
    }

    public void setIddaavaliacao(String iddaavaliacao) {
        this.iddaavaliacao = iddaavaliacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(Double notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }
}
