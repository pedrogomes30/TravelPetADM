package com.example.travelpetadm.ui.donoanimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.R;
import com.google.android.gms.common.api.internal.IStatusCallback;


import java.util.List;

public class AdapterDonoAnimal extends RecyclerView.Adapter<AdapterDonoAnimal.MyViewHolder> {

    private List<DonoAnimal> donosAnimais;
    private Context context;

    public AdapterDonoAnimal(List donosAnimal, Context context){
        this.donosAnimais = donosAnimal;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterDonoAnimal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_donoanimal, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonoAnimal donoAnimal = donosAnimais.get(position);
        holder.nome.setText(donoAnimal.getNome());
        holder.sobrenome.setText(donoAnimal.getSobrenome());
        holder.status.setText(donoAnimal.getStatus());
    }

    @Override
    public int getItemCount() {
        return donosAnimais.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, sobrenome, status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNomeListaDA);
            sobrenome = itemView.findViewById(R.id.textSobrenomeListaDA);
            status = itemView.findViewById(R.id.textCidadeListaDA);
        }
    }
}
