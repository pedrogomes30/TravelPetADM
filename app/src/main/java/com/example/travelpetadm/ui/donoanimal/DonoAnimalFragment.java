package com.example.travelpetadm.ui.donoanimal;

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
import com.example.travelpetadm.DAO.DonoAnimalDAO;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.DAO.GeradorXls;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonoAnimalFragment extends Fragment {
    public static AdapterDonoAnimal adapterListaDonoAnimal;
    private RecyclerView recyclerView;
    private ArrayList<DonoAnimal> donosAnimais =new ArrayList<>() ;
    private DatabaseReference donoAnimalRef;
    private ValueEventListener valueEventListenerDonoAnimal;
    private ProgressBar progresso;
    View view;
    public DonoAnimalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donoanimal, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarDonoAnimal();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listaDonoAnimal);
        donoAnimalRef  = Conexao.getFirebaseDatabase().child("donoAnimal");
        progresso = view.findViewById(R.id.progressDA);
    }

    public void recuperarDonoAnimal (){
        donosAnimais = DonoAnimalDAO.recuperarArray(donosAnimais);
        progresso.setVisibility(View.GONE);
    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaDonoAnimal = new AdapterDonoAnimal(donosAnimais, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaDonoAnimal);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                DonoAnimal donoAnimalSel =  donosAnimais.get(position);
                                Intent i =  new Intent(getActivity(), InfoDonoAnimalActivity.class);
                                i.putExtra("ExibirDonoAnimal",donoAnimalSel);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //necessário ou o botão e selecionado em qualquer ação
        switch (item.getItemId()) {
            case R.id.action_salvar:
                new GeradorXls("DonoAnimal", view.getContext());
                break;
            case R.id.action_procurar:
                Toast.makeText(getActivity(), "não há link com o firebase", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
