package com.example.travelpetadm.ui.Avaliacao;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.Avaliacao;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.TipoAnimal.AdapterListaTipoAnimal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListaAvaliacao extends RecyclerView.Adapter<AdapterListaAvaliacao.MyViewHolder> {
    private List<Avaliacao> avaliacoes;
    private Context context;

    public AdapterListaAvaliacao(List<Avaliacao> avaliacoes, Context context) {
        this.avaliacoes = avaliacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_avaliacao, parent, false);
        return new AdapterListaAvaliacao.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Avaliacao avaliacao = avaliacoes.get(position);
        holder.textAvaliado.setText(avaliacao.getAvaliado());
        holder.textAvaliador.setText(avaliacao.getAvaliador());
        if (avaliacao.getNotaAvaliacao() >= 4) {
            holder.textNotaAvaliacao.setText(String.valueOf(avaliacao.getNotaAvaliacao()));
            holder.textNotaAvaliacao.setTextColor(Color.GREEN);
        } else {
            if (avaliacao.getNotaAvaliacao() < 4 && avaliacao.getNotaAvaliacao() >= 3) {
                holder.textNotaAvaliacao.setText(String.valueOf(avaliacao.getNotaAvaliacao()));
                holder.textNotaAvaliacao.setTextColor(Color.YELLOW);
            } else {
                if (avaliacao.getNotaAvaliacao() < 3) {
                    holder.textNotaAvaliacao.setText(String.valueOf(avaliacao.getNotaAvaliacao()));
                    holder.textNotaAvaliacao.setTextColor(Color.RED);
                }
            }
        }


        switch(avaliacao.getTipoPerfil()){
            case "motorista":
                holder.imageAvaliacao.setImageResource(R.drawable.ic_menu_motorista);
                break;
            case "donoAnimal":
                holder.imageAvaliacao.setImageResource(R.drawable.ic_menu_donoanimal);
                break;
            default:
                holder.imageAvaliacao.setImageResource(R.drawable.ic_menu_about);
        }
    }

    @Override
    public int getItemCount() { return avaliacoes.size();}

    public void notifyDataSetChanged(ArrayList<Avaliacao>avaliacoes){}

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  textAvaliador, textAvaliado, textNotaAvaliacao;
        CircleImageView imageAvaliacao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                    textAvaliador = itemView.findViewById(R.id.textAvaliador);
                    textAvaliado = itemView.findViewById(R.id.textAvaliado);
                    textNotaAvaliacao = itemView.findViewById(R.id.textNotaAvaliacao);
                    imageAvaliacao = itemView.findViewById(R.id.imageAvaliacao);

        }
    }
}
