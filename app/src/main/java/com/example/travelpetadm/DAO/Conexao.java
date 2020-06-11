package com.example.travelpetadm.DAO;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.ui.Login.LoginActivity;
import com.example.travelpetadm.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Conexao {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;
    private static DatabaseReference firebaseRef;
    /*private static DatabaseReference firebaseRef;
    private static DatabaseReference firebaseRef;
    private static DatabaseReference firebaseRef;
    private static DatabaseReference firebaseRef;
    private static DatabaseReference firebaseRef;
    private static DatabaseReference firebaseRef;
    private static DatabaseReference firebaseRef;
    private static DatabaseReference firebaseRef;*/

    //Nome de cada nó principal do firebase --------------------------------------------------------------
    public static String adm =              "adm",
                         animal =           "animais",
                         avaliacao =        "avaliacao",
                         donoAnimal =       "donoAnimal",
                         enderecoDA =       "enderecosDonoAnimal",
                         enderecoMO =       "enderecosMotorista",
                         motorista =        "motorista",
                         tipoAnimal =       "racaAnimal",
                         veiculo =          "veiculo",
                         viagem =           "viagem";

    //outras variaveis chave no firebase
    public static String iconeUrl =         "iconeUrl"; // Url onde está salvo o icone da espécie


    // FUNÇÕES BASE DO FIREBASE ----------------------------------------------------------------------
            //referencia firebase Storage
    private static StorageReference firebaseStorage;

            //referencia firebase  Database
    public static DatabaseReference getFirebaseDatabase(){
        if(firebaseRef==null){
            firebaseRef = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseRef;
    }

            //inicia a autenticação
    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null){
            inicializarFirebaseAuth();
        }
        return  firebaseAuth;
    }

            //Referencia aos usuários conectados
    private static void inicializarFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser  user = firebaseAuth.getCurrentUser();
                if(user !=null){
                    firebaseUser = user;
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);
    }

            //pega o atual usuário conectado
    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;

    }

            //desloga o atual usuário conectado
    public static void logOut(){
        firebaseAuth.signOut();

    }

            //verifica usuário logado
    public static boolean usuarioLogin(){
        if(firebaseAuth.getCurrentUser()!=null){
            return true;
        }else{return false;}

    }
    public static String getEmailUsuario(){
        FirebaseAuth usuario = Conexao.getFirebaseAuth();
        return usuario.getCurrentUser().getEmail();
    }
            //pega a referencia do storage
    public static StorageReference getFirebaseStorage(){
        if (firebaseStorage == null){
            inicializarFirebaseAuth();
        }
        return  firebaseStorage;
    }

}
