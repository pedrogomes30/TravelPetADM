package com.example.travelpetadm.ui.animais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;

public class InfoAnimalActivity extends AppCompatActivity {
    private ImageView imageInfoanimal;
    private TextView textInfoAnimalNome,
            textInfoAnimalEspecie,
            textInfoAnimalRaca,
            textInfoAnimalIDAnimal,
            textInfoAnimalDA,
            textInfoAnimalPorte;
    private TextView textInfoAnimalDescricao;
    private RecyclerView listaInfoAnimaisViagens;
    private Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_animal);
        iniciarComponentes();
        iniciarBundle();
    }
    public void iniciarComponentes(){
        textInfoAnimalNome          = findViewById(R.id.textInfoAnimalNome);
        textInfoAnimalEspecie       = findViewById(R.id.textInfoAnimalEspecie);
        textInfoAnimalRaca          = findViewById(R.id.textInfoAnimalRaca);
        textInfoAnimalIDAnimal      = findViewById(R.id.textInfoAnimalIDAnimal);
        textInfoAnimalDA            = findViewById(R.id.textInfoAnimalDA);
        textInfoAnimalPorte         = findViewById(R.id.textInfoAnimalPorte);
        //...
        textInfoAnimalDescricao     = findViewById(R.id.textInfoAnimalDescricao);
        //...
        listaInfoAnimaisViagens     = findViewById(R.id.listaInfoAnimaisViagens);
    }

    public void iniciarBundle(){
    Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            animal = (Animal) bundle.getSerializable("ExibirAnimal");
            //imageInfoanimal = animal.getFotoAnimal();
            textInfoAnimalNome.setText(animal.getNomeAnimal());
            textInfoAnimalEspecie.setText(animal.getEspecieAnimal());
            textInfoAnimalRaca.setText(animal.getRacaAnimal());
            textInfoAnimalIDAnimal.setText(animal.getIdAnimal());
            textInfoAnimalDA.setText(animal.getIdUsuario());
            textInfoAnimalPorte.setText(animal.getPorteAnimal());
            //...
            textInfoAnimalDescricao.setText(animal.getObservacaoAnimal());
        }
    }
}
