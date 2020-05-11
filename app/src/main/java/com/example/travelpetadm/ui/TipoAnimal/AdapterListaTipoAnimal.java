package com.example.travelpetadm.ui.TipoAnimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;

import java.util.List;

public class AdapterListaTipoAnimal extends RecyclerView.Adapter<AdapterListaTipoAnimal.MyViewHolder> {

        List<TipoAnimal> tipoAnimal;
        Context context;

public AdapterListaTipoAnimal(List<TipoAnimal> tipoAnimal, Context context) {
        this.tipoAnimal = tipoAnimal;
        this.context = context;
        }

@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_tipoanimal, parent, false);
        return new MyViewHolder(itemLista);
        }


@Override
public void onBindViewHolder(MyViewHolder holder, int position) {
    TipoAnimal tipoAnimal1 = tipoAnimal.get(position);
    //tipoAnimal1 - objeto tipo animal, nao array
    holder.especie.setText(tipoAnimal1.getEspecie());
    holder.raca.setText(String.valueOf(tipoAnimal1.getNomeRacaAnimal()));
    holder.descricao.setText(tipoAnimal1.getDescricao());
}

@Override
public int getItemCount() {
        return tipoAnimal.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView especie, raca, descricao;

    public MyViewHolder(View itemView) {
        super(itemView);

        especie = itemView.findViewById(R.id.textEspecieList);
        raca = itemView.findViewById(R.id.textRacaList);
        descricao = itemView.findViewById(R.id.textDescricaoList);
    }

}
}
