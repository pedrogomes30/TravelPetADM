package com.example.travelpetadm.ui.contasAdm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.TipoAnimal.AdicionarTipoAnimalActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContasAdmFragment extends Fragment {

    public ContasAdmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contas_adm, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    //botão menu
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
                Toast.makeText(getActivity(),"EM IMPLEMENTAÇÃO",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_adicionar:
                startActivity(new Intent(getActivity(), AdicionarAdmActivity.class));
                break;
            case R.id.action_procurar:
                Toast.makeText(getActivity(),"EM IMPLEMENTAÇÃO",Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
