package com.example.travelpetadm.ui.motorista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.travelpetadm.R;

public class MotoristaFragment extends Fragment {

    private MotoristaViewModel motoristaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        motoristaViewModel =
                ViewModelProviders.of(this).get(MotoristaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_motorista, container, false);
        final TextView textView = root.findViewById(R.id.text_motorista);
        motoristaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        setHasOptionsMenu(true);
        return root;
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
