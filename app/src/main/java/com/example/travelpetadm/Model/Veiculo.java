package com.example.travelpetadm.Model;

import java.io.Serializable;

public class Veiculo implements Serializable {

    private String anoVeiculo;
    private String crvlVeiculo;
    private String idUsuario;
    private String idVeiculo;
    private String marcaVeiculo;
    private String modeloVeiculo;
    private String placaVeiculo;
    private String status;
    private String fotoCRVLurl;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnoVeiculo() {
        return anoVeiculo;
    }

    public void setAnoVeiculo(String anoVeiculo) {
        this.anoVeiculo = anoVeiculo;
    }

    public String getCrvlVeiculo() {
        return crvlVeiculo;
    }

    public void setCrvlVeiculo(String crvlVeiculo) {
        this.crvlVeiculo = crvlVeiculo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(String idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    public void setMarcaVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getFotoCRVLurl() {
        return fotoCRVLurl;
    }

    public void setFotoCRVLurl(String fotoCRVLurl) {
        this.fotoCRVLurl = fotoCRVLurl;
    }
}
