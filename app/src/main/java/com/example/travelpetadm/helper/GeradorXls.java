package com.example.travelpetadm.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.travelpetadm.DAO.Conexao;
import com.example.travelpetadm.Model.DonoAnimal;
import com.example.travelpetadm.Model.Motorista;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.Model.Viagem;
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

public class GeradorXls extends AppCompatActivity {
    private DatabaseReference ref;
    private ValueEventListener listener;
    Context context;

    public GeradorXls(String classe, Context context){
        this.context = context;
        switch(classe){
            case "Adm":
                break;
            case "Animal":
                break;
            case "Avaliacao":
                break;
            case "DonoAnimal":
                xlsDonoAnimal();
                break;
            case "Endereco":

                break;
            case "Motorista":
                xlsMotorista();
                break;
            case "TipoAnimal":
                xlsTipoAnimal();
                break;
            case "Veiculo":
                xlsVeiculo();
                break;
            case "Viagem":
                xlsViagem();
                break;
            default:
                alert("Denominação não encontrada");
        }
    }

    //não esta puxando devido a classe ser herdada de outra classe
    public  void xlsDonoAnimal(){
        ref = Conexao.getFirebaseDatabase().child("donoAnimal");
       listener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int indicador = 1;
                Workbook wb = new HSSFWorkbook();
                Cell cell= null;
                CellStyle cellStyle =wb.createCellStyle();
                cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
                Sheet sheet = null;
                sheet = wb.createSheet("Donos de animais cadastrados");

                alert("criou a planilha");

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

                alert("criou as colunas");

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
                    alert("baixou os dados");

                }
                //salvando a planilha criada no diretorio do dispositivo
                File file = new File(getFilesDir(),"Dono Animais.xls");
                FileOutputStream outputStream = null;
                alert("criou o arquivo" );

                try{
                    outputStream = new FileOutputStream (file);
                    wb.write(outputStream);
                    compartilharXls(file);
                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    public void xlsTipoAnimal(){
        ref = Conexao.getFirebaseDatabase().child("racaAnimal");
        listener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int indicador = 1;
                Workbook wb = new HSSFWorkbook();
                Cell cell= null;
                CellStyle cellStyle =wb.createCellStyle();
                cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
                Sheet sheet = null;
                sheet = wb.createSheet("Tipo de animal cadastrados");


                //construindo linhas de indicadores de atributo
                Row row = sheet.createRow(0);
                cell = row.createCell(0);cell.setCellValue("Espécie");cell.setCellStyle(cellStyle);sheet.setColumnWidth(0,(10*200));
                cell = row.createCell(1);cell.setCellValue("Raça");cell.setCellStyle(cellStyle);sheet.setColumnWidth(1,(10*200));
                cell = row.createCell(2);cell.setCellValue("Descrição");cell.setCellStyle(cellStyle);sheet.setColumnWidth(2,(10*200));



                //adicionando o conteudo
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for (DataSnapshot dados2 : dados.getChildren()) {
                        TipoAnimal tipoAnimal = dados2.getValue(TipoAnimal.class);
                        // inserindo os dados na planilha
                        Row row1 = sheet.createRow(indicador);
                        cell = row1.createCell(0);cell.setCellValue(tipoAnimal.getEspecie());
                        cell = row1.createCell(1);cell.setCellValue(tipoAnimal.getNomeRacaAnimal());
                        cell = row1.createCell(2);cell.setCellValue(tipoAnimal.getDescricao());
                        indicador++;
                    }

                }

                //salvando a planilha criada no diretorio do dispositivo
                File file = new File(context.getFilesDir(),"Tipo de Animais.xls");
                FileOutputStream outputStream = null;

                try{
                    outputStream = new FileOutputStream (file);
                    wb.write(outputStream);
                    compartilharXls(file);
                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void xlsViagem(){
        ref= Conexao.getFirebaseDatabase().child("viagem");
        listener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int indicador = 1;
                Workbook wb = new HSSFWorkbook();
                Cell cell= null;
                CellStyle cellStyle =wb.createCellStyle();
                cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
                Sheet sheet = null;
                sheet = wb.createSheet("Viagens Realizadas");
                //construindo linhas de indicados de atributo
                Row row = sheet.createRow(0);
                cell = row.createCell(0);cell.setCellValue("ID Viagem");             cell.setCellStyle(cellStyle);sheet.setColumnWidth(0,(10*200));
                cell = row.createCell(1);cell.setCellValue("ID DonoAnimal");         cell.setCellStyle(cellStyle);sheet.setColumnWidth(1,(10*200));
                cell = row.createCell(2);cell.setCellValue("ID Animal");             cell.setCellStyle(cellStyle);sheet.setColumnWidth(2,(10*200));
                cell = row.createCell(3);cell.setCellValue("ID Motorista");          cell.setCellStyle(cellStyle);sheet.setColumnWidth(3,(10*200));
                cell = row.createCell(4);cell.setCellValue("ID veiculo");            cell.setCellStyle(cellStyle);sheet.setColumnWidth(4,(10*200));
                cell = row.createCell(5);cell.setCellValue("data");                  cell.setCellStyle(cellStyle);sheet.setColumnWidth(5,(10*200));
                cell = row.createCell(6);cell.setCellValue("Origem");                cell.setCellStyle(cellStyle);sheet.setColumnWidth(6,(10*200));
                cell = row.createCell(7);cell.setCellValue("Destino");               cell.setCellStyle(cellStyle);sheet.setColumnWidth(7,(10*200));
                cell = row.createCell(8);cell.setCellValue("Distancia");             cell.setCellStyle(cellStyle);sheet.setColumnWidth(8,(10*200));
                cell = row.createCell(9);cell.setCellValue("Hora Inicio");           cell.setCellStyle(cellStyle);sheet.setColumnWidth(9,(10*200));
                cell = row.createCell(10);cell.setCellValue("Hora Fim");             cell.setCellStyle(cellStyle);sheet.setColumnWidth(10,(10*200));
                cell = row.createCell(11);cell.setCellValue("Porte animal");         cell.setCellStyle(cellStyle);sheet.setColumnWidth(11,(10*200));
                cell = row.createCell(12);cell.setCellValue("custo");                cell.setCellStyle(cellStyle);sheet.setColumnWidth(12,(10*200));


                //adicionando o conteudo
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    Viagem viagem = dados.getValue(Viagem.class);
                    // inserindo os dados na planilha
                    Row row1 = sheet.createRow(indicador);
                    cell = row1.createCell(0);cell.setCellValue(viagem.getIDViagem());
                    cell = row1.createCell(1);cell.setCellValue(viagem.getIDDonoAnimal());
                    cell = row1.createCell(2);cell.setCellValue(viagem.getAnimal());
                    cell = row1.createCell(3);cell.setCellValue(viagem.getIDMotorista());
                    cell = row1.createCell(4);cell.setCellValue(viagem.getIDVeiculo());
                    cell = row1.createCell(5);cell.setCellValue(viagem.getData());
                    cell = row1.createCell(6);cell.setCellValue(viagem.getOrigem());
                    cell = row1.createCell(7);cell.setCellValue(viagem.getDestino());
                    cell = row1.createCell(8);cell.setCellValue(viagem.getDistancia());
                    cell = row1.createCell(9);cell.setCellValue(viagem.getHoraInicio());
                    cell = row1.createCell(10);cell.setCellValue(viagem.getHoraFim());
                    cell = row1.createCell(11);cell.setCellValue(viagem.getPorteAnimal());
                    cell = row1.createCell(12);cell.setCellValue(viagem.getCustoViagem());
                    indicador++;

                }
                //salvando a planilha criada no diretorio do dispositivo
                File file = new File(context.getFilesDir(),"Viagem.xls");
                FileOutputStream outputStream = null;
                try{
                    outputStream = new FileOutputStream (file);
                    wb.write(outputStream);
                    compartilharXls(file);
                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void xlsVeiculo(){
        ref = Conexao.getFirebaseDatabase().child("veiculos");
        listener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int indicador = 1;
                Workbook wb = new HSSFWorkbook();
                Cell cell= null;
                CellStyle cellStyle =wb.createCellStyle();
                cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
                Sheet sheet = null;
                sheet = wb.createSheet("Veiculos cadastrados");

                //construindo linhas de indicados de atributo
                Row row = sheet.createRow(0);

                cell = row.createCell(0);cell.setCellValue("ID Veiculo");cell.setCellStyle(cellStyle);sheet.setColumnWidth(0,(10*200));
                cell = row.createCell(1);cell.setCellValue("ID Motorista");cell.setCellStyle(cellStyle);sheet.setColumnWidth(1,(10*200));
                cell = row.createCell(2);cell.setCellValue("Marca");cell.setCellStyle(cellStyle);sheet.setColumnWidth(2,(10*200));
                cell = row.createCell(3);cell.setCellValue("Modelo");cell.setCellStyle(cellStyle);sheet.setColumnWidth(3,(10*200));
                cell = row.createCell(4);cell.setCellValue("Ano Fabricação");cell.setCellStyle(cellStyle);sheet.setColumnWidth(4,(10*200));
                cell = row.createCell(5);cell.setCellValue("Placa Veículo");cell.setCellStyle(cellStyle);sheet.setColumnWidth(5,(10*200));
                cell = row.createCell(6);cell.setCellValue("Status Cadastro");cell.setCellStyle(cellStyle);sheet.setColumnWidth(6,(10*200));
                cell = row.createCell(7);cell.setCellValue("Nº CRVL");cell.setCellStyle(cellStyle);sheet.setColumnWidth(7,(10*200));
                cell = row.createCell(8);cell.setCellValue("CRVL URL");cell.setCellStyle(cellStyle);sheet.setColumnWidth(8,(10*200));

                //adicionando o conteudo
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for (DataSnapshot dados2 : dados.getChildren()) {
                        Veiculo veiculo = dados2.getValue(Veiculo.class);

                        // inserindo os dados na planilha
                        Row row1 = sheet.createRow(indicador);

                        cell = row1.createCell(0);cell.setCellValue(veiculo.getIdVeiculo());
                        cell = row1.createCell(1);cell.setCellValue(veiculo.getIdUsuario());
                        cell = row1.createCell(2);cell.setCellValue(veiculo.getMarcaVeiculo());
                        cell = row1.createCell(3);cell.setCellValue(veiculo.getModeloVeiculo());
                        cell = row1.createCell(4);cell.setCellValue(veiculo.getAnoVeiculo());
                        cell = row1.createCell(5);cell.setCellValue(veiculo.getPlacaVeiculo());
                        cell = row1.createCell(6);cell.setCellValue(veiculo.getStatus());
                        cell = row1.createCell(7);cell.setCellValue(veiculo.getCrvlVeiculo());
                        cell = row1.createCell(8);cell.setCellValue(veiculo.getFotoCRVLurl());

                        indicador++;

                    }
                }

                //salvando a planilha criada no diretorio do dispositivo
                File file = new File(context.getFilesDir(),"Veículos.xls");
                FileOutputStream outputStream = null;

                try{
                    outputStream = new FileOutputStream (file);
                    wb.write(outputStream);
                    compartilharXls(file);
                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    public void xlsMotorista(){
        ref = Conexao.getFirebaseDatabase().child("motorista");
       listener = ref.addValueEventListener(new ValueEventListener() {
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
                File file = new File(context.getFilesDir(),"Motoristas.xls");
                FileOutputStream outputStream = null;

                try{
                    outputStream = new FileOutputStream (file);
                    wb.write(outputStream);
                    compartilharXls(file);
                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    //compartilhar a planilha gerada
    public void compartilharXls(File file) {

        if (file.exists()) { // verifica se existe o arquivo
            Uri patch = FileProvider.getUriForFile(context, "com.example.travelpetadm.fileprovider", file);
            Intent compartilharRel = new Intent(Intent.ACTION_SEND);
            compartilharRel.setType("text/xls");
            compartilharRel.putExtra(Intent.EXTRA_SUBJECT, "Dados ");
            compartilharRel.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            compartilharRel.putExtra(Intent.EXTRA_STREAM, patch);
            context.startActivity(Intent.createChooser(compartilharRel, "compartilhar Dados "));

        }
    }

    public void alert(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
