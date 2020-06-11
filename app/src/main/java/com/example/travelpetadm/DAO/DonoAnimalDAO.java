package com.example.travelpetadm.DAO;

import com.google.firebase.database.DatabaseReference;

public class DonoAnimalDAO {
    private static DatabaseReference refDonoAnimal;

    public static DatabaseReference getDonoAnimalReference(){
        if(refDonoAnimal==null){refDonoAnimal = Conexao.getFirebaseDatabase().child(Conexao.donoAnimal);}
        return refDonoAnimal;
    }
}
