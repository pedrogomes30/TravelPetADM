package com.example.travelpetadm.Model;

import java.io.Serializable;

public class Viagem implements Serializable {

    private String animal;
    private String custoViagem;
    private String data;
    private String destino;
    private String distancia;
    private String IDDonoAnimal;
    private String horaInicio;
    private String horaFim;
    private String IDMotorista;
    private String origem;
    private String porteAnimal;
    private String IDViagem;
    private String IDVeiculo;

    public Viagem() {
    }

    public String getIDVeiculo() {
        return IDVeiculo;
    }

    public void setIDVeiculo(String IDVeiculo) {
        this.IDVeiculo = IDVeiculo;
    }


    public String getIDViagem() {
        return IDViagem;
    }

    public void setIDViagem(String ID) {
        this.IDViagem = ID;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getCustoViagem() {
        return custoViagem;
    }

    public void setCustoViagem(String  custoViagem) {
        this.custoViagem = custoViagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getIDDonoAnimal() {
        return IDDonoAnimal;
    }

    public void setIDDonoAnimal(String IDDonoAnimal) {
        this.IDDonoAnimal = IDDonoAnimal;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getIDMotorista() {
        return IDMotorista;
    }

    public void setIDMotorista(String IDMotorista) {
        this.IDMotorista = IDMotorista;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getPorteAnimal() {
        return porteAnimal;
    }

    public void setPorteAnimal(String porteAnimal) {
        this.porteAnimal = porteAnimal;
    }



}
