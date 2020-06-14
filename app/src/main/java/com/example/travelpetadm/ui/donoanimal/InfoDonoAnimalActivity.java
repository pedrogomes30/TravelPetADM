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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.travelpetadm.DAO.AnimalDAO;
import com.example.travelpetadm.DAO.Conexao;
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

import static androidx.core.os.LocaleListCompat.create;
import static com.example.travelpetadm.DAO.AnimalDAO.recuperarArrayDonoAnimal;

public class InfoDonoAnimalActivity extends AppCompatActivity {
    public static AdapterListaAnimais adapterListaAnimal;
    private TextView textPerfilNomeDA,
             textPerfilSobrenomeDA,
             textPerfilCPFDA,
             textPerfilEmailDA,
             textPerfilTipoPerfilDA,
            textPerfilAvaliacao,
             textPerfilStatusDA;
    private TextView textPerfilBairroDA,
            textPerfilCepDA,
            textPerfilCidadeDA,
            textPerfilRuaDA,
            textPerfilUfDA;
    private FloatingActionButton fabAprovarDA;

    private RecyclerView listaPerfilDAAnimal;

    private static ArrayList<Animal> animais =new ArrayList<>() ;
    private static ArrayList<Endereco> enderecos =new ArrayList<>() ;

    private ImageView imgAprovacao;

    DonoAnimal donoAnimal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dono_animal);
        iniciarBundle();
        iniciarReciclerView();

    }
    private void iniciarComponentes(){
        //TextView
        textPerfilNomeDA                = findViewById(R.id.textPerfilNomeDA);
        textPerfilSobrenomeDA           = findViewById(R.id.textPerfilSobrenomeDA);
        textPerfilCPFDA                 = findViewById(R.id.textPerfilCPFDA);
        textPerfilEmailDA               = findViewById(R.id.textPerfilEmailDA);
        textPerfilTipoPerfilDA          = findViewById(R.id.textPerfilTipoPerfilDA);
        textPerfilStatusDA              = findViewById(R.id.textPerfilStatusDA);
        textPerfilAvaliacao           = findViewById(R.id.textPerfilAvaliacaoDA);
        textPerfilBairroDA              = findViewById(R.id.textPerfilBairroDA);
        textPerfilCepDA                 = findViewById(R.id.textPerfilCepDA);
        textPerfilAvaliacao              = findViewById(R.id.textPerfilAvaliacao);
        textPerfilRuaDA                 = findViewById(R.id.textPerfilRuaDA);
        textPerfilUfDA                  = findViewById(R.id.textPerfilUfDA);
        fabAprovarDA                    = findViewById(R.id.fabAprovarDA);
        //ListView
        listaPerfilDAAnimal             = findViewById(R.id.listaPerfilDAAnimal);
        //ImageView
        imgAprovacao                    = findViewById(R.id.imgAprovacao);

    }
    private void iniciarBundle(){
        iniciarComponentes();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            donoAnimal = (DonoAnimal) bundle.getSerializable("ExibirDonoAnimal");
            textPerfilNomeDA.setText(donoAnimal.getNome());
            textPerfilSobrenomeDA.setText(donoAnimal.getSobrenome());
            textPerfilCPFDA.setText(donoAnimal.getCpf());
            textPerfilEmailDA.setText(donoAnimal.getEmail());
            textPerfilTipoPerfilDA.setText(donoAnimal.getTipoUsuario());
            textPerfilStatusDA.setText(donoAnimal.getStatusPerfil());
            textPerfilAvaliacao.setText(String.valueOf( donoAnimal.getAvaliacao()));

            if(donoAnimal.getStatusPerfil() == "ativo"){
                imgAprovacao.setImageDrawable(getResources().getDrawable(R.drawable.ic_aprovar));
                fabAprovarDA.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_bloquear));
            }else{
                imgAprovacao.setImageDrawable(getResources().getDrawable((R.drawable.ic_desaprovar)));
                fabAprovarDA.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_aprovar));
            }
        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        recuperarAnimal();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public void recuperarAnimal (){
        String id = Encriptador.codificarBase64(donoAnimal.getEmail());
        animais = AnimalDAO.recuperarArrayDonoAnimal(id,animais);
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

}
