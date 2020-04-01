package com.example.travelpetadm.Model;

public class Cartao {
    private int numeroCartao;
    private String codigoSeguranca, validade;

    //Construtor --------------------------------------------

    public Cartao() {
    }

    //Getters and Setters ------------------------------------

    public int getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(int numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(String codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}
