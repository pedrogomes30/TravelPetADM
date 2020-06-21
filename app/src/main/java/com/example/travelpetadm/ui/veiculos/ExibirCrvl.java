package com.example.travelpetadm.ui.veiculos;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.Motorista.ExibirCnh;

public class ExibirCrvl extends AppCompatActivity {
    ImageView imgCrvl;
    Veiculo veiculo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_crvl);
        imgCrvl = findViewById(R.id.imgCrvl);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            veiculo = (Veiculo) bundle.getSerializable("ExibirCrvl");
        }
        if(veiculo.getFotoCRVLurl()!= null){
            Uri fotoPerfilUri = Uri.parse(veiculo.getFotoCRVLurl());
            Glide.with(ExibirCrvl.this).load( fotoPerfilUri ).into( imgCrvl );
        }else{
            imgCrvl.setImageResource(R.drawable.ic_menu_veiculo);
            Toast.makeText(getApplicationContext(),"sem foto do Crvl!",Toast.LENGTH_LONG);
        }
    }
}
