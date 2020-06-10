package com.example.travelpetadm.ui.TipoAnimal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.DAO.TipoAnimalDAO;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTipoAnimalActivity extends AppCompatActivity {
    private TextView textEditarTAEspecie, textEditarTARaca, textEditarTAObservacao;
    private Button btSalvarTA;
    private TipoAnimal tipoanimal;
    private String especieS,racaS,descricaoS;

    //ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tipo_animal);
        iniciarComponentes();
        eventoClick();
        }

    //iniciador dos componentes
    private void iniciarComponentes(){
        textEditarTAEspecie = findViewById(R.id.textEditarTAEspecie);
        textEditarTARaca = findViewById(R.id.textEditarTARaca);
        textEditarTAObservacao = findViewById(R.id.textEditarTAObservacao);
        btSalvarTA = findViewById(R.id.btSalvarTA);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
             tipoanimal = (TipoAnimal)bundle.getSerializable("EditarTipoAnimal");
            textEditarTAEspecie.setText(String.valueOf(tipoanimal.getEspecie()));
            textEditarTARaca.setText(tipoanimal.getNomeRacaAnimal());
            textEditarTAObservacao.setText(String.valueOf( tipoanimal.getDescricao()));
            }
        }

    //eventos de click na tela
    private void eventoClick(){
        btSalvarTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validador()){
                    criarTipoAnimal();
                }
            }
        });

    }

    //cria o tipo de animal no banco de dados
    private void criarTipoAnimal(){
        tipoanimal = new TipoAnimal();
        tipoanimal.setEspecie(textEditarTAEspecie.getText().toString());
        tipoanimal.setRaca(textEditarTARaca.getText().toString());
        tipoanimal.setDescricao(textEditarTAObservacao.getText().toString());
        alert("animal " + textEditarTARaca.getText().toString() + " salvo com sucesso! ");
        textEditarTAEspecie.setText("");
        textEditarTARaca.setText("");
        textEditarTAObservacao.setText("");
        TipoAnimalDAO.salvarTipoAnimal(tipoanimal);
        finish();
    }

    //validador de campos em branco
    private boolean validador() {
        especieS            = textEditarTAEspecie.getText().toString().trim();
            racaS           = textEditarTARaca.getText().toString().trim();
                descricaoS  = textEditarTAObservacao.getText().toString().trim();

        if (!especieS.isEmpty()) {
            if (!racaS.isEmpty()) {
                if (!descricaoS.isEmpty()) {
                    return true;
                } else {
                    alert("informe uma descrição");
                    return false;
                }
                }else{
                alert("informe  uma raca");
                return false;}
            } else {
            alert("informe uma especie ");
            return false;
        }
    }

    //metodo para facilitar toast
    private void alert (String msg){
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        }

}
