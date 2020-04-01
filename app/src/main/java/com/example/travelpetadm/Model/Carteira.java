package com.example.travelpetadm.Model;

public class Carteira {
    private double valor;
    private Cartao cartao;
    private boolean tipoperfil;

    //Contrutor --------------------------------------------------------

    public Carteira() {
    }

    //Getters and Setters ------------------------------------------------

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public boolean isTipoperfil() {
        return tipoperfil;
    }

    public void setTipoperfil(boolean tipoperfil) {
        this.tipoperfil = tipoperfil;
    }
}
