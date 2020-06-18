package com.example.travelpetadm.Model;

import java.io.Serializable;

public class Viagem implements Serializable {

    private String idAnimal1,idAnimal2,idAnimal3;
    private String custoViagem;
    private String data;
    private String destino;
    private Double distancia;
    private String idDonoAnimal;
    private String horaInicio;
    private String horaFim;
    private String IDMotorista;
    private String idOrigem;
    private String porteAnimal;
    private String IDViagem;
    private String IDVeiculo;
    private String statusViagem;
    private Double custo;

    public Viagem() {
    }

    public String getIDVeiculo() {
        return IDVeiculo;
    }

    public void setIDVeiculo(String IDVeiculo) {
        this.IDVeiculo = IDVeiculo;
    }

    public String getIdAnimal1() {
        return idAnimal1;
    }

    public void setIdAnimal1(String idAnimal1) {
        this.idAnimal1 = idAnimal1;
    }

    public String getIdAnimal2() {
        return idAnimal2;
    }

    public void setIdAnimal2(String idAnimal2) {
        this.idAnimal2 = idAnimal2;
    }

    public String getIdAnimal3() {
        return idAnimal3;
    }

    public void setIdAnimal3(String idAnimal3) {
        this.idAnimal3 = idAnimal3;
    }

    public String getIdDonoAnimal() {
        return idDonoAnimal;
    }

    public void setIdDonoAnimal(String idDonoAnimal) {
        this.idDonoAnimal = idDonoAnimal;
    }

    public String getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(String idOrigem) {
        this.idOrigem = idOrigem;
    }

    public String getStatusViagem() {
        return statusViagem;
    }

    public void setStatusViagem(String statusViagem) {
        this.statusViagem = statusViagem;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public String getIDViagem() {
        return IDViagem;
    }

    public void setIDViagem(String ID) {
        this.IDViagem = ID;
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

    public String getPorteAnimal() {
        return porteAnimal;
    }

    public void setPorteAnimal(String porteAnimal) {
        this.porteAnimal = porteAnimal;
    }



}
