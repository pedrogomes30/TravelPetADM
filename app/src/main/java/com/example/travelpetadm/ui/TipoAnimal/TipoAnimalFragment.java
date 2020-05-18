package com.example.travelpetadm.ui.TipoAnimal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TipoAnimalFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListaTipoAnimal adapterListaTipoAnimal;
    private ArrayList<TipoAnimal> tiposAnimais =new ArrayList<>() ;
    private DatabaseReference tipoAnimalRef;
    private ValueEventListener valueEventListenerListaTipoAnimal;
    private ProgressBar progresso;

    public  TipoAnimalFragment() {    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipo_animal, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
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
        recyclerView = view.findViewById(R.id.listaTiposAnimais);
        tipoAnimalRef  = Conexao.getFirebaseDatabase().child("racaAnimal");
        progresso = view.findViewById(R.id.progressoTipoAnimal);
    }

    public void iniciarReciclerView(View view) {

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


    public void recuperarTipoAnimal (){
        valueEventListenerListaTipoAnimal = tipoAnimalRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tiposAnimais.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for (DataSnapshot especie : dados.getChildren()) {
                        TipoAnimal tipoAnimal = especie.getValue(TipoAnimal.class);
                        tiposAnimais.add(tipoAnimal);
                        progresso.setVisibility(View.VISIBLE);
                    }
                }


                adapterListaTipoAnimal.notifyDataSetChanged();
                progresso.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    //ITENS DE MENU
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
    //METODO ALERTA DE MENSSAGENS
    private void Alert(String msg){
        Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

    }

}
