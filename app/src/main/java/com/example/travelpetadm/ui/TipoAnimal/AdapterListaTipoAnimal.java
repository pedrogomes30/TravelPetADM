package com.example.travelpetadm.ui.TipoAnimal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;

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
public void onBindViewHolder(MyViewHolder holder, int position) {
    TipoAnimal tipoAnimal = tiposAnimais.get(position);
    holder.especie.setText(tipoAnimal.getEspecie());
    holder.raca.setText(String.valueOf(tipoAnimal.getNomeRacaAnimal()));
    holder.descricao.setText(tipoAnimal.getDescricao());


    Log.i("REFERENCIA CLASSSE   ",holder.especie.toString());

    switch(holder.especie.toString()){
        case "androidx.appcompat.widget.AppCompatTextView{69669d4 V.ED..... ......ID 0,0-0,0 #7f090147 app:id/textEspecieList}":
            holder.imageEspecie.setImageResource(R.drawable.ic_ave_spinner);
            break;
        case "cachorro":
            holder.imageEspecie.setImageResource(R.drawable.ic_cachorro_spinner);
            break;
        case "gato":
            holder.imageEspecie.setImageResource(R.drawable.ic_gato_spinner);
            break;
        case "reptil":
            holder.imageEspecie.setImageResource(R.drawable.ic_reptil_spinner);
            break;
        case "roedor":
            holder.imageEspecie.setImageResource(R.drawable.ic_roedor_spinner);
            break;
        default:
            holder.imageEspecie.setImageResource(R.drawable.ic_especie_spinner);
            break;
    }


}

@Override
public int getItemCount() {
        return tiposAnimais.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView especie, raca, descricao;
    ImageView imageEspecie;

    public MyViewHolder(View itemView) {
        super(itemView);
        imageEspecie = itemView.findViewById(R.id.imageEspecie);
        especie = itemView.findViewById(R.id.textEspecieList);
        raca = itemView.findViewById(R.id.textRacaList);
        descricao = itemView.findViewById(R.id.textDescricaoList);
    }

}
}
