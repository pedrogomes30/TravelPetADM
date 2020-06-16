package com.example.travelpetadm.DAO;

import com.example.travelpetadm.Model.Endereco;
import com.google.firebase.database.DatabaseReference;

public class VeiculoDAO extends Conexao {
    private static DatabaseReference refVeiculo ;

    public static DatabaseReference getVeiculoRef(){
        if(refVeiculo==null){
            refVeiculo = Conexao.getFirebaseDatabase().child(veiculo);
        }
        return refVeiculo;
    }
}
