package com.example.travelpetadm.ui.donoanimal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.DAO.AvaliacaoDAO;
import com.example.travelpetadm.DAO.DonoAnimalDAO;
import com.example.travelpetadm.DAO.MotoristaDAO;
import com.example.travelpetadm.Model.Avaliacao;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.Avaliacao.AdapterListaAvaliacao;
import com.example.travelpetadm.ui.Avaliacao.InfoAvaliacaoActivity;
import com.example.travelpetadm.ui.Motorista.AvaliacaoMotoristaActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AvaliacaoDonoAnimalActivity extends AppCompatActivity {
    public static AdapterListaAvaliacao adapterListaAvaliacao;
    DonoAnimal donoAnimal;
    private Double nota = 0.0 ;
    private int divisor = 0;
    private String idUsuario;
    private CircleImageView imgAvaliacaoDA;
    private TextView textAvaliacaoNomeDA,
            textAvaliacaoSobrenomeDA,
            textAvaliacaoEmailDA,
            textAvaliacaoTipoPerfilDA,
            textAvaliacaoNotaDA;
    private RecyclerView listaAvaliacoesPerfilDA;
    private ArrayList<Avaliacao> avaliacoes =new ArrayList<>();
    //temporárias
    private DatabaseReference refAvaliacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_dono_animal);
        iniciarComponentes();
        iniciarBundle();
        iniciarReciclerView();
    }

    public void iniciarComponentes() {
        imgAvaliacaoDA = findViewById(R.id.imgAvaliacaoDA);
        textAvaliacaoNomeDA = findViewById(R.id.textAvaliacaoNomeDA);
        textAvaliacaoSobrenomeDA = findViewById(R.id.textAvaliacaoSobrenomeDA);
        textAvaliacaoEmailDA = findViewById(R.id.textAvaliacaoEmailDA);
        textAvaliacaoTipoPerfilDA = findViewById(R.id.textAvaliacaoTipoPerfilDA);
        textAvaliacaoNotaDA = findViewById(R.id.textAvaliacaoNotaDA);
        listaAvaliacoesPerfilDA = findViewById(R.id.listaAvaliacoesPerfilDA);
    }

    public void iniciarBundle(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            donoAnimal = (DonoAnimal) bundle.getSerializable("ExibirAvaliacoesDonoAnimal");
            textAvaliacaoNomeDA.setText(donoAnimal.getNome());
            textAvaliacaoSobrenomeDA.setText(donoAnimal.getSobrenome());
            textAvaliacaoEmailDA.setText(donoAnimal.getEmail());
            textAvaliacaoTipoPerfilDA.setText(donoAnimal.getTipoUsuario());
            donoAnimal.setNotaAvaliacao(0.0);
            textAvaliacaoNotaDA.setText(String.valueOf(donoAnimal.getNotaAvaliacao()));
            idUsuario = donoAnimal.getIdUsuario();

        }
        if(donoAnimal != null){
            Uri fotoPerfilUri = Uri.parse(donoAnimal.getFotoPerfilUrl());
            Glide.with(AvaliacaoDonoAnimalActivity.this).load( fotoPerfilUri ).into( imgAvaliacaoDA );
        }else{
            imgAvaliacaoDA.setImageResource(R.drawable.ic_star_border_black_24dp);
            Toast.makeText(getApplicationContext(),"Usuário sem foto de perfil",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarAvaliacao();
    }

    public void salvarAvaliacao(){
        if(nota != 0 || divisor != 0) {
            Double resultado = nota/divisor;
            Math.round(resultado);
            donoAnimal.setNotaAvaliacao(resultado);
        }
        textAvaliacaoNotaDA.setText(String.valueOf(donoAnimal.getNotaAvaliacao()));
        if (donoAnimal.getNotaAvaliacao() >= 4) {
            textAvaliacaoNotaDA.setText(String.valueOf(donoAnimal.getNotaAvaliacao()));
            textAvaliacaoNotaDA.setTextColor(Color.GREEN);
        } else {
            if (donoAnimal.getNotaAvaliacao() < 4 && donoAnimal.getNotaAvaliacao() >= 3) {
                textAvaliacaoNotaDA.setText(String.valueOf(donoAnimal.getNotaAvaliacao()));
                textAvaliacaoNotaDA.setTextColor(Color.YELLOW);
            } else {
                if (donoAnimal.getNotaAvaliacao() < 3) {
                    textAvaliacaoNotaDA.setText(String.valueOf(donoAnimal.getNotaAvaliacao()));
                    textAvaliacaoNotaDA.setTextColor(Color.RED);
                }
            }
        }
        if(donoAnimal.getIdUsuario() != null|| donoAnimal.getIdUsuario() != "") {
            refAvaliacao = null;
            refAvaliacao = DonoAnimalDAO.getDonoAnimalReference().child(donoAnimal.getIdUsuario());
            refAvaliacao.child("notaAvaliacao").setValue(donoAnimal.getNotaAvaliacao());
        }
    }

    public void recuperarAvaliacao (){
        // avaliacoes= AvaliacaoDAO.recuperarAvaliacaoPerfil(avaliacoes, idUsuario);
        avaliacoes.clear();
        refAvaliacao = null;
        refAvaliacao = AvaliacaoDAO.getRefAvaliacao().child(idUsuario);
        refAvaliacao.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avaliacoes.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    Avaliacao avaliacao = dados.getValue(Avaliacao.class);
                    avaliacoes.add(avaliacao);
                    nota = nota + avaliacao.getNotaAvaliacao();
                    divisor++;
                }
                salvarAvaliacao();
                adapterListaAvaliacao.notifyDataSetChanged();
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());}
        });
    }

    public void iniciarReciclerView() {
        //configurar Adapter
        adapterListaAvaliacao = new AdapterListaAvaliacao(avaliacoes, getApplicationContext());
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaAvaliacoesPerfilDA.setLayoutManager(layoutManager);
        listaAvaliacoesPerfilDA.setHasFixedSize(true);
        listaAvaliacoesPerfilDA.setAdapter(adapterListaAvaliacao);
        //Configurar evento de clique
        listaAvaliacoesPerfilDA.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listaAvaliacoesPerfilDA,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Avaliacao avaliacao =  avaliacoes.get(position);
                                Intent i =  new Intent(getApplicationContext(), InfoAvaliacaoActivity.class);
                                i.putExtra("informacoes avaliacao",avaliacao);
                                startActivity(i);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {
                            }
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            }
                        }
                )
        );
    }

}


