package com.example.travelpetadm.DAO;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.TipoAnimal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TipoAnimalDAO extends Conexao {
    private static DatabaseReference refTipoAnimal;
    private static ValueEventListener listener;
    private static ArrayList<TipoAnimal> tipoAnimais =new ArrayList<>() ;


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



}
