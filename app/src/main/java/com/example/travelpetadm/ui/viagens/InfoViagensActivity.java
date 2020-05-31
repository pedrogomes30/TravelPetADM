package com.example.travelpetadm.ui.viagens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.Model.Viagem;
import com.example.travelpetadm.R;

public class InfoViagensActivity extends AppCompatActivity {
    private TextView
            textInfoViagemID,
            textInfoViagemDA,
            textInfoViagemAnimal,
            textInfoViagemPorte,
            textInfoViagemData,
            textInfoViagemDestino,
            textInfoViagemOrigem,
            textInfoViagemDistancia,
            textInfoViagemHI,
            textInfoViagemHf,
            textInfoViagemMotorista,
            textInfoViagemVeiculoMarca,
            textInfoViagemVeiculoModelo;
    private Viagem viagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viagens);
        iniciarComponentes();
        iniciarBundle();
    }


    public void iniciarComponentes(){
                textInfoViagemID                    = findViewById(R.id.textInfoViagemID);
                textInfoViagemDA                    = findViewById(R.id.textInfoViagemDA);
                textInfoViagemAnimal                = findViewById(R.id.textInfoViagemAnimal);
                textInfoViagemPorte                 = findViewById(R.id.textInfoViagemPorte);
                textInfoViagemData                  = findViewById(R.id.textInfoViagemData);
                textInfoViagemDestino               = findViewById(R.id.textInfoViagemDestino);
                textInfoViagemOrigem                = findViewById(R.id.textInfoViagemOrigem);
                textInfoViagemDistancia             = findViewById(R.id.textInfoViagemDistancia);
                textInfoViagemHI                    = findViewById(R.id.textInfoViagemHI);
                textInfoViagemHf                    = findViewById(R.id.textInfoViagemHf);
                textInfoViagemMotorista             = findViewById(R.id.textInfoViagemMotorista);
                textInfoViagemVeiculoMarca          = findViewById(R.id.textInfoViagemVeiculoMarca);
                textInfoViagemVeiculoModelo         = findViewById(R.id.textInfoViagemVeiculoModelo);
    }

    public void iniciarBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            viagem = (Viagem) bundle.getSerializable("ExibirViagem");
                    textInfoViagemID.setText(viagem.getID());
                    textInfoViagemDA.setText(viagem.getDonoAnimal());
                    textInfoViagemAnimal.setText(viagem.getAnimal());
                    textInfoViagemPorte.setText(viagem.getPorteAnimal());
                    textInfoViagemData.setText(viagem.getData());
                    textInfoViagemDestino.setText(viagem.getDestino());
                    textInfoViagemOrigem.setText(viagem.getOrigem());
                    textInfoViagemDistancia.setText(viagem.getDistancia());
                    textInfoViagemHI.setText(viagem.getHoraInicio());
                    textInfoViagemHf.setText(viagem.getHoraFim());
                    textInfoViagemMotorista.setText(viagem.getMotorista());
                    textInfoViagemVeiculoMarca.setText(viagem.getIDVeiculo());


        }
    }
}
