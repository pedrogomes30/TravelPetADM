package com.example.travelpetadm.DAO;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.helper.Encriptador;
import com.example.travelpetadm.ui.contasAdm.ContasAdmFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

    public class AdmDAO {
    private Adm adm;
    private static String idAdm;
    private static DatabaseReference refAdm ;
    private static ValueEventListener listener;
    private static ArrayList<Adm> adms =new ArrayList<>();

    public static void  salvar(){
        DatabaseReference adm = referenciaAdm().child(getIdAdm());
        adm.setValue(adm);
    }

    public static  String getIdentificadorAdm (){
        FirebaseAuth adm = Conexao.getFirebaseAuth();
        String email = adm.getCurrentUser().getEmail();
        String identificadorAdm = Encriptador.codificarBase64(email);
        return identificadorAdm;
    }

    public static String getIdAdm() {
        idAdm =  getIdentificadorAdm();
        return idAdm;
    }

    public static DatabaseReference referenciaAdm(){
        DatabaseReference ref = Conexao.getFirebaseDatabase().child(Conexao.adm);
        return ref;
    }

    public static ArrayList<Adm> recuperarArray(ArrayList<Adm> admS){
        adms = admS;
        refAdm = referenciaAdm();
        listener = refAdm.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adms.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                            Adm adm = dados.getValue(Adm.class);
                            adms.add(dados.getValue(Adm.class));
                }
                refAdm.removeEventListener(listener);
                ContasAdmFragment.adapterListaAdm.notifyDataSetChanged();
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return adms;
    }
}
