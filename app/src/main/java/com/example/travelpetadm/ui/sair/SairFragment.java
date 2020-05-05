package com.example.travelpetadm.ui.sair;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SairFragment extends Fragment {

    public SairFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Conexao.logOut();
        Toast.makeText(getActivity(),"LogOut",Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_sair, container, false);


        return view;
    }
}
