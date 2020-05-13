package com.example.travelpetadm.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView nomeAdm,emailAdm;
    private String nome, email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iniciarComponentes();
        //recuperarAdm();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_estatisticas,
                R.id.nav_donoAnimal,
                R.id.nav_motorista,
                R.id.nav_viagem,
                R.id.nav_veiculos,
                R.id.nav_animais,
                R.id.nav_especies,
                R.id.nav_contasadm,
                R.id.nav_configuracao,
                R.id.nav_log,
                R.id.nav_about,
                R.id.nav_sair)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void recuperarAdm(){
        FirebaseUser adm = Adm.getAdmAtual();
        nomeAdm.setText(adm.getDisplayName());



    }
public void iniciarComponentes(){
        nomeAdm = findViewById(R.id.nomePerfilAdm);
        emailAdm = findViewById(R.id.emailPerfilAdm);

}

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
    }


}
