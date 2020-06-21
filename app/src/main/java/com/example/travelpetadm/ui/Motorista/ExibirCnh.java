package com.example.travelpetadm.ui.Motorista;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.R;

public class ExibirCnh extends AppCompatActivity {
Motorista motorista;
ImageView imgCnh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_cnh);
        imgCnh = findViewById(R.id.imgCnh);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            motorista = (Motorista) bundle.getSerializable("ExibirCnh");
        }
        if(motorista.getFotoCnhUrl()!=null){
            Uri fotoPerfilUri = Uri.parse(motorista.getFotoCnhUrl());
            Glide.with(ExibirCnh.this).load( fotoPerfilUri ).into( imgCnh );
        }else{
            imgCnh.setImageResource(R.drawable.ic_fab_cnh);
            Toast.makeText(getApplicationContext(),"Usuário sem foto de CNH!!!",Toast.LENGTH_LONG);
        }
        imgCnh = findViewById(R.id.imgCnh);
    }
}
