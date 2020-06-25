package com.example.travelpetadm.ui.Avaliacao;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelpetadm.Model.Avaliacao;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;

public class InfoAvaliacaoActivity extends AppCompatActivity {
    private TextView textInfoNotaAvaliacao,
                    textInfoAvaliadorAvaliacao,
                    textInfoAvaliadoAvaliacao,
                    textInfoTipoAvaliacao,
                    textInfoObservacaoAvaliacao,
                    textInfoDataAvaliacao;
    private ImageView imageTipoAvaliacao;
    private Avaliacao avaliacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_avaliacao);
        iniciarComponentes();
        setBundle();
    }
    public void iniciarComponentes() {
        textInfoNotaAvaliacao=findViewById(R.id.textInfoNotaAvaliacao);
        textInfoAvaliadorAvaliacao =findViewById(R.id.textInfoAvaliadorAvaliacao);
        textInfoAvaliadoAvaliacao=findViewById(R.id.textInfoAvaliadoAvaliacao);
        textInfoTipoAvaliacao=findViewById(R.id.textInfoTipoAvaliacao);
        textInfoObservacaoAvaliacao=findViewById(R.id.textInfoObservacaoAvaliacao);
        textInfoDataAvaliacao=findViewById(R.id.textInfoDataAvaliacao);
        imageTipoAvaliacao=findViewById(R.id.imageTipoAvaliacao);
    }

    public void setBundle() {
        Bundle bundle = getIntent().getExtras();
           if (bundle != null) {
            avaliacao = (Avaliacao) bundle.getSerializable("informacoes avaliacao");
            if (avaliacao.getNotaAvaliacao() >= 4) {
                textInfoNotaAvaliacao.setText(String.valueOf(avaliacao.getNotaAvaliacao()));
                textInfoNotaAvaliacao.setTextColor(Color.GREEN);
            } else {
                if (avaliacao.getNotaAvaliacao() < 4 && avaliacao.getNotaAvaliacao() >= 2) {
                    textInfoNotaAvaliacao.setText(String.valueOf(avaliacao.getNotaAvaliacao()));
                    textInfoNotaAvaliacao.setTextColor(Color.YELLOW);
                } else {if (avaliacao.getNotaAvaliacao() <2) {
                    textInfoNotaAvaliacao.setText(String.valueOf(avaliacao.getNotaAvaliacao()));
                    textInfoNotaAvaliacao.setTextColor(Color.RED);
                    }
                }
            }
            textInfoAvaliadorAvaliacao.setText(String.valueOf(avaliacao.getIdAvaliador()));
            textInfoAvaliadoAvaliacao.setText(String.valueOf(avaliacao.getIdAvaliado()));
            textInfoTipoAvaliacao.setText(String.valueOf(avaliacao.getTipoAvaliacao()));
               textInfoDataAvaliacao.setText(String.valueOf(avaliacao.getData()));
            textInfoObservacaoAvaliacao.setText(String.valueOf(avaliacao.getObservacao()));
               switch(avaliacao.getTipoAvaliacao()){
                   case "motorista":
                       imageTipoAvaliacao.setImageResource(R.drawable.ic_motorista);
                       break;
                   case "donoAnimal":
                       imageTipoAvaliacao.setImageResource(R.drawable.ic_dono_animal);
                       break;
                   default:
                       imageTipoAvaliacao.setImageResource(R.drawable.ic_menu_about);
               }
        }
    }
}
