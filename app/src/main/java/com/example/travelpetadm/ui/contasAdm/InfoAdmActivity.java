package com.example.travelpetadm.ui.contasAdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.R;

public class InfoAdmActivity extends AppCompatActivity {
    private TextView nome, email, tipoPerfil;
    private Adm adm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_adm);
        Bundle bundle = getIntent().getExtras();
        /*nome = findViewById(R.id.textSobrenomeListaDA);
        email = findViewById(R.id.textEmail);
        tipoPerfil = findViewById(R.id.textTipoPerfil);*/

        if(bundle != null){
            adm = (Adm)bundle.getSerializable("ExibirAdm");
            nome.setText(adm.getNome());
            email.setText(adm.getEmail());
            tipoPerfil.setText(adm.getTipoPerfil());
        }
    }
}
