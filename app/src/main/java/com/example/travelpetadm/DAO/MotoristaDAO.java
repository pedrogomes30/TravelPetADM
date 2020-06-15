package com.example.travelpetadm.DAO;

import com.google.firebase.database.DatabaseReference;
public class MotoristaDAO extends Conexao {

    private static DatabaseReference refMotorista;
    public static DatabaseReference getmotoritaReference() {
        if (refMotorista == null) {
            refMotorista = Conexao.getFirebaseDatabase().child(Conexao.motorista);
        }
        return refMotorista;
    }
}

