package com.example.travelpetadm.ui.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class RecuperarSenha extends AppCompatActivity {
private TextView textEmail;
private Button btSalvar;
private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
    iniciandoComponentes();
    eventoClick();
    }
    private void iniciandoComponentes(){
        textEmail = findViewById(R.id.textEmail);
        btSalvar  = findViewById(R.id.btSalvar);
    }
    private void eventoClick(){
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                resetSenha(email);
            }
        });
    }
    private void resetSenha (String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(RecuperarSenha.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    alert("Um E-mail foi enviado para alterar sua senha");

                }
                else{
                    alert("email não encontrado");
                }
            }
        });
    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

}
