package com.example.travelpetadm.ui.veiculos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.animais.AdapterListaAnimais;

import java.util.List;

public class AdapterListaVeiculos extends RecyclerView.Adapter<AdapterListaVeiculos.MyViewHolder> {
    private List<Veiculo> veiculos;
    private Context context;

    public AdapterListaVeiculos(List<Veiculo> veiculos, Context context) {
        this.veiculos = veiculos;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterListaVeiculos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_veiculo, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListaVeiculos.MyViewHolder holder, int position) {
        Veiculo veiculo = veiculos.get(position);
        holder.marca.setText(veiculo.getMarcaVeiculo());
        holder.modelo.setText(veiculo.getModeloVeiculo());
        holder.placa.setText(veiculo.getPlacaVeiculo());
        holder.status.setText(veiculo.getStatus());
    }

    @Override
    public int getItemCount() {return veiculos.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView marca, modelo, placa, status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            marca = itemView.findViewById(R.id.textListaVeiculosMarca);
            modelo = itemView.findViewById(R.id.textListaVeiculosModelo);
            placa = itemView.findViewById(R.id.textListaVeiculosPlaca);
            status = itemView.findViewById(R.id.textListaVeiculosStatus);
        }
    }
}
