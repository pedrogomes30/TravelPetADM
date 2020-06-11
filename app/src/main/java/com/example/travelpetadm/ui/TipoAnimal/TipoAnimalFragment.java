package com.example.travelpetadm.ui.TipoAnimal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
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

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.DAO.TipoAnimalDAO;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.GeradorXls;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.contasAdm.AdicionarAdmActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import static com.example.travelpetadm.DAO.TipoAnimalDAO.recuperarArrayAdapter;

public class TipoAnimalFragment extends Fragment {
    private RecyclerView recyclerView;
    // é necessário ser publico e estático devido a classe DAO informar os itens salvos na classe Adapter intanciada dentro do fragment
    public static AdapterListaTipoAnimal adapterListaTipoAnimal;
    private ArrayList<TipoAnimal> tiposAnimais =new ArrayList<>() ;
    private ProgressBar progresso;
    private FloatingActionButton fabAdicionarTipoAnimal;
    View view;

    public  TipoAnimalFragment() {    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tipo_animal, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes();
        iniciarReciclerView();
        adicionarTipoAnimal();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarTipoAnimal();
    }

    public void iniciarComponentes(){
        recyclerView = view.findViewById(R.id.listaTiposAnimais);
        progresso = view.findViewById(R.id.progressoTipoAnimal);
        fabAdicionarTipoAnimal = view.findViewById(R.id.fabAdicionarTipoAnimal);
    }

    public void iniciarReciclerView() {
        //configurar Adapter
        adapterListaTipoAnimal = new AdapterListaTipoAnimal(tiposAnimais, getActivity());
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaTipoAnimal);
        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TipoAnimal tipoAnimalSel =  tiposAnimais.get(position);
                                Intent i =  new Intent(getActivity(),AdicionarTipoAnimalActivity.class);
                                i.putExtra("EditarTipoAnimal",tipoAnimalSel);
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

    public void adicionarTipoAnimal() {
        fabAdicionarTipoAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdicionarTipoAnimalActivity.class));
            }
        });
    }

    public void recuperarTipoAnimal (){
        tiposAnimais= TipoAnimalDAO.recuperarArrayAdapter(tiposAnimais);
        progresso.setVisibility(View.INVISIBLE);
    }

    //ITENS DE MENU
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
                gerarXLS();
                break;
            case R.id.action_procurar:
                Toast.makeText(getActivity(),"não há link com o firebase",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //METODO ALERTA DE MENSSAGENS
    private void Alert(String msg){
        Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }


    public void gerarXLS(){
        new GeradorXls("TipoAnimal", view.getContext());
        }
}