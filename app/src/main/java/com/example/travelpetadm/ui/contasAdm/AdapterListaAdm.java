package com.example.travelpetadm.ui.contasAdm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.ui.TipoAnimal.AdapterListaTipoAnimal;

import java.util.List;

public class AdapterListaAdm extends RecyclerView.Adapter<AdapterListaAdm.MyViewHolder> {

    private List<Adm> adms;
    private Context context;

    public AdapterListaAdm(List<Adm> adms, Context context) {
        this.adms = adms;
        this.context = context;
    }
    @Override
    public AdapterListaAdm.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_adm, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Adm adm = adms.get(position);
        holder.email.setText(adm.getEmail());
        holder.nome.setText(String.valueOf(adm.getNome()));
        holder.tipoPerfil.setText(adm.getTipoPerfil());
    }


    @Override
    public int getItemCount() {
        return adms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView email, nome, tipoPerfil;


        public MyViewHolder(View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.textEmail);
            nome = itemView.findViewById(R.id.textNome);
            tipoPerfil = itemView.findViewById(R.id.textTipoPerfil);

        }
    }

}
