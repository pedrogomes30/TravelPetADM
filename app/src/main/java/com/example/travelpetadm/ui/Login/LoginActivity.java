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
import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.ui.MainActivity;
import com.example.travelpetadm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private TextView textEmail, textSenha,textResetSenha;
    private FirebaseAuth auth;
    private Button bt_login;
    private Adm adm;

    //ON CREATE DA ACTIVITY
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarComponenentes();
        eventoClicks();

    }

    //EVENTOS DE CLICK DO BOTÃO E "ESQUECI MINHA SENHA".
    private void eventoClicks(){
        //BOTAO LOGIN
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String senha = textSenha.getText().toString().trim();
                if(valiadarDados(email,senha)) {
                    adm = new Adm();
                    adm.setEmail(email);
                    adm.setSenha(senha);
                    Login(email, senha);
                }
            }
        });
        //"CAIXA TEXTO "ESQUECI MINHA SENHA"
        textResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
                startActivity(intent);
            }
        });
    }

    // INICIAR COMPONENTES
    private void iniciarComponenentes(){
        bt_login = findViewById(R.id.bt_Login);
        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        textResetSenha = findViewById(R.id.textResetSenha);
        auth = FirebaseAuth.getInstance();

    }

    //INICIAR VARIAVEL FIREBASE
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        if(Conexao.usuarioLogin()){
            startActivity(new Intent(getBaseContext(),MainActivity.class));
            alert("Bem vindo novamente!" );
            finish();
        };
    }

    //VALIDADOR DE CAMPOS VAZIOS
    private boolean valiadarDados(String email, String senha){

        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                return  true;
            }else{
                alert("informe a senha");
                return false;
            }
        }else{
            alert("informe um e-mail");
            return false;
        }

    }

    //REALIZAR O LOGIN NO FIREBASE
    private void Login(String email, String senha) {
        auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    alert("Bem Vindo" );
                }else{
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        alert("email ou senha inválidos");
                    }catch (FirebaseAuthInvalidUserException e){
                        alert ("Administrador não cadastrado");
                    }catch(Exception e){
                        alert("Erro ao cadastrar usuário: "+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //METODO DE MENSSAGENS
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
