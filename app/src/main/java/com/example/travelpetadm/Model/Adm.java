package com.example.travelpetadm.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.travelpetadm.DAO.AdmDAO;
import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.helper.Encriptador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Adm implements Serializable {
    private String idAdm,nome,email,senha;
    private String tipoPerfil = "Administrador"; //identificador para tipo de usuário padrão,
    // usuario "administradorROOT" não pode ser excluido do sistema.


    //CONSTRUTOR
    public Adm() {
    }

  //Link para classe DAO
    public void salvar(){AdmDAO.salvar();}


    //GETTER AND SETTERS
    public String getTipoPerfil() {
        return tipoPerfil;
    }
    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }
    public String getNome() {
        return nome;
    }
    @Exclude
    public String getIdAdm() {
        return idAdm;
    }
    public void setIdAdm(String idAdm) {
        this.idAdm = idAdm;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
