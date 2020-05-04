package com.example.travelpetadm.ui.contasAdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.travelpetadm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdicionarAdmActivity extends AppCompatActivity {
    private TextInputEditText email, senha,confirmar;
    private Button btSalvar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_adm);
    email = findViewById(R.id.textEmail);
    senha = findViewById(R.id.textEmail);
    confirmar = findViewById(R.id.textConfirmar);


    auth.createUserWithEmailAndPassword(
            email.getText().toString(),
            senha.getText().toString()
    ).addOnCanceledListener( AdicionarAdmActivity.this ,new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(),"salvo com sucesso",Toast.LENGTH_SHORT);
            }else {
                Toast.makeText(getApplicationContext(), "erro", Toast.LENGTH_SHORT);
            }
        }
    });


    }
}
