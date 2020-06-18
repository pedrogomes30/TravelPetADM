package com.example.travelpetadm.ui.TipoAnimal;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.DAO.TipoAnimalDAO;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.animais.InfoAnimalActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterListaTipoAnimal extends RecyclerView.Adapter<AdapterListaTipoAnimal.MyViewHolder> {

        private List<TipoAnimal> tiposAnimais;
        private Context context;

public AdapterListaTipoAnimal(List<TipoAnimal> tiposAnimais, Context context) {
        this.tiposAnimais = tiposAnimais;
        this.context = context;
        }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_tipoanimal, parent, false);
        return new MyViewHolder(itemLista);
        }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
    final TipoAnimal tipoAnimal = tiposAnimais.get(position);
    holder.especie.setText(tipoAnimal.getEspecie());
    holder.raca.setText(String.valueOf(tipoAnimal.getNomeRacaAnimal()));
        DatabaseReference ref = TipoAnimalDAO.getTipoAnimalReference().child(tipoAnimal.getEspecie());
        ValueEventListener listener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TipoAnimal tipoAnimalI = dataSnapshot.getValue(TipoAnimal.class);
                if (tipoAnimalI.getIconeUrl()!=null) {
                    Uri fotoPerfilUri = Uri.parse(tipoAnimalI.getIconeUrl());
                    Glide.with(context).load(fotoPerfilUri).into(holder.imageEspecie);
                }else {
                    holder.imageEspecie.setImageResource(R.drawable.ic_especie_spinner);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
}

    @Override
    public int getItemCount() {
        return tiposAnimais.size();
        }

    public void notifyDataSetChanged(ArrayList<TipoAnimal> tiposAnimais) {}

    public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView especie, raca;
    ImageView imageEspecie;

    public MyViewHolder(View itemView) {
        super(itemView);
        imageEspecie = itemView.findViewById(R.id.imageEspecie);
        especie = itemView.findViewById(R.id.textEspecieList);
        raca = itemView.findViewById(R.id.textRacaList);
    }

}
}
