package com.example.travelpetadm.DAO;

import android.content.Intent;

import androidx.annotation.NonNull;

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
    private static StorageReference firebaseStorage;
    private Conexao() {}

    public static DatabaseReference getFirebaseDatabase(){
        if(firebaseRef==null){
            firebaseRef = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseRef;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null){
            inicializarFirebaseAuth();
        }
        return  firebaseAuth;
    }

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

    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;

    }

    public static void logOut(){
        firebaseAuth.signOut();

    }

    //verifica usuário logado
    public static boolean usuarioLogin(){
        if(firebaseAuth.getCurrentUser()!=null){
            return true;
        }else{return false;}

    }

    public static StorageReference getFirebaseStorage(){
        if (firebaseStorage == null){
            inicializarFirebaseAuth();
        }
        return  firebaseStorage;
    }
}
