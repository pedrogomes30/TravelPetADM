package com.example.travelpetadm.ui.Motorista;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.GeradorXls;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.Motorista.AdapterMotorista;
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

public class MotoristaFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterMotorista adapterListaMotorista;
    private ArrayList<Motorista> motoristas =new ArrayList<>() ;
    private DatabaseReference motoristaRef;
    private ValueEventListener valueEventListenerMotorista;
    private ProgressBar progressoMO;
    View view;

    public MotoristaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_motorista, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        recuperarMotorista();
    }
    @Override
    public void onStop(){
        super.onStop();
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listaMotorista);
        motoristaRef  = Conexao.getFirebaseDatabase().child("motorista");
        progressoMO = view.findViewById(R.id.progressMO);
    }

    public void recuperarMotorista (){
        valueEventListenerMotorista = motoristaRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motoristas.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Motorista motorista = dados.getValue(Motorista.class);
                    motoristas.add(motorista);
                    progressoMO.setVisibility(View.VISIBLE);
                }
                adapterListaMotorista.notifyDataSetChanged();
                progressoMO.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaMotorista = new AdapterMotorista(motoristas, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaMotorista);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                               Motorista motoristaSel =  motoristas.get(position);
                                Intent i =  new Intent(getActivity(), InfoMotoristaActivity.class);
                                i.putExtra("ExibirMotorista",motoristaSel);
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

    private void alert(String MSG){
        Toast.makeText(getActivity(),MSG,Toast.LENGTH_SHORT).show();
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
                new GeradorXls("Motorista",view.getContext());
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
