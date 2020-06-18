package com.example.travelpetadm.ui.Motorista;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMotorista extends RecyclerView.Adapter<AdapterMotorista.MyViewHolder> {

    private List<Motorista> motoristas;
    private Context context;

    public AdapterMotorista(List motoristas, Context context){
        this.motoristas = motoristas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_motorista, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Motorista motorista = motoristas.get(position);
        holder.nome.setText(motorista.getNome());
        holder.sobrenome.setText(motorista.getSobrenome());
        holder.status.setText(motorista.getStatusCadastro());
        if (motorista.getFotoPerfilUrl()!=null) {
            Uri fotoPerfilUri = Uri.parse(motorista.getFotoPerfilUrl());
            Glide.with(context).load(fotoPerfilUri).into(holder.imageListaMO);
        } else {
            holder.imageListaMO.setImageResource(R.drawable.ic_especie_spinner);
        }
    }

    @Override
    public int getItemCount() {return motoristas.size();  }

    public static class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView nome, sobrenome, status;
        CircleImageView imageListaMO;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNomeListaMO);
            sobrenome = itemView.findViewById(R.id.textSobrenomeListaMO);
            status = itemView.findViewById(R.id.textStatusListaMO);
            imageListaMO = itemView.findViewById(R.id.imageListaMO);
        }
    }
}
