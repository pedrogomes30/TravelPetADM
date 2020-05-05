package com.example.travelpetadm.ui.configuracao;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelpetadm.R;

public class ConfiguracaoFragment extends Fragment {

    private ConfiguracaoViewModel mViewModel;

    public static ConfiguracaoFragment newInstance() {
        return new ConfiguracaoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracao, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConfiguracaoViewModel.class);
        // TODO: Use the ViewModel
    }

}
