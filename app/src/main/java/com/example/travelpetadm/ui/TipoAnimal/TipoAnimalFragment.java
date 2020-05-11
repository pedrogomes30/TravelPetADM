package com.example.travelpetadm.ui.TipoAnimal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipoAnimalFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListaTipoAnimal adapterListaTipoAnimal;
    private List<TipoAnimal> tipoAnimal = new ArrayList<>();
    private DatabaseReference tipoAnimalRef  = Conexao.getFirebaseDatabase();
    private ValueEventListener valueEventListenerListaTipoAnimal;


    public TipoAnimalFragment() {
        // Required empty public constructor
    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tipo_animal, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        recuperarTipoAnimal();
        iniciarReciclerView(view);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        recuperarTipoAnimal();
    }

    @Override
    public void onStop(){
        super.onStop();
        tipoAnimalRef.removeEventListener(valueEventListenerListaTipoAnimal);
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listaAnimais);
    }

    public void iniciarReciclerView(View view){
        //configurar Adapter
        adapterListaTipoAnimal = new AdapterListaTipoAnimal(tipoAnimal,getContext());
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaTipoAnimal);
    }

    public void recuperarTipoAnimal (){
        tipoAnimalRef.child("racaAnimal");
        valueEventListenerListaTipoAnimal = tipoAnimalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tipoAnimal.clear();
                for(DataSnapshot dados:dataSnapshot.getChildren()){
                    Log.i("dados","retorno" +dados.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main2, menu);
        }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //necessário ou o botão e selecionado em qualquer ação
        switch(item.getItemId()){
            case R.id.action_salvar:
                Toast.makeText(getActivity(),"não há link com o firebase",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_adicionar:
                startActivity(new Intent(getActivity(), AdicionarTipoAnimalActivity.class));
                break;
            case R.id.action_procurar:
                Toast.makeText(getActivity(),"não há link com o firebase",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Alert(String msg){
        Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
