package com.example.travelpetadm.DAO;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.Endereco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

import javax.security.auth.callback.Callback;

public class EnderecoDAO {
    private static DatabaseReference refEndereco ;


    public static DatabaseReference getEnderecoDAref(){
        if(refEndereco==null){
            refEndereco = Conexao.getFirebaseDatabase().child(Conexao.enderecoDA);
        }
        return refEndereco;
    }

    public static void recuperarEndereco(){
        refEndereco = getEnderecoDAref();
        refEndereco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   Endereco endereco=dataSnapshot.getValue(Endereco.class);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
