package com.example.travelpetadm.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.travelpetadm.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mostrarLogin();

    }

    private void mostrarLogin() {
        Intent intent = new Intent(SplashScreenActivity.this, Login.class);
        startActivity(intent);
        finish();

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                mostrarLogin();
            }
        }, 5000);
    }
}
