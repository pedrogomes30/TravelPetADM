package com.example.travelpetadm.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.Avaliacao;
import com.example.travelpetadm.ui.Avaliacao.AvaliacaoFragment;
import com.example.travelpetadm.ui.Motorista.AvaliacaoMotoristaActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AvaliacaoDAO {
    private static DatabaseReference refAvaliacao;
    private static ValueEventListener listener;
    private static ArrayList<Avaliacao> avaliacoes =new ArrayList<>();

    //Recupera um arraylist desse objeto com a classe adapter
    public static ArrayList<Avaliacao> recuperarArrayAdapter(ArrayList<Avaliacao> avaliacaos){
        avaliacoes = avaliacaos;
        refAvaliacao = getRefAvaliacao();
        listener = refAvaliacao.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avaliacoes.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for(DataSnapshot dados2 : dados.getChildren()) {
                        avaliacoes.add(dados2.getValue(Avaliacao.class));
                    }
                }
                refAvaliacao.removeEventListener(listener);
                AvaliacaoFragment.adapterListaAvaliacao.notifyDataSetChanged();
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());}
        });
        return avaliacoes;
    }

    public static ArrayList<Avaliacao> recuperarAvaliacaoPerfil(ArrayList<Avaliacao> avaliacaos, String idUsuario){
        avaliacoes = avaliacaos;
        refAvaliacao = getRefAvaliacao().child(idUsuario);
        listener = refAvaliacao.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avaliacoes.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                        avaliacoes.add(dados.getValue(Avaliacao.class));
                }
                refAvaliacao.removeEventListener(listener);
                AvaliacaoMotoristaActivity.adapterListaAvaliacao.notifyDataSetChanged();
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());}
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
