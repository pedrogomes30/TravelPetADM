package com.example.travelpetadm.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.ui.TipoAnimal.TipoAnimalFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class TipoAnimalDAO extends Conexao {
    private static DatabaseReference refTipoAnimal ;
    private static ValueEventListener listener;
    private static ArrayList<TipoAnimal> tipoAnimais =new ArrayList<>();
    private static StorageReference storageImgRef = Conexao.getFirebaseStorage().child(storageTipoAnimal);
    private Context context;
    private static String fotoAnimalUrl;


    // referencia tipo animal
    public static DatabaseReference getTipoAnimalReference(){
        if(refTipoAnimal==null){
            refTipoAnimal = Conexao.getFirebaseDatabase().child(tipoAnimal);
        }
        return refTipoAnimal;
    }

    //salvar Tipo Animal
    public static void salvarTipoAnimal(TipoAnimal tipoAnimal){
        refTipoAnimal = getTipoAnimalReference();
        refTipoAnimal
                .child(tipoAnimal.getEspecie())
                .child(tipoAnimal.getNomeRacaAnimal())
                .setValue(tipoAnimal);
    }

    public static void salvarUrlTipoAnimal(TipoAnimal tipoAnimal){
        refTipoAnimal = getTipoAnimalReference();
        refTipoAnimal.child(tipoAnimal.getEspecie())
                .child("iconeUrl")
                .setValue(tipoAnimal.getIconeEspecieUrl());
    }

    public static String salvarFotoTipoAnimal(String especie, Bitmap imagem){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG,70,baos);
        byte[] dadosImagem = baos.toByteArray();
        UploadTask uploadTask = storageImgRef
                .child(especie)
                .child(especie + ".JPEG")
                .putBytes(dadosImagem);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               System.out.println("Falha ao Salvar imagem ----------------------------------------------");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Sucesso ao salvar a imagem -----------------------------------------");
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url = uri.getResult();
                fotoAnimalUrl = url.toString();
            }
        });
        return fotoAnimalUrl;
    }

    //Recupera um arraylist desse objeto com a classe adapter
    public static ArrayList<TipoAnimal> recuperarArrayAdapter(ArrayList<TipoAnimal> tiposAnimais){
         tipoAnimais = tiposAnimais;
        getTipoAnimalReference();
         listener = refTipoAnimal.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tipoAnimais.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for (DataSnapshot especie : dados.getChildren()) {
                        if (!especie.getKey().equals(Conexao.iconeUrl)) {
                            TipoAnimal tipoAnimal = especie.getValue(TipoAnimal.class);
                            tipoAnimais.add(especie.getValue(TipoAnimal.class));
                        }
                    }
                }
                refTipoAnimal.removeEventListener(listener);
                TipoAnimalFragment.adapterListaTipoAnimal.notifyDataSetChanged();
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return tipoAnimais;
    }

    //Recupera um arraylist desse objeto sem a classe adapter
    public static ArrayList<TipoAnimal> recuperarArray(ArrayList<TipoAnimal> tiposAnimais){
        tipoAnimais = tiposAnimais;
        getTipoAnimalReference();
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
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return tipoAnimais;
    }

}
