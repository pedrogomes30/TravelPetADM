package com.example.travelpetadm.ui.viagens;

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
import com.example.travelpetadm.Model.Viagem;
import com.example.travelpetadm.R;
import com.example.travelpetadm.DAO.GeradorXls;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViagemFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListaViagem adapterListaViagem;
    private ArrayList<Viagem> viagens =new ArrayList<>() ;
    private DatabaseReference viagemRef;
    private ValueEventListener valueEventListenerViagem;
    private ProgressBar progressoViagem;
    View view;

    public ViagemFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_viagem, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarViagem();
    }

    @Override
    public void onStop(){
        super.onStop();
        viagemRef.removeEventListener(valueEventListenerViagem);
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listViagem);
        viagemRef  = Conexao.getFirebaseDatabase().child(Conexao.viagem);
        progressoViagem = view.findViewById(R.id.progressoViagem);
    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaViagem = new AdapterListaViagem(viagens, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaViagem);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Viagem viagem = viagens.get(position);
                                Intent i =  new Intent(getActivity(), InfoViagensActivity.class);
                                i.putExtra("ExibirViagem",viagem);
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

    public void recuperarViagem () {

        valueEventListenerViagem = viagemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viagens.clear();
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE AVES

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Viagem viagem = dados.getValue(Viagem.class);
                        viagens.add(viagem);
                        progressoViagem.setVisibility(View.VISIBLE);
                }
                adapterListaViagem.notifyDataSetChanged();
                progressoViagem.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
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
                new GeradorXls("Viagem",view.getContext());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void alerta(String string) {
        Toast.makeText(getActivity().getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}
