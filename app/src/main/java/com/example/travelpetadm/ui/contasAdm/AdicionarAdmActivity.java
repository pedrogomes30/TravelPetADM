package com.example.travelpetadm.ui.contasAdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdicionarAdmActivity extends AppCompatActivity {
    private TextView textEmail, textSenha, textConfirmar,textNome;
    private Button btSalvar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_adm);
        inicializarComponentes();
        eventoclickes();


    }//END ON CREATE

    private void inicializarComponentes() {
        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        textNome = findViewById(R.id.textNome);
        textConfirmar = findViewById(R.id.textConfirmar);
        btSalvar = findViewById(R.id.btSalvar);
    }

    private void eventoclickes() {
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String senha = textSenha.getText().toString().trim();
                criarADM(email,senha);

                finish(); //finaliza a activity
            }
        });
    }

    private void criarADM(String email, String senha) {
    auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(AdicionarAdmActivity.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                alerta("Usuário cadastrado com sucesso");
                finish();
            }else{
                alerta("Falha ao cadastrar usuário");
                }
        }
    });
    }

    private void alerta (String msg){
        Toast.makeText(AdicionarAdmActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
       super.onStart();
       auth = Conexao.getFirebaseAuth();
    }//END ON START
}//END CLASS
