package com.example.travelpetadm.DAO;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.ui.TipoAnimal.AdapterListaTipoAnimal;
import com.example.travelpetadm.ui.TipoAnimal.TipoAnimalFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TipoAnimalDAO extends Conexao {
    private static DatabaseReference refTipoAnimal ;
    private static ValueEventListener listener;
    private static ArrayList<TipoAnimal> tipoAnimais =new ArrayList<>();
    private static AdapterListaTipoAnimal adapterListaTipoAnimal;


    // referencia tipo animal
    public static DatabaseReference getTipoAnimalDatabase(){
        if(refTipoAnimal==null){
            refTipoAnimal = Conexao.getFirebaseDatabase().child(Conexao.tipoAnimal);
        }
        return refTipoAnimal;
    }
    //salvar Tipo Animal
    public static void salvarTipoAnimal(TipoAnimal tipoAnimal){
        if(refTipoAnimal==null){
            refTipoAnimal = Conexao.getFirebaseDatabase().child(Conexao.tipoAnimal);
        }
        refTipoAnimal
                .child(tipoAnimal.getEspecie())
                .child(tipoAnimal.getNomeRacaAnimal())
                .setValue(tipoAnimal);
    }

    public static ArrayList<TipoAnimal> recuperarArray(ArrayList<TipoAnimal> tiposAnimais){
         tipoAnimais = tiposAnimais;
        if(refTipoAnimal==null){
            refTipoAnimal = Conexao.getFirebaseDatabase().child(Conexao.tipoAnimal); }
         listener = refTipoAnimal.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tipoAnimais.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for (DataSnapshot especie : dados.getChildren()) {
                        if (!especie.getKey().equals(Conexao.iconeUrl)) {
                            TipoAnimal tipoAnimal = especie.getValue(TipoAnimal.class);
                            tipoAnimais.add(tipoAnimal);
                        }
                    }
                }
                TipoAnimalFragment.adapterListaTipoAnimal.notifyDataSetChanged();
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return tipoAnimais;
    }

}
