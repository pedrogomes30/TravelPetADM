package com.example.travelpetadm.ui.Motorista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.R;

import java.util.List;

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
        holder.status.setText(motorista.getStatus());
    }

    @Override
    public int getItemCount() {return motoristas.size();  }

    public static class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView nome, sobrenome, status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNomeListaMO);
            sobrenome = itemView.findViewById(R.id.textSobrenomeListaMO);
            status = itemView.findViewById(R.id.textCidadeListaMO);
        }
    }
}
