package com.example.travelpetadm.ui.animais;

import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.travelpetadm.DAO.AnimalDAO;
import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;
import com.example.travelpetadm.DAO.GeradorXls;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.Avaliacao.AdapterListaAvaliacao;
import com.example.travelpetadm.ui.TipoAnimal.AdapterListaTipoAnimal;
import com.example.travelpetadm.ui.TipoAnimal.AdicionarTipoAnimalActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AnimaisFragment extends Fragment {
    private RecyclerView recyclerView;
    public static AdapterListaAnimais adapterListaAnimal;
    private ArrayList<Animal> animais =new ArrayList<>() ;
    private DatabaseReference animalRef;
    private ValueEventListener valueEventListenerAnimal;
    private ProgressBar progressoAnimal;
    View view;

    public  AnimaisFragment () {    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_animais, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarAnimal();
    }

    @Override
    public void onStop(){
        super.onStop();
        //animalRef.removeEventListener(valueEventListenerAnimal);
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listaAnimais);
        animalRef  = Conexao.getFirebaseDatabase().child("animais");
        progressoAnimal = view.findViewById(R.id.progressoAnimal);
    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaAnimal = new AdapterListaAnimais(animais, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaAnimal);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                               Animal animalSel =  animais.get(position);
                                Intent i =  new Intent(getActivity(), InfoAnimalActivity.class);
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

    public void recuperarAnimal (){
        AnimalDAO.recuperarArrayAnimais(animais);
        progressoAnimal.setVisibility(View.GONE);
       /* valueEventListenerAnimal = animalRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                animais.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for (DataSnapshot animalDs : dados.getChildren()) {
                        Animal animal = animalDs.getValue(Animal.class);
                        animais.add(animal);
                        progressoAnimal.setVisibility(View.VISIBLE);
                    }
                }
               adapterListaAnimal.notifyDataSetChanged();
                progressoAnimal.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });*/

    }

    //BOTAO DE MENU
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //necessário ou o botão e selecionado em qualquer ação
        switch(item.getItemId()){
            case R.id.action_salvar:
                new GeradorXls("Animal", view.getContext());
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
