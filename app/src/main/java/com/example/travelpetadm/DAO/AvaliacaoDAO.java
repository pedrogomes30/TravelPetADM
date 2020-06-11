package com.example.travelpetadm.DAO;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.Avaliacao;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.ui.Avaliacao.AvaliacaoFragment;
import com.example.travelpetadm.ui.TipoAnimal.TipoAnimalFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvaliacaoDAO {
    private static DatabaseReference refAvaliacao;
    private static ValueEventListener listener;
    private static ArrayList<Avaliacao> avaliacoes =new ArrayList<>();

    //Recupera um arraylist desse objeto com a classe adapter
    public static ArrayList<Avaliacao> recuperarArrayAdapter(ArrayList<Avaliacao> avaliacaos){
        avaliacoes = avaliacaos;
        getRefAvaliacao();
        listener = refAvaliacao.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avaliacoes.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                                Avaliacao avaliacao = dados.getValue(Avaliacao.class);
                            avaliacoes.add(dados.getValue(Avaliacao.class));
                }
                refAvaliacao.removeEventListener(listener);
                AvaliacaoFragment.adapterListaAvaliacao.notifyDataSetChanged();
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return avaliacoes;
    }

    // referencia tipo animal
    public static DatabaseReference getRefAvaliacao(){
        if(refAvaliacao==null){
            refAvaliacao = Conexao.getFirebaseDatabase().child(Conexao.avaliacao);
        }
        return refAvaliacao;
    }
}
