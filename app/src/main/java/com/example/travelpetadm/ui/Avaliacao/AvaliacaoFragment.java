package com.example.travelpetadm.ui.Avaliacao;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

public class AvaliacaoFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_avaliacao, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        setHasOptionsMenu(true);
        return root;
    }

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
                //gerarXLS();
                break;
            case R.id.action_procurar:
                Toast.makeText(getActivity(),"não há link com o firebase",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void gerarXLS(){
        valueEventListenerDonoAnimal = donoAnimalRef.addValueEventListener(new ValueEventListener() {
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

                cell = row.createCell(0);cell.setCellValue("ID Dono Animal");               cell.setCellStyle(cellStyle);sheet.setColumnWidth(0,(10*200));
                cell = row.createCell(1);cell.setCellValue("Nome");                         cell.setCellStyle(cellStyle);sheet.setColumnWidth(1,(10*200));
                cell = row.createCell(2);cell.setCellValue("Sobrenome");                    cell.setCellStyle(cellStyle);sheet.setColumnWidth(2,(10*200));
                cell = row.createCell(3);cell.setCellValue("CPF");                          cell.setCellStyle(cellStyle);sheet.setColumnWidth(3,(10*200));
                cell = row.createCell(4);cell.setCellValue("Email");                        cell.setCellStyle(cellStyle);sheet.setColumnWidth(4,(10*200));
                cell = row.createCell(5);cell.setCellValue("Avaliação");                    cell.setCellStyle(cellStyle);sheet.setColumnWidth(5,(10*200));
                cell = row.createCell(6);cell.setCellValue("Telefone");                     cell.setCellStyle(cellStyle);sheet.setColumnWidth(6,(10*200));
                cell = row.createCell(7);cell.setCellValue("Status");                       cell.setCellStyle(cellStyle);sheet.setColumnWidth(7,(10*200));
                cell = row.createCell(8);cell.setCellValue("UrlFotoPerfil");                cell.setCellStyle(cellStyle);sheet.setColumnWidth(8,(10*200));

                //adicionando o conteudo
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    DonoAnimal donoAnimal = dados.getValue(DonoAnimal.class);
                    // inserindo os dados na planilha
                    Row row1 = sheet.createRow(indicador);
                    cell = row1.createCell(0);cell.setCellValue(donoAnimal.getId());
                    cell = row1.createCell(1);cell.setCellValue(donoAnimal.getNome());
                    cell = row1.createCell(2);cell.setCellValue(donoAnimal.getSobrenome());
                    cell = row1.createCell(3);cell.setCellValue(donoAnimal.getCpf());
                    cell = row1.createCell(4);cell.setCellValue(donoAnimal.getEmail());
                    cell = row1.createCell(5);cell.setCellValue(String.valueOf(donoAnimal.getAvaliacao()));
                    cell = row1.createCell(6);cell.setCellValue(donoAnimal.getTelefone());
                    cell = row1.createCell(7);cell.setCellValue(donoAnimal.getStatus());
                    cell = row1.createCell(8);cell.setCellValue(donoAnimal.getFotoUsuarioUrl());
                    indicador++;
                }
                //salvando a planilha criada no diretorio do dispositivo
                File file = new File(getActivity().getFilesDir(),"Dono Animais.xls");
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

    }*/
}
