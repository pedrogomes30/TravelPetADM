package com.example.travelpetadm.ui.donoanimal;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.DAO.AnimalDAO;
import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.DAO.DonoAnimalDAO;
import com.example.travelpetadm.DAO.EnderecoDAO;
import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.Model.Endereco;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.Encriptador;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.Avaliacao.AdapterListaAvaliacao;
import com.example.travelpetadm.ui.animais.AdapterListaAnimais;
import com.example.travelpetadm.ui.animais.InfoAnimalActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.concurrent.CountDownLatch;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.os.LocaleListCompat.create;
import static com.example.travelpetadm.DAO.AnimalDAO.getAnimalReference;
import static com.example.travelpetadm.DAO.AnimalDAO.recuperarArrayDonoAnimal;

public class InfoDonoAnimalActivity extends AppCompatActivity {
    public static AdapterListaAnimais adapterListaAnimal;
    public Endereco endereco;
    //firebase atributos
    DatabaseReference animalRef;
    ValueEventListener listenerAnimal;
    DatabaseReference refDA;
    ValueEventListener listenerDA;
    DatabaseReference ref;
    ValueEventListener listener;
    //componentes tela
    private TextView textPerfilNomeDA,
            textPerfilSobrenomeDA,
            textPerfilCPFDA,
            textPerfilEmailDA,
            textPerfilTipoPerfilDA,
            textPerfilAvaliacaoDA,
            textPerfilStatusDA;
    private TextView textPerfilBairroDA,
            textPerfilCepDA,
            textPerfilCidadeDA,
            textPerfilRuaDA,
            textPerfilUfDA;
    private FloatingActionButton fabAprovarDA;
    private RecyclerView listaPerfilDAAnimal;
    private static ArrayList<Animal> animais = new ArrayList<>();
    private ImageView imgAprovacao;
    private CircleImageView imgPerfilDA;
    DonoAnimal donoAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dono_animal);
        iniciarBundle();
        iniciarReciclerView();
        aprovar(donoAnimal.getStatusConta());
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarDonoAnimal();
    }

    private void iniciarComponentes() {
        //TextView
        textPerfilNomeDA = findViewById(R.id.textPerfilNomeDA);
        textPerfilSobrenomeDA = findViewById(R.id.textPerfilSobrenomeDA);
        textPerfilCPFDA = findViewById(R.id.textPerfilCPFDA);
        textPerfilEmailDA = findViewById(R.id.textPerfilEmailDA);
        textPerfilTipoPerfilDA = findViewById(R.id.textPerfilTipoPerfilDA);
        textPerfilStatusDA = findViewById(R.id.textPerfilStatusDA);
        textPerfilAvaliacaoDA = findViewById(R.id.textPerfilAvaliacaoDA);
        textPerfilBairroDA = findViewById(R.id.textPerfilBairroDA);
        textPerfilCepDA = findViewById(R.id.textPerfilCepDA);
        textPerfilCidadeDA = findViewById(R.id.textPerfilCidadeDA);
        textPerfilRuaDA = findViewById(R.id.textPerfilRuaDA);
        textPerfilUfDA = findViewById(R.id.textPerfilUfDA);
        fabAprovarDA = findViewById(R.id.fabAprovarDA);
        //ListView
        listaPerfilDAAnimal = findViewById(R.id.listaPerfilDAAnimal);
        listaPerfilDAAnimal.setHasFixedSize(true);
        //ImageView
        imgAprovacao = findViewById(R.id.imgAprovacao);
        imgPerfilDA = findViewById(R.id.imgPerfilDA);

    }

    private void iniciarBundle() {
        iniciarComponentes();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            donoAnimal = (DonoAnimal) bundle.getSerializable("ExibirDonoAnimal");
        }
    }
    public void recuperarDonoAnimal(){
            refDA = DonoAnimalDAO.getDonoAnimalReference().child(donoAnimal.getIdUsuario());
            listenerDA = refDA.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    donoAnimal = dataSnapshot.getValue(DonoAnimal.class);
                    textPerfilNomeDA.setText(donoAnimal.getNome());
                    textPerfilSobrenomeDA.setText(donoAnimal.getSobrenome());
                    textPerfilCPFDA.setText(donoAnimal.getCpf());
                    textPerfilEmailDA.setText(donoAnimal.getEmail());
                    textPerfilTipoPerfilDA.setText(donoAnimal.getTipoUsuario());
                    textPerfilAvaliacaoDA.setText(String.valueOf(donoAnimal.getAvaliacao()));
                    textPerfilStatusDA.setText(donoAnimal.getStatusConta());
                    if(donoAnimal.getAvaliacao()==null)textPerfilAvaliacaoDA.setText("0,0");
                    if (!donoAnimal.getStatusConta().isEmpty()) {
                        if (donoAnimal.getStatusConta().equals(Conexao.donoAnimalAtivo)) {
                            imgAprovacao.setImageDrawable(getResources().getDrawable(R.drawable.ic_aprovar));
                            fabAprovarDA.setImageDrawable(getResources().getDrawable(R.drawable.ic_desaprovar));
                        } else {if (donoAnimal.getStatusConta().equals(Conexao.donoAnimalBloqueado)) {
                            imgAprovacao.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_bloquear));
                            fabAprovarDA.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_aprovar));
                             }
                        }
                    }
                    if(donoAnimal.getFotoPerfilUrl()!=null){
                        Uri fotoPerfilUri = Uri.parse(donoAnimal.getFotoPerfilUrl());
                        Glide.with(InfoDonoAnimalActivity.this).load( fotoPerfilUri ).into( imgPerfilDA );
                    }else{
                        imgPerfilDA.setImageResource(R.drawable.ic_dono_animal);
                    }
                    aprovar(donoAnimal.getStatusConta());
                    recuperarEndereco();
                    recuperarAnimal();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }


    public void aprovar (final String status) {
        final DatabaseReference aprovar = DonoAnimalDAO.getDonoAnimalReference().child(donoAnimal.getIdUsuario());
        textPerfilStatusDA.setText(status);
        fabAprovarDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.isEmpty()) {
                    if (status.equals(Conexao.donoAnimalAtivo)) {
                        textPerfilStatusDA.setText(Conexao.donoAnimalBloqueado);
                        donoAnimal.setStatusConta(Conexao.donoAnimalBloqueado);
                        aprovar.child(Conexao.statusPerfil).setValue(Conexao.donoAnimalBloqueado);
                    } else {
                        if (status.equals(Conexao.donoAnimalBloqueado)) {
                            textPerfilStatusDA.setText(Conexao.donoAnimalAtivo);
                            donoAnimal.setFotoPerfilUrl(Conexao.donoAnimalAtivo);
                            aprovar.child(Conexao.statusPerfil).setValue(Conexao.donoAnimalAtivo);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onStop(){
        super.onStop();
        animalRef.removeEventListener(listenerAnimal);
        refDA.removeEventListener(listenerDA);
        ref.removeEventListener(listener);
    }

    public void recuperarAnimal (){
       // animais = AnimalDAO.recuperarArrayDonoAnimal(id,animais);
        animais.clear();
        animalRef = AnimalDAO.getAnimalReference().child(donoAnimal.getIdUsuario());
        listenerAnimal = animalRef
                .addValueEventListener(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot animalDs: dataSnapshot.getChildren()) {
                            Animal animal = animalDs.getValue(Animal.class);
                            animais.add(animal);
                        }
                        adapterListaAnimal.notifyDataSetChanged();
                    }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
    }

    public void iniciarReciclerView() {

        //configurar Adapter
        adapterListaAnimal = new AdapterListaAnimais(animais, getApplicationContext());
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaPerfilDAAnimal.setLayoutManager(layoutManager);
        listaPerfilDAAnimal.setHasFixedSize(true);
        listaPerfilDAAnimal.setAdapter(adapterListaAnimal);
        //Configurar evento de clique
        listaPerfilDAAnimal.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listaPerfilDAAnimal,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Animal animalSel =  animais.get(position);
                                Intent i =  new Intent(getApplicationContext(), InfoAnimalActivity.class);
                                i.putExtra("ExibirAnimal",animalSel);
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
        ref = Conexao.getFirebaseDatabase().child(Conexao.enderecoDA).child(donoAnimal.getIdUsuario());
        listener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Endereco endereco = dataSnapshot.getValue(Endereco.class);
                if (endereco != null) {
                    textPerfilBairroDA.setText(endereco.getBairro());
                    textPerfilCepDA.setText(endereco.getCep());
                    textPerfilCidadeDA.setText(endereco.getLocalidade());
                    textPerfilRuaDA.setText(endereco.getLogradouro());
                    textPerfilUfDA.setText(endereco.getUf());
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
}
