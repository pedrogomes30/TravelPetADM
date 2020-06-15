package com.example.travelpetadm.DAO;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.Endereco;
import com.example.travelpetadm.ui.donoanimal.InfoDonoAnimalActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.security.auth.callback.Callback;

public class EnderecoDAO {
    private static DatabaseReference refEndereco ;
    Endereco enderecoDao ;


    public static DatabaseReference getEnderecoDAref(){
        if(refEndereco==null){
            refEndereco = Conexao.getFirebaseDatabase().child(Conexao.enderecoDA);
        }
        return refEndereco;
    }

    public Endereco recuperarEndereco(final String usuario){
        Thread recuperarEndereco = new Thread(new Runnable() {
            CountDownLatch tempo = new CountDownLatch(1);
            @Override
            public void run() {
                enderecoDao =  new Endereco();
                refEndereco = Conexao.getFirebaseDatabase().child(Conexao.enderecoDA).child(usuario);
                try {
                    tempo.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refEndereco.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        enderecoDao = dataSnapshot.getValue(Endereco.class);
                        System.out.println(enderecoDao.getBairro() + enderecoDao.getLocalidade()+ " ***********************************DENTRO*******************************");
                        tempo.countDown();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        tempo.countDown();
                    }
                });

            }});
        recuperarEndereco.start();
        //System.out.println(enderecoDao.getBairro() + enderecoDao.getLocalidade() + " *************************************FORA*****************************");

        return enderecoDao;
    }
}
