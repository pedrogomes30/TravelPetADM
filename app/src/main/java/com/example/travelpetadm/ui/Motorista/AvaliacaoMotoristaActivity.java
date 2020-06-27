package com.example.travelpetadm.ui.Motorista;

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
import com.example.travelpetadm.DAO.MotoristaDAO;
import com.example.travelpetadm.Model.Avaliacao;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.Avaliacao.AdapterListaAvaliacao;
import com.example.travelpetadm.ui.Avaliacao.InfoAvaliacaoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AvaliacaoMotoristaActivity extends AppCompatActivity {
    public static AdapterListaAvaliacao adapterListaAvaliacao;
    Motorista motorista;
    DonoAnimal donoAnimal;
    private Double nota = 0.0 ;
    private int divisor = 0;
    private String idUsuario;
    private CircleImageView imgAvaliacao;
    private TextView textAvaliacaoNome,
                     textAvaliacaoSobrenome,
                     textAvaliacaoEmail,
                     textAvaliacaoTipoPerfil,
                     textAvaliacaoNota;
    private RecyclerView listaAvaliacoesPerfil;
    private ArrayList<Avaliacao> avaliacoes =new ArrayList<>();
    //temporárias
    private DatabaseReference refAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_perfil);
        iniciarComponentes();
        iniciarBundle();
        iniciarReciclerView();
    }

    public void iniciarComponentes() {
        imgAvaliacao = findViewById(R.id.imgAvaliacao);
        textAvaliacaoNome = findViewById(R.id.textAvaliacaoNome);
        textAvaliacaoSobrenome = findViewById(R.id.textAvaliacaoSobrenome);
        textAvaliacaoEmail = findViewById(R.id.textAvaliacaoEmail);
        textAvaliacaoTipoPerfil = findViewById(R.id.textAvaliacaoTipoPerfil);
        textAvaliacaoNota = findViewById(R.id.textAvaliacaoNota);
        listaAvaliacoesPerfil = findViewById(R.id.listaAvaliacoesPerfil);
    }

    public void iniciarBundle(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            motorista = (Motorista) bundle.getSerializable("ExibirAvaliacoesMotorista");
            textAvaliacaoNome.setText(motorista.getNome());
            textAvaliacaoSobrenome.setText(motorista.getSobrenome());
            textAvaliacaoEmail.setText(motorista.getEmail());
            textAvaliacaoTipoPerfil.setText(motorista.getTipoUsuario());
            motorista.setNotaAvaliacao(0.0);
            textAvaliacaoNota.setText(String.valueOf(motorista.getNotaAvaliacao()));
            idUsuario = motorista.getIdUsuario();

        }
        if(motorista != null){
            Uri fotoPerfilUri = Uri.parse(motorista.getFotoPerfilUrl());
            Glide.with(AvaliacaoMotoristaActivity.this).load( fotoPerfilUri ).into( imgAvaliacao );
        }else{
            imgAvaliacao.setImageResource(R.drawable.ic_star_border_black_24dp);
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
          motorista.setNotaAvaliacao(resultado);
        }
        textAvaliacaoNota.setText(String.valueOf(motorista.getNotaAvaliacao()));
        if (motorista.getNotaAvaliacao() >= 4) {
            textAvaliacaoNota.setText(String.valueOf(motorista.getNotaAvaliacao()));
            textAvaliacaoNota.setTextColor(Color.GREEN);
        } else {
            if (motorista.getNotaAvaliacao() < 4 && motorista.getNotaAvaliacao() >= 3) {
                textAvaliacaoNota.setText(String.valueOf(motorista.getNotaAvaliacao()));
                textAvaliacaoNota.setTextColor(Color.YELLOW);
            } else {
                if (motorista.getNotaAvaliacao() < 3) {
                    textAvaliacaoNota.setText(String.valueOf(motorista.getNotaAvaliacao()));
                    textAvaliacaoNota.setTextColor(Color.RED);
                }
            }
        }
        if(motorista.getIdUsuario() != null|| motorista.getIdUsuario() != "") {
            refAvaliacao = null;
            refAvaliacao = MotoristaDAO.getmotoritaReference().child(motorista.getIdUsuario());
           refAvaliacao.child("notaAvaliacao").setValue(motorista.getNotaAvaliacao());
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
        listaAvaliacoesPerfil.setLayoutManager(layoutManager);
        listaAvaliacoesPerfil.setHasFixedSize(true);
        listaAvaliacoesPerfil.setAdapter(adapterListaAvaliacao);
        //Configurar evento de clique
        listaAvaliacoesPerfil.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listaAvaliacoesPerfil,
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
