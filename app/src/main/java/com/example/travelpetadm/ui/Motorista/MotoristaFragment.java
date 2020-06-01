package com.example.travelpetadm.ui.Motorista;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.Motorista.AdapterMotorista;
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

public class MotoristaFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterMotorista adapterListaMotorista;
    private ArrayList<Motorista> Motoristas =new ArrayList<>() ;
    private DatabaseReference motoristaRef;
    private ValueEventListener valueEventListenerMotorista;
    private ProgressBar progressoMO;

    public MotoristaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motorista, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        recuperarMotorista();
    }
    @Override
    public void onStop(){
        super.onStop();
        motoristaRef.removeEventListener(valueEventListenerMotorista);
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listaMotorista);
        motoristaRef  = Conexao.getFirebaseDatabase().child("motorista");
        progressoMO = view.findViewById(R.id.progressMO);
    }

    public void recuperarMotorista (){
        valueEventListenerMotorista = motoristaRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Motoristas.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Motorista motorista = dados.getValue(Motorista.class);
                    Motoristas.add(motorista);
                    progressoMO.setVisibility(View.VISIBLE);

                }
                adapterListaMotorista.notifyDataSetChanged();
                progressoMO.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaMotorista = new AdapterMotorista(Motoristas, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaMotorista);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                               /* DonoAnimal donoAnimalSel =  donosAnimais.get(position);
                                Intent i =  new Intent(getActivity(), InfoDonoAnimalActivity.class);
                                i.putExtra("ExibirDonoAnimal",donoAnimalSel);
                                startActivity(i);*/
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

    private void alert(String MSG){
        Toast.makeText(getActivity(),MSG,Toast.LENGTH_SHORT).show();
    }

    //BOTAO DE MENU
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //necessário ou o botão e selecionado em qualquer ação
        switch(item.getItemId()){
            case R.id.action_salvar:
                gerarXLS();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gerarXLS(){
        valueEventListenerMotorista = motoristaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int indicador = 1;
                Workbook wb = new HSSFWorkbook();
                Cell cell= null;
                CellStyle cellStyle =wb.createCellStyle();
                cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
                Sheet sheet = null;
                sheet = wb.createSheet("Donos de animais cadastrados");

                //construindo linhas de indicados de atributo
                Row row = sheet.createRow(0);

                cell = row.createCell(0);cell.setCellValue("ID motorista");                 cell.setCellStyle(cellStyle);sheet.setColumnWidth(0,(10*200));
                cell = row.createCell(1);cell.setCellValue("Nome");                         cell.setCellStyle(cellStyle);sheet.setColumnWidth(1,(10*200));
                cell = row.createCell(2);cell.setCellValue("Sobrenome");                    cell.setCellStyle(cellStyle);sheet.setColumnWidth(2,(10*200));
                cell = row.createCell(3);cell.setCellValue("CPF");                          cell.setCellStyle(cellStyle);sheet.setColumnWidth(3,(10*200));
                cell = row.createCell(4);cell.setCellValue("Email");                        cell.setCellStyle(cellStyle);sheet.setColumnWidth(4,(10*200));
                cell = row.createCell(5);cell.setCellValue("Avaliação");                    cell.setCellStyle(cellStyle);sheet.setColumnWidth(5,(10*200));
                cell = row.createCell(6);cell.setCellValue("Telefone");                     cell.setCellStyle(cellStyle);sheet.setColumnWidth(6,(10*200));
                cell = row.createCell(7);cell.setCellValue("StatusCadastro");               cell.setCellStyle(cellStyle);sheet.setColumnWidth(7,(10*200));
                cell = row.createCell(7);cell.setCellValue("CNH");                          cell.setCellStyle(cellStyle);sheet.setColumnWidth(8,(10*200));
                cell = row.createCell(7);cell.setCellValue("Foto CNH URL");                 cell.setCellStyle(cellStyle);sheet.setColumnWidth(9,(10*200));
                cell = row.createCell(7);cell.setCellValue("Foto Perfil URL");              cell.setCellStyle(cellStyle);sheet.setColumnWidth(10,(10*200));


                //adicionando o conteudo
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                        Motorista motorista = dados.getValue(Motorista.class);

                        // inserindo os dados na planilha
                        Row row1 = sheet.createRow(indicador);

                        cell = row1.createCell(0);cell.setCellValue(motorista.getId());
                        cell = row1.createCell(1);cell.setCellValue(motorista.getNome());
                        cell = row1.createCell(2);cell.setCellValue(motorista.getSobrenome());
                        cell = row1.createCell(3);cell.setCellValue(motorista.getCpf());
                        cell = row1.createCell(4);cell.setCellValue(motorista.getEmail());
                        cell = row1.createCell(5);cell.setCellValue(String.valueOf(motorista.getAvaliacao()));
                        cell = row1.createCell(6);cell.setCellValue(motorista.getTelefone());
                        cell = row1.createCell(7);cell.setCellValue(motorista.getStatus());
                        cell = row1.createCell(7);cell.setCellValue(motorista.getCnh());
                        cell = row1.createCell(7);cell.setCellValue(motorista.getCnhURL());
                        cell = row1.createCell(7);cell.setCellValue(motorista.getFotoPerfilURL());
                        indicador++;
                }

                //salvando a planilha criada no diretorio do dispositivo
                File file = new File(getActivity().getFilesDir(),"Motoristas.xls");
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
                        compartilharRel.putExtra(Intent.EXTRA_SUBJECT,  "Dados Tipos de animais");
                        compartilharRel.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        compartilharRel.putExtra(Intent.EXTRA_STREAM,patch);
                        startActivity(Intent.createChooser(compartilharRel, "compartilhar Dados Tipo de animal"));
                        progressoMO.setVisibility(View.VISIBLE);
                    }


                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                progressoMO.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
}
