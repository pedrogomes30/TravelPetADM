package com.example.travelpetadm.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.GetChars;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.MainActivity;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.contasAdm.AdicionarAdmActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView textEmail, textSenha,textResetSenha;
    private FirebaseAuth auth;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarComponenentes();


        //eventoClicks();
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String senha = textSenha.getText().toString().trim();
                Login(email, senha);
            }
        });

}

    private void Login(String email, String senha) {
        auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    alert("login Ok");
                }else{
                    alert("Login Falhou");

                }
            }
        });
    }

    private void iniciarComponenentes(){
        bt_login = findViewById(R.id.bt_Login);
        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        textResetSenha = findViewById(R.id.textResetSenha);
        auth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();

    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
