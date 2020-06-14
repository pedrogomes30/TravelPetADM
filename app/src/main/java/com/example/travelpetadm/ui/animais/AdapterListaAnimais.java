package com.example.travelpetadm.ui.animais;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.Encriptador;
import com.example.travelpetadm.ui.TipoAnimal.AdapterListaTipoAnimal;

import java.util.List;

public class AdapterListaAnimais extends RecyclerView.Adapter<AdapterListaAnimais.MyViewHolder> {

    private List<Animal> animais;
    private Context context;

    public AdapterListaAnimais(List<Animal> animais, Context context) {
        this.animais = animais;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_animal, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Animal animal = animais.get(position);
        holder.especie.setText(animal.getEspecieAnimal());
        holder.raca.setText(animal.getRacaAnimal());
        holder.donoAnimal.setText(Encriptador.decodificarBase64(animal.getIdUsuario()));
        holder.nome.setText(animal.getNomeAnimal());

        if(animal.getEspecieAnimal()!= null) {

            switch (animal.getEspecieAnimal()) {
                case "ave":
                    holder.imageListaAnimais.setImageResource(R.drawable.ic_ave_spinner);
                    break;
                case "cachorro":
                    holder.imageListaAnimais.setImageResource(R.drawable.ic_cachorro_spinner);
                    break;
                case "gato":
                    holder.imageListaAnimais.setImageResource(R.drawable.ic_gato_spinner);
                    break;
                case "reptil":
                    holder.imageListaAnimais.setImageResource(R.drawable.ic_reptil_spinner);
                    break;
                case "roedor":
                    holder.imageListaAnimais.setImageResource(R.drawable.ic_roedor_spinner);
                    break;
                default:
                    holder.imageListaAnimais.setImageResource(R.drawable.ic_especie_spinner);
                    break;
            }
        }


    }

    @Override
    public int getItemCount() {return animais.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView especie, raca, donoAnimal, nome;
        ImageView imageListaAnimais;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageListaAnimais = itemView.findViewById(R.id.imageListaAnimais);
            especie = itemView.findViewById(R.id.textListaAnimaisEspecie);
            raca = itemView.findViewById(R.id.textListaAnimaisRaca);
            donoAnimal = itemView.findViewById(R.id.textListaAnimaisDA);
            nome = itemView.findViewById(R.id.textListaAnimaisNome);
        }

    }
}
