package com.example.travelpetadm.ui.contasAdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class AdicionarAdmActivity extends AppCompatActivity {
    private TextView textEmail, textSenha, textNome;
    private Button btSalvar;
    private FirebaseAuth auth;
    private Adm adm;

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
        btSalvar = findViewById(R.id.btSalvarRec);
    }

    private void eventoclickes() {
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textEmail.getText().toString().trim();
                String senha = textSenha.getText().toString().trim();
                String nome = textNome.getText().toString().trim();
                if(vailadarDados(email,senha,nome)){
                    adm = new Adm();
                    adm.setEmail(email);
                    adm.setNome(nome);
                    adm.setSenha(senha);
                    criarADM();
                }
            }
        });
    }

    //cria o usuário no firebase, expostar em uma classe DAO posteriormente
    private void criarADM() {

    auth.createUserWithEmailAndPassword(adm.getEmail(),adm.getSenha()).addOnCompleteListener(AdicionarAdmActivity.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                alert("Usuário cadastrado com sucesso");
                textNome.setText("");
                textEmail.setText("");
                textSenha.setText("");
            }else{
                try{
                    throw task.getException();
                }catch (FirebaseAuthWeakPasswordException e){
                    alert("senha curta demais");
                }catch (FirebaseAuthInvalidCredentialsException e){
                    alert("email inválido");
                }catch (FirebaseAuthUserCollisionException e){
                    alert ("Usuário já cadastrado");
                }catch(Exception e){
                    alert("Erro ao cadastrar usuário: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    });
    }


    //validar os campos
    private boolean vailadarDados(String email, String senha,String nome){
        if(!nome.isEmpty()){
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
        }else{
            alert("informe um nome de usuário");
            return false;
        }


    }


    //envio de menssagens da classe
    private void alert (String msg){
        Toast.makeText(AdicionarAdmActivity.this,msg,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
       super.onStart();
       auth = Conexao.getFirebaseAuth();
    }//END ON START
}//END CLASS
