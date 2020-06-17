package com.example.travelpetadm.ui.Motorista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.DAO.AnimalDAO;
import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.DAO.DonoAnimalDAO;
import com.example.travelpetadm.DAO.EnderecoDAO;
import com.example.travelpetadm.DAO.MotoristaDAO;
import com.example.travelpetadm.DAO.VeiculoDAO;
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.Model.Endereco;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.animais.AdapterListaAnimais;
import com.example.travelpetadm.ui.animais.InfoAnimalActivity;
import com.example.travelpetadm.ui.donoanimal.InfoDonoAnimalActivity;
import com.example.travelpetadm.ui.veiculos.AdapterListaVeiculos;
import com.example.travelpetadm.ui.veiculos.InfoVeiculosActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoMotoristaActivity extends AppCompatActivity {
    public static AdapterListaVeiculos adapterListaVeiculos;
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
    private FloatingActionButton fabAprovarMO,
                                 fabCnh,
                                 fabRejeitarMO;
    private RecyclerView listaPerfilMOVeiculo;
    private static ArrayList<Veiculo> veiculos = new ArrayList<>();
    private ImageView imgAprovacaoMO;
    private CircleImageView imgPerfilMO;
    Motorista motorista;
    Boolean validacao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_motorista);
        iniciarBundle();
        iniciarReciclerView();
        //aprovar(motorista.getStatusPerfil());
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarMotorista();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStop(){
        super.onStop();
        veiculoRef.removeEventListener(listenerVeiculo);
        refMO.removeEventListener(listenerMO);
        refEnd.removeEventListener(listenerEnd);
    }

    private void iniciarBundle() {
        iniciarComponentes();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            motorista = (Motorista) bundle.getSerializable("ExibirMotorista");
        }
    }

    public void recuperarMotorista(){
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
                    if (motorista.getStatusCadastro().equals(Conexao.motoristaEmAnalise)) {
                        imgAprovacaoMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_atencao));
                        fabAprovarMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_aprovar));
                        fabRejeitarMO.setVisibility(View.VISIBLE);
                    } else {if (motorista.getStatusCadastro().equals(Conexao.motoristaAprovado)) {
                        imgAprovacaoMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_aprovar));
                        fabAprovarMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_atencao));
                        fabRejeitarMO.setVisibility(View.VISIBLE);
                    }else {
                        if (motorista.getStatusCadastro().equals(Conexao.motoristaRejeitado)) {
                            imgAprovacaoMO.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_bloquear));
                            fabRejeitarMO.setVisibility(View.GONE);
                        }
                    }
                    }
                }
                if(!motorista.getFotoPerfilUrl().equals("")){
                    Uri fotoPerfilUri = Uri.parse(motorista.getFotoPerfilUrl());
                    Glide.with(InfoMotoristaActivity.this).load( fotoPerfilUri ).into( imgPerfilMO );
                }else{
                    imgPerfilMO.setImageResource(R.drawable.ic_menu_motorista);
                }
                aprovar(motorista.getStatusCadastro());
                recuperarEndereco();
                recuperarVeiculo();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void recuperarVeiculo (){
        veiculos.clear();
        veiculoRef = VeiculoDAO.getVeiculoRef().child(motorista.getIdUsuario());
        listenerVeiculo = veiculoRef.addValueEventListener(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dados: dataSnapshot.getChildren()) {
                            Veiculo veiculo = dados.getValue(Veiculo.class);
                            veiculos.add(veiculo);
                        }
                        adapterListaVeiculos.notifyDataSetChanged();
                    }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
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
        fabCnh = findViewById(R.id.fabCnh);
        fabRejeitarMO = findViewById(R.id.fabRejeitarMO);
        //ListView
        listaPerfilMOVeiculo = findViewById(R.id.listaPerfilMOVeiculo);
        //ImageView
        imgAprovacaoMO = findViewById(R.id.imgAprovacaoMO);
        imgPerfilMO = findViewById(R.id.imgPerfilMO);

    }

    public void aprovar (final String status) {
        final DatabaseReference aprovar = MotoristaDAO.getmotoritaReference().child(motorista.getIdUsuario());
        textPerfilUfMO.setText(status);
        //BOTAO APROVAR / EM ANALISE
        fabAprovarMO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.isEmpty()) {
                    if (status.equals(Conexao.motoristaEmAnalise)||status.equals(Conexao.motoristaRejeitado)) {
                        textPerfilStatusMO.setText(Conexao.motoristaAprovado);
                        motorista.setStatusCadastro(Conexao.motoristaAprovado);
                        aprovar.child(Conexao.statusCadastro).setValue(Conexao.motoristaAprovado);
                    } else {
                        if (status.equals(Conexao.motoristaAprovado)) {
                            textPerfilStatusMO.setText(Conexao.motoristaEmAnalise);
                            motorista.setStatusCadastro(Conexao.motoristaEmAnalise);
                            aprovar.child(Conexao.statusCadastro).setValue(Conexao.motoristaEmAnalise);
                        }
                    }
                }
            }
        });
        //BOTAO REJEITAR / EM ANALISE
        fabRejeitarMO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.isEmpty()) {
                    if (status.equals(Conexao.motoristaEmAnalise)||status.equals(Conexao.motoristaAprovado)) {
                        textPerfilStatusMO.setText(Conexao.motoristaRejeitado);
                        motorista.setStatusCadastro(Conexao.motoristaRejeitado);
                        aprovar.child(Conexao.statusCadastro).setValue(Conexao.motoristaRejeitado);
                    }
                }
            }
        });
        fabCnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), ExibirCnh.class);
                i.putExtra("ExibirCnh",motorista);
                startActivity(i);
            }
        });
    }
    public void iniciarReciclerView(){

            //configurar Adapter
            adapterListaVeiculos = new AdapterListaVeiculos(veiculos, getApplicationContext());
            //Configurar RecyclerView
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            listaPerfilMOVeiculo.setLayoutManager(layoutManager);
            listaPerfilMOVeiculo.setHasFixedSize(true);
            listaPerfilMOVeiculo.setAdapter(adapterListaVeiculos);
            //Configurar evento de clique
            listaPerfilMOVeiculo.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            getApplicationContext(),
                            listaPerfilMOVeiculo,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Veiculo veiculoSel =  veiculos.get(position);
                                    Intent i =  new Intent(getApplicationContext(), InfoVeiculosActivity.class);
                                    i.putExtra("ExibirVeiculo",veiculoSel);
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

    public void recuperarEndereco() {
        refEnd = Conexao.getFirebaseDatabase().child(Conexao.enderecoMO).child(motorista.getIdUsuario());
        listenerEnd = refEnd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Endereco endereco = dataSnapshot.getValue(Endereco.class);
                if (endereco != null) {
                    textPerfilBairroMO.setText(endereco.getBairro());
                    textPerfilCepMO.setText(endereco.getCep());
                    textPerfilCidadeMO.setText(endereco.getLocalidade());
                    textPerfilRuaMO.setText(endereco.getLogradouro());
                    textPerfilUfMO.setText(endereco.getUf());
                }else{
                    alert("Endereco NULL" + " -- " + refEnd.getKey() +" -- " + motorista.getIdUsuario() );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void alert(String S){
        Toast.makeText(this,S,Toast.LENGTH_SHORT).show();
    }
}//---fim classe
