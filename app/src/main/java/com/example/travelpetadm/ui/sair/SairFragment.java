package com.example.travelpetadm.ui.sair;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.Login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SairFragment extends Fragment {
   private ImageView porquinho;
   private Conexao auth;
    public SairFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sair, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        porcoSair();
    }

    public void porcoSair(){
        porquinho = getView().findViewById(R.id.porcoSair);
        porquinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.logOut();
                Intent intent = new Intent(getActivity(),LoginActivity.class );
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
