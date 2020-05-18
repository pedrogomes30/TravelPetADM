package com.example.travelpetadm.ui.viagens;

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


public class ViagemFragment extends Fragment {

    public ViagemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viagem, container, false);
        setHasOptionsMenu(true);
        return view;
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
                Toast.makeText(getActivity(),"não há link com o firebase",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
