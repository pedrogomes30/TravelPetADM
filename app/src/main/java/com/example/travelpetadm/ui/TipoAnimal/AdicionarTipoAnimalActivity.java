package com.example.travelpetadm.ui.TipoAnimal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTipoAnimalActivity extends AppCompatActivity {
    private TextInputEditText especie, raca, descricao;
    private Button btSalvar;
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
        especie = findViewById(R.id.textEspecie);
        raca = findViewById(R.id.textRaca);
        descricao = findViewById(R.id.textObservacao);
        btSalvar = findViewById(R.id.btSalvarTA);
        }

    //eventos de click na tela
    private void eventoClick(){
        btSalvar.setOnClickListener(new View.OnClickListener() {
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
        tipoanimal.setEspecie(especie.getText().toString());
        tipoanimal.setRaca(raca.getText().toString());
        tipoanimal.setDescricao(descricao.getText().toString());
        especie.setText("");
        raca.setText("");
        descricao.setText("");
        alert("Novo Tipo de animal cadastrado!");
        tipoanimal.salvar();

    }

    //validador de campos em branco
    private boolean validador() {
        especieS            = especie.getText().toString().trim();
            racaS           = raca.getText().toString().trim();
                descricaoS  = descricao.getText().toString().trim();

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
