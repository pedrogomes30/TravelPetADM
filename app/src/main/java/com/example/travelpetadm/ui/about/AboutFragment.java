package com.example.travelpetadm.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelpetadm.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //metodo para inflar o fragment
        //return inflater.inflate(R.layout.fragment_about, container, false);

        /*esta classe utiliza uma biblioteca AboutPage, no qual não é necessário implementar o XML,
        todo layout é realizado dentro da classe controll e gerado automaticamente.*/

        Element versaoPrincipal =new Element();
        Element versaoAdm =new Element();

        versaoPrincipal.setTitle("aplicação principal versao 41.0");
        versaoAdm.setTitle("aplicação administrativa versao 1.0");

        return new AboutPage(getActivity())
                .setImage(R.drawable.logo_travel_pet_laranja)
                .setDescription(getString(R.string.descricao))
                .addGroup("Entre em contato")
                .addEmail("travelpetapp@gmail.com.br","Envie um e-mail")
                .addFacebook("TravelPet-102032251464773","Facebook")
                .addGroup("paginas do projeto")
                .addGitHub("pedrogomes30/TravelPet","Aplicativo Principal")
                .addGitHub("pedrogomes30/TravelPetADM","Aplicativo ADM")
                .addItem(versaoPrincipal)
                .addItem(versaoAdm)
                .create();


    }
}
