package com.example.travelpetadm.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.Encriptador;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_avaliacao,
                R.id.nav_donoAnimal,
                R.id.nav_motorista,
                R.id.nav_viagem,
                R.id.nav_veiculos,
                R.id.nav_animais,
                R.id.nav_especies,
                R.id.nav_contasadm,
                R.id.nav_about,
                R.id.nav_sair)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        iniciarComponentes(navigationView);
    }

public void iniciarComponentes(NavigationView navigationView){
    View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        nomeAdm = view.findViewById(R.id.nomePerfilAdm);
        emailAdm = view.findViewById(R.id.emailPerfilAdm);
        recuperarNomeEmailAdm();
}

    @Override
    protected void onStart() {
        super.onStart();
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

    public void recuperarNomeEmailAdm(){
        nomeAdm.setText("Olá! "+nome);
        emailAdm.setText(email);
        DatabaseReference donoAnimalRef = Conexao.getFirebaseDatabase()
                .child( Conexao.adm)
                .child(Encriptador.codificarBase64(Conexao.getEmailUsuario()));
        donoAnimalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DonoAnimal donoAnimal = dataSnapshot.getValue(DonoAnimal.class);
                nome       =   donoAnimal.getNome();
                email       =   donoAnimal.getEmail();

                nomeAdm.setText("Olá! "+nome);
                emailAdm.setText(email);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
