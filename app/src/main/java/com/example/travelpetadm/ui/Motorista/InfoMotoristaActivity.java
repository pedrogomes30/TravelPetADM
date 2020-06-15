package com.example.travelpetadm.ui.Motorista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.DAO.DonoAnimalDAO;
import com.example.travelpetadm.DAO.MotoristaDAO;
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.Model.Endereco;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.animais.AdapterListaAnimais;
import com.example.travelpetadm.ui.donoanimal.InfoDonoAnimalActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoMotoristaActivity extends AppCompatActivity {
    public static AdapterMotorista adapterListaAnimal;
    public Endereco endereco;
    //firebase atributos
    DatabaseReference veiculoRef;
    ValueEventListener listenerVeiculo;
    DatabaseReference refMO;
    ValueEventListener listenerMO;
    DatabaseReference refEnd;
    ValueEventListener listenerEnd;
    //componentes tela
    private TextView textPerfilNomeMO,
            textPerfilSobrenomeMO,
            textPerfilCPFMO,
            textPerfilEmailMO,
            textPerfilTipoPerfilMO,
            textPerfilAvaliacaoMO,
            textPerfilStatusMO;
    private TextView textPerfilBairroMO,
            textPerfilCepMO,
            textPerfilCidadeMO,
            textPerfilRuaMO,
            textPerfilUfMO;
    private FloatingActionButton fabAprovarMO;
    private RecyclerView listaPerfilMOVeiculo;
    private static ArrayList<Veiculo> veiculos = new ArrayList<>();
    private ImageView imgAprovacaoMO;
    private CircleImageView imgPerfilMO;
    Motorista motorista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_motorista);
        iniciarBundle();
        //iniciarReciclerView();
       // aprovar(motorista.getStatusPerfil());
    }

    private void iniciarBundle() {
        iniciarComponentes();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            motorista = (Motorista) bundle.getSerializable("ExibirMotorista");
            refMO = MotoristaDAO.getmotoritaReference().child(motorista.getIdUsuario());
            listenerMO = refMO.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    motorista = dataSnapshot.getValue(Motorista.class);
                    textPerfilNomeMO.setText(motorista.getNome());
                    textPerfilSobrenomeMO.setText(motorista.getSobrenome());
                    textPerfilCPFMO.setText(motorista.getCpf());
                    textPerfilEmailMO.setText(motorista.getEmail());
                    textPerfilTipoPerfilMO.setText(motorista.getTipoUsuario());
                    textPerfilAvaliacaoMO.setText(String.valueOf(motorista.getAvaliacao()));
                    textPerfilStatusMO.setText(motorista.getStatusCadastro());
                    if(motorista.getAvaliacao()==null)textPerfilAvaliacaoMO.setText("0,0");
                    if (!motorista.getStatusCadastro().isEmpty()) {
                        if (motorista.getStatusCadastro().equals("ativo")) {
                            imgAprovacaoMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_aprovar));
                            fabAprovarMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_desaprovar));
                        } else {if (motorista.getStatusCadastro().equals("bloqueado")) {
                            imgAprovacaoMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_bloquear));
                            fabAprovarMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_aprovar));
                        }
                        }
                    }
                    if(!motorista.getFotoPerfilUrl().equals("")){
                        Uri fotoPerfilUri = Uri.parse(motorista.getFotoPerfilUrl());
                        Glide.with(InfoMotoristaActivity.this).load( fotoPerfilUri ).into( imgPerfilMO );
                    }else{
                        imgPerfilMO.setImageResource(R.drawable.ic_menu_motorista);
                    }
                    //aprovar(donoAnimal.getStatusPerfil());
                    //recuperarEndereco();
                    //recuperarAnimal();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
    private void iniciarComponentes() {
        //TextView
        textPerfilNomeMO = findViewById(R.id.textPerfilNomeMO);
        textPerfilSobrenomeMO = findViewById(R.id.textPerfilSobrenomeMO);
        textPerfilCPFMO = findViewById(R.id.textPerfilCPFMO);
        textPerfilEmailMO = findViewById(R.id.textPerfilEmailMO);
        textPerfilTipoPerfilMO = findViewById(R.id.textPerfilTipoPerfilMO);
        textPerfilStatusMO = findViewById(R.id.textPerfilStatusMO);
        textPerfilAvaliacaoMO = findViewById(R.id.textPerfilAvaliacaoMO);
        textPerfilBairroMO = findViewById(R.id.textPerfilBairroMO);
        textPerfilCepMO = findViewById(R.id.textPerfilCepMO);
        textPerfilCidadeMO = findViewById(R.id.textPerfilCidadeMO);
        textPerfilRuaMO = findViewById(R.id.textPerfilRuaMO);
        textPerfilUfMO = findViewById(R.id.textPerfilUfMO);
        fabAprovarMO = findViewById(R.id.fabAprovarMO);
        //ListView
        listaPerfilMOVeiculo = findViewById(R.id.listaPerfilMOVeiculo);
        //ImageView
        imgAprovacaoMO = findViewById(R.id.imgAprovacaoMO);
        imgPerfilMO = findViewById(R.id.imgPerfilMO);

    }

}//---fim classe
