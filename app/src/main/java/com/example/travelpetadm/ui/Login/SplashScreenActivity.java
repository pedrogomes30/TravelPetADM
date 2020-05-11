package com.example.travelpetadm.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar=findViewById(R.id.progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                finish();
            }
        },1000);

    }
}
