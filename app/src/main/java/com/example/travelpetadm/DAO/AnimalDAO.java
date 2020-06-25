package com.example.travelpetadm.DAO;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.helper.Encriptador;
import com.example.travelpetadm.ui.animais.AnimaisFragment;
import com.example.travelpetadm.ui.donoanimal.InfoDonoAnimalActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnimalDAO extends Conexao {

    private static DatabaseReference animalRef;
    private static ArrayList<Animal> animais =new ArrayList<>() ;
    private static ValueEventListener valueEventListenerAnimal;

    public static DatabaseReference getAnimalReference(){
        if(animalRef==null){
            animalRef = FirebaseDatabase.getInstance().getReference().child(animal);
        }
        return animalRef;
    }

    public static ArrayList<Animal> recuperarArrayAnimaisDonoAnimal(String idDonoAnimal, ArrayList<Animal> animals){
        animais = animals;
        animalRef = getAnimalReference().child(idDonoAnimal);
        valueEventListenerAnimal = animalRef
                .addValueEventListener(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot animalDs: dataSnapshot.getChildren()) {
                            Animal animal = animalDs.getValue(Animal.class);
                            animais.add(animal);
                        }
                        animalRef.removeEventListener(valueEventListenerAnimal);
                        InfoDonoAnimalActivity.adapterListaAnimal.notifyDataSetChanged();
                    }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        return animais;
    }

    public static ArrayList<Animal> recuperarArrayAnimais(ArrayList<Animal> animals){
        animais = animals;
        animalRef = getAnimalReference();
        valueEventListenerAnimal = animalRef.addValueEventListener(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dados : dataSnapshot.getChildren()) {
                            for (DataSnapshot animalDs : dados.getChildren()) {
                                Animal animal = animalDs.getValue(Animal.class);
                                animais.add(animal);
                            }
                        }
                        animalRef.removeEventListener(valueEventListenerAnimal);
                        AnimaisFragment.adapterListaAnimal.notifyDataSetChanged();
                    }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        return animais;
    }
}
