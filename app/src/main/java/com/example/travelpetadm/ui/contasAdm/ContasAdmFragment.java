package com.example.travelpetadm.ui.contasAdm;

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

import com.example.travelpetadm.DAO.AdmDAO;
import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.R;
import com.example.travelpetadm.DAO.GeradorXls;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContasAdmFragment extends Fragment {
    public static AdapterListaAdm adapterListaAdm;
    private RecyclerView recyclerView;
    private ArrayList<Adm> adms =new ArrayList<>() ;
    private DatabaseReference admRef;
    private ValueEventListener valueEventListenerAdm;
    private ProgressBar progresso;
    private FloatingActionButton fabAdicionarAdm;
    View view;

    public ContasAdmFragment() {}

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_contas_adm, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        recuperarAdm();
        adicionarAdm();
        return view;
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listaAdm);
        admRef  = Conexao.getFirebaseDatabase().child("adm");
        progresso = view.findViewById(R.id.progressAdm);
        fabAdicionarAdm = view.findViewById(R.id.fabAdicionarAdm);
    }

    public void recuperarAdm (){
        AdmDAO.recuperarArray(adms);
        progresso.setVisibility(View.GONE);
    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
    adapterListaAdm = new AdapterListaAdm(adms, getActivity());

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
                               Adm admSel =  adms.get(position);
                                Intent i =  new Intent(getActivity(), InfoAdmActivity.class);
                                i.putExtra("ExibirAdm",admSel);
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

    //BOTÔES DE MENU
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        }
    public void adicionarAdm(){
        fabAdicionarAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AdicionarAdmActivity.class));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //necessário ou o botão e selecionado em qualquer ação
        switch(item.getItemId()){
            case R.id.action_salvar:
                new GeradorXls("Adm", view.getContext());
                break;
            case R.id.action_procurar:
                Toast.makeText(getActivity(),"EM IMPLEMENTAÇÃO",Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
