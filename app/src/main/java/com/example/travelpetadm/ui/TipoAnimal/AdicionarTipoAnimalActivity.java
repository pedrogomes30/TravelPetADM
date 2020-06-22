package com.example.travelpetadm.ui.TipoAnimal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.DAO.TipoAnimalDAO;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.Permissao;
import com.example.travelpetadm.ui.Motorista.InfoMotoristaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.EventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdicionarTipoAnimalActivity extends AppCompatActivity {
    private TextView textEditarTAEspecie, textEditarTARaca, textEditarTAObservacao;
    private FloatingActionButton btSalvarTA, btSalvarFt;
    private TipoAnimal tipoanimal;
    private String especieS,racaS,descricaoS;
    private CircleImageView circleImageViewiconeEspecie;
    private static final int GALERIA = 100;
    private StorageReference storageImgRef ;
    private ValueEventListener listener;
    Uri localImagemSelecionada;
    String ulr;
    DatabaseReference ref = TipoAnimalDAO.getTipoAnimalReference();
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission. READ_EXTERNAL_STORAGE
    };

    //ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tipo_animal);
        Permissao.validarPermissoes(permissoesNecessarias,this,1);
        iniciarBundle();
        eventoClick();
        alterarIcone();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bitmap  imagem = null;
            try{
                switch(requestCode){
                    case GALERIA:
                        localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                }

            }catch (Exception e ){
                e.printStackTrace();
            }
            if(imagem != null){
                circleImageViewiconeEspecie.setImageBitmap(imagem);
                ulr = TipoAnimalDAO.salvarFotoTipoAnimal(textEditarTAEspecie.getText().toString(),imagem,tipoanimal);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int permissaoResultado : grantResults) {
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                alert("PERMISSÃO NEGADA!" +
                        "\n Para Adicionar um noto tipo Animal, é necessário aceitar as permissões");
                finish();
            }
        }
    }

    //iniciador dos componentes
    private void iniciarComponentes() {
        textEditarTAEspecie = findViewById(R.id.textEditarTAEspecie);
        textEditarTARaca = findViewById(R.id.textEditarTARaca);
        textEditarTAObservacao = findViewById(R.id.textEditarTAObservacao);
        circleImageViewiconeEspecie = findViewById(R.id.circleImageViewiconeEspecie);
        storageImgRef = Conexao.getFirebaseStorage();
        btSalvarTA = findViewById(R.id.btSalvarTA);
        btSalvarFt = findViewById(R.id.btSalvarFt);
        btSalvarFt.setVisibility(View.GONE);

    }
        private void iniciarBundle () {
            iniciarComponentes();
            tipoanimal = new TipoAnimal();
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                tipoanimal = (TipoAnimal) bundle.getSerializable("EditarTipoAnimal");
                textEditarTAEspecie.setText(tipoanimal.getEspecie());
                textEditarTARaca.setText(tipoanimal.getNomeRacaAnimal());
                textEditarTAObservacao.setText(String.valueOf(tipoanimal.getDescricao()));
                btSalvarFt.setVisibility(View.VISIBLE);
                ref.child(tipoanimal.getEspecie()).child(tipoanimal.getNomeRacaAnimal()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tipoanimal = dataSnapshot.getValue(TipoAnimal.class);
                        if(tipoanimal.getIconeUrl()!=null){
                            Uri fotoPerfilUri = Uri.parse(tipoanimal.getIconeUrl());
                            Glide.with(getApplicationContext()).load( fotoPerfilUri ).into( circleImageViewiconeEspecie );
                            //btSalvarFt.setVisibility(View.GONE);
                        }else{
                            circleImageViewiconeEspecie.setImageResource(R.drawable.ic_especie_spinner);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        }


    //Alterar Icone de especie
    public void alterarIcone(){
        btSalvarFt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textEditarTAEspecie.length() == 0){
                    alert("Prencha o campo Expecie primeiro!");
                }else{
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    if(intent.resolveActivity(getPackageManager())!=null) {
                        startActivityForResult(intent, GALERIA);
                    }
                }
            }
        });
        circleImageViewiconeEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert("salve o tipo animal primeiro!");
            }
        });
    }

    //eventos de click na tela
    private void eventoClick(){
        btSalvarTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validador()){
                    criarTipoAnimal();
                }
            }
        });
    }

    //cria o tipo de animal no banco de dados
    private void criarTipoAnimal(){
        tipoanimal.setEspecie(textEditarTAEspecie.getText().toString());
        tipoanimal.setRaca(textEditarTARaca.getText().toString());
        tipoanimal.setDescricao(textEditarTAObservacao.getText().toString());
        alert("animal " + textEditarTARaca.getText().toString() + " salvo com sucesso! ");
        TipoAnimalDAO.salvarTipoAnimal(tipoanimal);
        finish();

    }

    //validador de campos em branco
    private boolean validador() {
        especieS            = textEditarTAEspecie.getText().toString().trim();
            racaS           = textEditarTARaca.getText().toString().trim();
                descricaoS  = textEditarTAObservacao.getText().toString().trim();

        if (!especieS.isEmpty()) {
            if (!racaS.isEmpty()) {
                if (!descricaoS.isEmpty()) {
                    return true;
                } else {
                    alert("informe uma descrição");
                    return false;
                }
                }else{
                alert("informe  uma raca");
                return false;}
            } else {
            alert("informe uma especie ");
            return false;
        }
    }

    //metodo para facilitar toast
    private void alert (String msg){
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        }

}
