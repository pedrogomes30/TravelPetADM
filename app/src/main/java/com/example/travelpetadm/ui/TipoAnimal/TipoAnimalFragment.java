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

/**
 * A simple {@link Fragment} subclass.
 */
public class TipoAnimalFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListaTipoAnimal adapterListaAdm;
    private ArrayList<TipoAnimal> tiposAnimais =new ArrayList<>() ;
    private DatabaseReference tipoAnimalRef;
    private ValueEventListener valueEventListenerListaTipoAnimal;
    private ProgressBar progresso;


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
        tipoAnimalRef  = Conexao.getFirebaseDatabase().child("racaAnimal");
        progresso = view.findViewById(R.id.progresso);
    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaAdm = new AdapterListaTipoAnimal(tiposAnimais, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaAdm);

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
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE AVES
                DataSnapshot ave = dataSnapshot.child("ave");
                for(DataSnapshot dados: ave.getChildren()){
                   TipoAnimal tipoAnimal = dados.getValue(TipoAnimal.class);
                    tiposAnimais.add(tipoAnimal);
                    progresso.setVisibility(View.VISIBLE);
                }
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE CACHORROS
                DataSnapshot cachorro = dataSnapshot.child("cachorro");
                for(DataSnapshot dados: cachorro.getChildren()){
                    TipoAnimal tipoAnimal = dados.getValue(TipoAnimal.class);
                    tiposAnimais.add(tipoAnimal);
                    progresso.setVisibility(View.VISIBLE);
                }
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE GATOS
                DataSnapshot gato = dataSnapshot.child("gato");
                for(DataSnapshot dados: gato.getChildren()){
                    TipoAnimal tipoAnimal = dados.getValue(TipoAnimal.class);
                    tiposAnimais.add(tipoAnimal);
                    progresso.setVisibility(View.VISIBLE);
                }
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE REPTIL
                DataSnapshot reptil = dataSnapshot.child("reptil");
                for(DataSnapshot dados: reptil.getChildren()){
                    TipoAnimal tipoAnimal = dados.getValue(TipoAnimal.class);
                    tiposAnimais.add(tipoAnimal);
                    progresso.setVisibility(View.VISIBLE);
                }
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE ROEDOR
                DataSnapshot roedor = dataSnapshot.child("roedor");
                for(DataSnapshot dados: roedor.getChildren()){
                    TipoAnimal tipoAnimal = dados.getValue(TipoAnimal.class);
                    tiposAnimais.add(tipoAnimal);
                    progresso.setVisibility(View.VISIBLE);
                }

                adapterListaAdm.notifyDataSetChanged();
                progresso.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
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
