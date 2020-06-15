package com.example.travelpetadm.DAO;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.ui.donoanimal.DonoAnimalFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;

public class DonoAnimalDAO {
    private static DatabaseReference refDonoAnimal;
    private static ArrayList<DonoAnimal> donosAnimals;
    private static DatabaseReference donoAnimalRef;
    private static ValueEventListener valueEventListenerDonoAnimal;

    public DonoAnimalDAO() {
    }

    public static DatabaseReference getDonoAnimalReference(){
        if(refDonoAnimal==null){refDonoAnimal = Conexao.getFirebaseDatabase().child(Conexao.donoAnimal);}
        return refDonoAnimal;
    }
    public static ArrayList<DonoAnimal> recuperarArray (ArrayList<DonoAnimal> donosAnimais){
        donosAnimals = donosAnimais;
        donoAnimalRef = getDonoAnimalReference();
        valueEventListenerDonoAnimal = donoAnimalRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donosAnimals.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    DonoAnimal donoAnimal = dados.getValue(DonoAnimal.class);
                    donosAnimals.add(donoAnimal);
                }
                donoAnimalRef.removeEventListener(valueEventListenerDonoAnimal);
                DonoAnimalFragment.adapterListaDonoAnimal.notifyDataSetChanged();
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return donosAnimais;
    }

}
