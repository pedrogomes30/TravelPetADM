package com.example.travelpetadm.Model;

import android.util.Log;

import androidx.annotation.NonNull;

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

    //salva o ADM no Firebase -- Alterar este metodo para a classe DAO
    public void salvar(){
        DatabaseReference firebaseRef = Conexao.getFirebaseDatabase();
        DatabaseReference adm = firebaseRef.child("adm").child(getIdAdm());
        adm.setValue(this);
    }

    //converte o e-mail do usuário em base64 para se ter o ID através do email codificado
    // -- Importar para a classe DAO
    public static  String getIdentificadorAdm (){
        FirebaseAuth adm = Conexao.getFirebaseAuth();
        String email = adm.getCurrentUser().getEmail();
        String identificadorAdm = Encriptador.codificarBase64(email);
        return identificadorAdm;
    }
    //retorna o atual usuário logado no sistema -- importar para a classe DAO
    public static FirebaseUser getAdmAtual(){
        FirebaseAuth adm = Conexao.getFirebaseAuth();
        return adm.getCurrentUser();
    }
    //Metodo para recuperar o nome do usuário --importar par aa classe DAO

    public static boolean atualizarNomeUsuario (String nome){
    try{
        FirebaseUser adm = getAdmAtual();
        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build();

        adm.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.d("PERFIL", "Erro ao atualizar perfil");
                }
            }
        });
        return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

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
