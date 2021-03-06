package com.example.travelpetadm.ui.Avaliacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.DAO.AvaliacaoDAO;
import com.example.travelpetadm.Model.Avaliacao;
import com.example.travelpetadm.R;
import com.example.travelpetadm.DAO.GeradorXls;
import com.example.travelpetadm.helper.RecyclerItemClickListener;

import java.util.ArrayList;

public class AvaliacaoFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    // é necessário ser publico e estático devido a classe DAO informar os itens salvos na classe Adapter intanciada dentro do fragment
    public static AdapterListaAvaliacao adapterListaAvaliacao;
    private ArrayList<Avaliacao> avaliacoes =new ArrayList<>() ;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_avaliacao, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        setHasOptionsMenu(true);
        iniciarComponentes();
        iniciarReciclerView();
        return root;
    }
    public void iniciarComponentes(){
        recyclerView = root.findViewById(R.id.listaAvaliacao);
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarAvaliacao();
    }

    public void iniciarReciclerView() {
        //configurar Adapter
        adapterListaAvaliacao = new AdapterListaAvaliacao(avaliacoes, root.getContext());
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaAvaliacao);
        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        root.getContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Avaliacao avaliacao =  avaliacoes.get(position);
                                Intent i =  new Intent(root.getContext(), InfoAvaliacaoActivity.class);
                                i.putExtra("informacoes avaliacao",avaliacao);
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

    public void recuperarAvaliacao (){
        avaliacoes= AvaliacaoDAO.recuperarArrayAdapter(avaliacoes);
    }

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
        }
        return super.onOptionsItemSelected(item);
    }

    public void gerarXLS(){
        new GeradorXls("Avaliacao", root.getContext());
    }

}
