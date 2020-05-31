package com.example.travelpetadm.ui.estatisticas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.travelpetadm.R;

public class EstatisticasFragment extends Fragment {
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_estatisticas, container, false);

             webView = (WebView)view.findViewById(R.id.webEstatisticas);
             webView.getSettings().setJavaScriptEnabled(true);
             webView.setWebViewClient(new WebViewClient());
             webView.loadUrl("https://docs.google.com/spreadsheets/d/1W2sHWU6bMAKE7LGgSMlyN-dZ1OK9x3om5SHz2Ub5tdI/edit?usp=sharing");

             return view;
    }

}
