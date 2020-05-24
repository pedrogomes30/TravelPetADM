package com.example.travelpetadm.ui.viagens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;

public class InfoViagensActivity extends AppCompatActivity {
    private ImageView imageInfoVeiculo;
    private TextView textInfoVeiculoMarca,
            textInfoVeiculoModelo,
            textInfoVeiculoID,
            textInfoVeiculoMotorista,
            textInfoVeiculoStatus;
    private RecyclerView listaInfoVeiculosViagens;
    private Veiculo veiculo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viagens);
        iniciarComponentes();
        iniciarBundle();
    }


    public void iniciarComponentes(){
        textInfoVeiculoMarca             = findViewById(R.id.textInfoVeiculoMarca);
        textInfoVeiculoModelo            = findViewById(R.id.textInfoVeiculoModelo);
        textInfoVeiculoID                = findViewById(R.id.textInfoVeiculoID);
        textInfoVeiculoMotorista         = findViewById(R.id.textInfoVeiculoMotorista);
        textInfoVeiculoStatus            = findViewById(R.id.textInfoVeiculoStatus);
        //...
        listaInfoVeiculosViagens     = findViewById(R.id.listaInfoVeiculosViagens);
    }
    public void iniciarBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            veiculo = (Veiculo) bundle.getSerializable("ExibirVeiculo");
            //imageInfoanimal = animal.getFotoAnimal();
            textInfoVeiculoMarca.setText(veiculo.getMarcaVeiculo());
            textInfoVeiculoModelo.setText(veiculo.getModeloVeiculo());
            textInfoVeiculoID.setText(veiculo.getIdVeiculo());
            textInfoVeiculoMotorista.setText(veiculo.getIdVeiculo());
            textInfoVeiculoStatus.setText(veiculo.getStatus());

        }
    }
}
