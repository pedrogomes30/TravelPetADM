package com.example.travelpetadm.ui.veiculos;

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
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.GeradorXls;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.animais.AdapterListaAnimais;
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

public class VeiculosFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListaVeiculos adapterListaVeiculo;
    private ArrayList<Veiculo> veiculos =new ArrayList<>() ;
    private DatabaseReference veiculoRef;
    private ValueEventListener valueEventListenerVeiculo;
    private ProgressBar progressoVeiculo;
    View view;

    public VeiculosFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_veiculos, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        recuperarVeiculo();
    }

    @Override
    public void onStop(){
        super.onStop();
        veiculoRef.removeEventListener(valueEventListenerVeiculo);
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listVeiculo);
        veiculoRef  = Conexao.getFirebaseDatabase().child("veiculos");
        progressoVeiculo = view.findViewById(R.id.progressoVeiculo);
    }
    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaVeiculo = new AdapterListaVeiculos(veiculos, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaVeiculo);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Veiculo veiculoSel =  veiculos.get(position);
                                Intent i =  new Intent(getActivity(),InfoVeiculosActivity.class);
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
    public void recuperarVeiculo (){
        valueEventListenerVeiculo = veiculoRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                veiculos.clear();
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE AVES

                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for (DataSnapshot veiculolDs : dados.getChildren()) {
                        Veiculo veiculo = veiculolDs.getValue(Veiculo.class);
                        veiculos.add(veiculo);
                        progressoVeiculo.setVisibility(View.VISIBLE);
                    }
                }
                adapterListaVeiculo.notifyDataSetChanged();
                progressoVeiculo.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

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
                new GeradorXls("Veiculo", view.getContext());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
