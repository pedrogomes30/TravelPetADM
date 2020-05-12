package com.example.travelpetadm.ui.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarSenhaActivity extends AppCompatActivity {
private TextView textEmail;
private Button btSalvar;
private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        auth = FirebaseAuth.getInstance();
    iniciandoComponentes();
    eventoClick();
    }

    private void iniciandoComponentes(){
        textEmail = findViewById(R.id.textEmailRec);
        btSalvar  = findViewById(R.id.btSalvarRec);
    }

    private void eventoClick(){
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString();
                if(!email.isEmpty()){
                    resetSenha(email);
                }else {
                    alert("informe o e-mail");
                }
            }
        });
    }

    private void resetSenha (String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(RecuperarSenhaActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    alert("e-mail enviado!");
                    finish();
                }try {

                }catch (Exception e){
                    alert("erro ao enviar e-mail:/n "+e.getMessage());
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

    }

}
