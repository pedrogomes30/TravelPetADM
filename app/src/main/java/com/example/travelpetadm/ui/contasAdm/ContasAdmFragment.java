package com.example.travelpetadm.ui.contasAdm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.Adm;
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.donoanimal.AdapterDonoAnimal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContasAdmFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListaAdm adapterListaAdm;
    private ArrayList<Adm> adms =new ArrayList<>() ;
    private DatabaseReference admRef;
    private ValueEventListener valueEventListenerAdm;
    private ProgressBar progresso;

    public ContasAdmFragment() {}

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop(){
        super.onStop();
        admRef.removeEventListener(valueEventListenerAdm);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contas_adm, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        recuperarAdm();
        return view;
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listaAdm);
        admRef  = Conexao.getFirebaseDatabase().child("adm");
        progresso = view.findViewById(R.id.progressAdm);
    }

    public void recuperarAdm (){
        valueEventListenerAdm = admRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adms.clear();
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE AVES
               for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Adm adm = dados.getValue(Adm.class);
                    adms.add(adm);
                    progresso.setVisibility(View.VISIBLE);
                }

                adapterListaAdm.notifyDataSetChanged();
                progresso.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
    adapterListaAdm = new AdapterListaAdm(adms, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaAdm);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                               Adm admSel =  adms.get(position);
                                Intent i =  new Intent(getActivity(), InfoAdmActivity.class);
                                i.putExtra("ExibirAdm",admSel);
                                startActivity(i);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {
                            }
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            }
                        }
                )
        );
    }

    //BOTÔES DE MENU
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main2, menu);
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //necessário ou o botão e selecionado em qualquer ação
        switch(item.getItemId()){
            case R.id.action_salvar:
                gerarXLS();
                break;
            case R.id.action_adicionar:
                adicionarAdm();
                break;
            case R.id.action_procurar:
                Toast.makeText(getActivity(),"EM IMPLEMENTAÇÃO",Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gerarXLS(){
        valueEventListenerAdm = admRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int indicador = 1;
                Workbook wb = new HSSFWorkbook();
                Cell cell= null;
                CellStyle cellStyle =wb.createCellStyle();
                cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
                Sheet sheet = null;
                sheet = wb.createSheet("Administradores Cadastrados");
                //construindo linhas de indicados de atributo
                Row row = sheet.createRow(0);
                cell = row.createCell(0);cell.setCellValue("ID Adm");               cell.setCellStyle(cellStyle);sheet.setColumnWidth(0,(10*200));
                cell = row.createCell(1);cell.setCellValue("Nome");                 cell.setCellStyle(cellStyle);sheet.setColumnWidth(1,(10*200));
                cell = row.createCell(2);cell.setCellValue("Email");                cell.setCellStyle(cellStyle);sheet.setColumnWidth(2,(10*200));
                cell = row.createCell(3);cell.setCellValue("Tipo de Perfil");       cell.setCellStyle(cellStyle);sheet.setColumnWidth(3,(10*200));

                //adicionando o conteudo
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                        Adm adm = dados.getValue(Adm.class);
                        // inserindo os dados na planilha
                        Row row1 = sheet.createRow(indicador);
                        cell = row1.createCell(0);cell.setCellValue(adm.getIdAdm());
                        cell = row1.createCell(1);cell.setCellValue(adm.getNome());
                        cell = row1.createCell(2);cell.setCellValue(adm.getEmail());
                        cell = row1.createCell(3);cell.setCellValue(adm.getTipoPerfil());
                        indicador++;
                }
                //salvando a planilha criada no diretorio do dispositivo
                File file = new File(getActivity().getFilesDir(),"Adm.xls");
                FileOutputStream outputStream = null;
                try{
                    outputStream = new FileOutputStream (file);
                    wb.write(outputStream);
                    //compartilhar a planilha gerada
                    if(file.exists()) { // verifica se existe o arquivo
                        Context context = getActivity().getApplicationContext();
                        Uri patch = FileProvider.getUriForFile(context,"com.example.travelpetadm.fileprovider",file);
                        Intent compartilharRel = new Intent(Intent.ACTION_SEND);
                        compartilharRel.setType("text/xls");
                        compartilharRel.putExtra(Intent.EXTRA_SUBJECT,  "Animais");
                        compartilharRel.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        compartilharRel.putExtra(Intent.EXTRA_STREAM,patch);
                        startActivity(Intent.createChooser(compartilharRel, "compartilhar Dados Administradores"));
                        progresso.setVisibility(View.VISIBLE);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                progresso.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void adicionarAdm(){
        startActivity(new Intent(getActivity(), AdicionarAdmActivity.class));
    }

}
