package com.example.travelpetadm.ui.motorista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        return root;
    }
}
