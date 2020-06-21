package com.example.travelpetadm.ui.veiculos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.DAO.MotoristaDAO;
import com.example.travelpetadm.DAO.VeiculoDAO;
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.Encriptador;
import com.example.travelpetadm.ui.Motorista.ExibirCnh;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class InfoVeiculosActivity extends AppCompatActivity {
    private ImageView imageInfoVeiculo;
    private TextView textInfoVeiculoMarca,
            textInfoVeiculoModelo,
            textInfoVeiculoID,
            textInfoVeiculoMotorista,
            textInfoVeiculoStatus,
            textInfoVeiculoPlaca,
            textInfoVeiculoAno;
    private Veiculo veiculo;
    private FloatingActionButton fabRejeitarVei,
                                 fabCrvl,
                                 fabAprovarVei;
    DatabaseReference refVeiculo;
    ValueEventListener listenerVeiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_veiculos);
        iniciarComponentes();
        iniciarBundle();
        recuperarVeiculo();
    }

    public void iniciarComponentes(){
        textInfoVeiculoMarca             = findViewById(R.id.textInfoVeiculoMarca);
        textInfoVeiculoModelo            = findViewById(R.id.textInfoVeiculoModelo);
        textInfoVeiculoID                = findViewById(R.id.textInfoVeiculoID);
        textInfoVeiculoMotorista         = findViewById(R.id.textInfoVeiculoMotorista);
        textInfoVeiculoStatus            = findViewById(R.id.textInfoVeiculoStatus);
        textInfoVeiculoPlaca             = findViewById(R.id.textInfoVeiculoPlaca);
        textInfoVeiculoAno               = findViewById(R.id.textInfoVeiculoAno);
        //...
        fabRejeitarVei                   =findViewById(R.id.fabRejeitarVei);
        fabCrvl                          =findViewById(R.id.fabCrvl);
        fabAprovarVei                    =findViewById(R.id.fabAprovarVei);
    }
    public void iniciarBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            veiculo = (Veiculo) bundle.getSerializable("ExibirVeiculo");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarVeiculo();
    }
    @Override
    public void onStop(){
        super.onStop();
        //refVeiculo.removeEventListener(listenerVeiculo);
    }
    public void recuperarVeiculo(){

        refVeiculo = VeiculoDAO.getVeiculoRef().child(veiculo.getIdUsuario()).child(veiculo.getIdVeiculo());
        listenerVeiculo=refVeiculo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Veiculo veiculo = dataSnapshot.getValue(Veiculo.class);
                    textInfoVeiculoMarca.setText(veiculo.getMarcaVeiculo());
                    textInfoVeiculoModelo.setText(veiculo.getModeloVeiculo());
                    textInfoVeiculoID.setText(veiculo.getIdVeiculo());
                    textInfoVeiculoMotorista.setText(Encriptador.decodificarBase64(veiculo.getIdUsuario()));
                    textInfoVeiculoStatus.setText(veiculo.getStatus());
                    textInfoVeiculoPlaca.setText(veiculo.getPlacaVeiculo());
                    textInfoVeiculoAno.setText(veiculo.getAnoVeiculo());
                    if (!veiculo.getStatus().isEmpty()) {
                        if (veiculo.getStatus().equals(Conexao.veiculoEmAnalise)) {
                            fabAprovarVei.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_aprovar));
                            fabRejeitarVei.setVisibility(View.VISIBLE);
                        }
                        if (veiculo.getStatus().equals(Conexao.veiculoAprovado)) {
                            fabAprovarVei.setImageDrawable(getResources().getDrawable(R.drawable.ic_atencao));
                            fabRejeitarVei.setVisibility(View.VISIBLE);
                        }
                        if (veiculo.getStatus().equals(Conexao.veiculoBloqueado)) {
                            fabAprovarVei.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_aprovar));
                            fabRejeitarVei.setVisibility(View.GONE);
                        }
                    }
                    aprovar(veiculo.getStatus());
                    alert(veiculo.getStatus());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
    public void aprovar (final String status) {
        //BOTAO APROVAR / EM ANALISE
        final DatabaseReference aprovar = VeiculoDAO.getVeiculoRef()
                                            .child(veiculo.getIdUsuario())
                                            .child(veiculo.getIdVeiculo());
        fabAprovarVei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.isEmpty()) {
                    if (status.equals(Conexao.veiculoEmAnalise)||status.equals(Conexao.veiculoBloqueado)) {
                        textInfoVeiculoStatus.setText(Conexao.veiculoAprovado);
                        veiculo.setStatus(Conexao.veiculoAprovado);
                        aprovar.child(Conexao.statusVeiculo).setValue(Conexao.veiculoAprovado);
                    } else {
                        if (status.equals(Conexao.veiculoAprovado)) {
                            textInfoVeiculoStatus.setText(Conexao.motoristaEmAnalise);
                            veiculo.setStatus(Conexao.veiculoEmAnalise);
                            aprovar.child(Conexao.statusVeiculo).setValue(Conexao.veiculoEmAnalise);
                        }
                    }
                }
            }
        });
        //BOTAO REJEITAR / EM ANALISE
        fabRejeitarVei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.isEmpty()) {
                    if (status.equals(Conexao.veiculoEmAnalise)||status.equals(Conexao.veiculoAprovado)) {
                        textInfoVeiculoStatus.setText(Conexao.veiculoBloqueado);
                        veiculo.setStatus(Conexao.veiculoBloqueado);
                        aprovar.child(Conexao.statusVeiculo).setValue(Conexao.veiculoBloqueado);
                    }
                }
            }
        });
        fabCrvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), ExibirCrvl.class);
                i.putExtra("ExibirCrvl",veiculo);
                startActivity(i);
            }
        });
    }
    private void alert(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}

