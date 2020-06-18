package com.example.travelpetadm.ui.animais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.DAO.AnimalDAO;
import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.DAO.TipoAnimalDAO;
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.Encriptador;
import com.example.travelpetadm.ui.Motorista.InfoMotoristaActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoAnimalActivity extends AppCompatActivity {
    private CircleImageView imageInfoAnimal,
                            imageInfoEspecie;
    private TextView textInfoAnimalNome,
            textInfoAnimalEspecie,
            textInfoAnimalRaca,
            textInfoAnimalIDAnimal,
            textInfoAnimalDA,
            textInfoAnimalPorte;
    private TextView textInfoAnimalDescricao;
    private Animal animal;
    ValueEventListener  listener;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_animal);
        iniciarComponentes();
        iniciarBundle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarTipoAnimal();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ref.removeEventListener(listener);
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
        imageInfoAnimal     = findViewById(R.id.imageInfoAnimal);
        imageInfoEspecie     = findViewById(R.id.imageInfoEspecie);
        //...
    }

    public void iniciarBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            animal = (Animal) bundle.getSerializable("ExibirAnimal");
            //imageInfoanimal = animal.getFotoAnimal();
            textInfoAnimalNome.setText(animal.getNomeAnimal());
            textInfoAnimalEspecie.setText(animal.getEspecieAnimal());
            textInfoAnimalRaca.setText(animal.getRacaAnimal());
            textInfoAnimalIDAnimal.setText(animal.getIdAnimal());
            textInfoAnimalDA.setText(Encriptador.decodificarBase64(animal.getIdUsuario()));
            textInfoAnimalPorte.setText(animal.getPorteAnimal());
            //...
            textInfoAnimalDescricao.setText(animal.getObservacaoAnimal());
        }
    }
    public void recuperarTipoAnimal(){
            if (animal.getFotoAnimal()!=null) {
                Uri fotoPerfilUri = Uri.parse(animal.getFotoAnimal());
                Glide.with(InfoAnimalActivity.this).load(fotoPerfilUri).into(imageInfoAnimal);
            } else {
                imageInfoAnimal.setImageResource(R.drawable.ic_especie_spinner);
            }
            ref = TipoAnimalDAO.getTipoAnimalReference().child(animal.getEspecieAnimal());
            listener = ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            TipoAnimal tipoAnimal = dataSnapshot.getValue(TipoAnimal.class);
                            if (tipoAnimal.getIconeUrl()!=null) {
                                Uri fotoPerfilUri = Uri.parse(tipoAnimal.getIconeUrl());
                                Glide.with(InfoAnimalActivity.this).load(fotoPerfilUri).into(imageInfoEspecie);
                            }else {
                                imageInfoEspecie.setImageResource(R.drawable.ic_especie_spinner);
                            }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }
}
