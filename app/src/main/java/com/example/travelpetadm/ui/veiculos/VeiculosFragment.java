package com.example.travelpetadm.ui.veiculos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelpetadm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VeiculosFragment extends Fragment {

    public VeiculosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_veiculos, container, false);
    }
}
