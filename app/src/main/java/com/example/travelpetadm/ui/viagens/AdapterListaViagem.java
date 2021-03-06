package com.example.travelpetadm.ui.viagens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.Model.Viagem;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.Encriptador;
import com.example.travelpetadm.ui.veiculos.AdapterListaVeiculos;

import java.util.List;

public class AdapterListaViagem extends RecyclerView.Adapter <AdapterListaViagem.MyViewHolder> {
    private List<Viagem> viagens;
    private Context context;

    public AdapterListaViagem (List<Viagem>viagens, Context context){
        this.viagens = viagens;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterListaViagem.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_viagem, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Viagem viagem = viagens.get(position);
        holder.textListViagemID.setText(viagem.getIDViagem());
        holder.textListViagemData.setText(viagem.getData());
        if(viagem.getIdDonoAnimal()!=null){
            holder.textListViagemDA.setText("Dono Animal: " + Encriptador.decodificarBase64(viagem.getIdDonoAnimal()));}
        if(!viagem.getIdDonoAnimal().equals(null)){
            holder.textListViagemMO.setText(viagem.getIDMotorista());}
    }

    @Override
    public int getItemCount() {return viagens.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textListViagemID, textListViagemData,textListViagemDA, textListViagemMO;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textListViagemID = itemView.findViewById(R.id.textListViagemID);
            textListViagemData = itemView.findViewById(R.id.textListViagemData);
            textListViagemDA = itemView.findViewById(R.id.textListViagemDA);
            textListViagemMO = itemView.findViewById(R.id.textListViagemMO);
        }
    }
}
