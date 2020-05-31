package com.example.travelpetadm.ui.viagens;

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
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.Model.Viagem;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.veiculos.AdapterListaVeiculos;
import com.example.travelpetadm.ui.veiculos.InfoVeiculosActivity;
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


public class ViagemFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListaViagem adapterListaViagem;
    private ArrayList<Viagem> viagens =new ArrayList<>() ;
    private DatabaseReference viagemRef;
    private ValueEventListener valueEventListenerViagem;
    private ProgressBar progressoViagem;

    public ViagemFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viagem, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarViagem();
    }

    @Override
    public void onStop(){
        super.onStop();
        viagemRef.removeEventListener(valueEventListenerViagem);
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listViagem);
        viagemRef  = Conexao.getFirebaseDatabase().child("viagem");
        progressoViagem = view.findViewById(R.id.progressoViagem);
    }

    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaViagem = new AdapterListaViagem(viagens, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaViagem);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Viagem viagem = viagens.get(position);
                                Intent i =  new Intent(getActivity(), InfoViagensActivity.class);
                                i.putExtra("ExibirViagem",viagem);
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

    public void recuperarViagem () {
        valueEventListenerViagem = viagemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viagens.clear();
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE AVES

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Viagem viagem = dados.getValue(Viagem.class);
                        viagens.add(viagem);
                        progressoViagem.setVisibility(View.VISIBLE);
                }
                adapterListaViagem.notifyDataSetChanged();
                progressoViagem.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void gerarXLS(){
        valueEventListenerViagem = viagemRef.addValueEventListener(new ValueEventListener() {
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
                    cell = row1.createCell(0);cell.setCellValue(viagem.getID());
                    cell = row1.createCell(1);cell.setCellValue(viagem.getDonoAnimal());
                    cell = row1.createCell(2);cell.setCellValue(viagem.getAnimal());
                    cell = row1.createCell(3);cell.setCellValue(viagem.getMotorista());
                    cell = row1.createCell(4);cell.setCellValue(viagem.getIDVeiculo());
                    cell = row1.createCell(5);cell.setCellValue(viagem.getData());
                    cell = row1.createCell(6);cell.setCellValue(viagem.getOrigem());
                    cell = row1.createCell(7);cell.setCellValue(viagem.getDestino());
                    cell = row1.createCell(8);cell.setCellValue(viagem.getDistancia());
                    cell = row1.createCell(9);cell.setCellValue(viagem.getHoraInicio());
                    cell = row1.createCell(10);cell.setCellValue(viagem.getHoraFim());
                    cell = row1.createCell(11);cell.setCellValue(viagem.getPorteAnimal());
                    cell = row1.createCell(12);cell.setCellValue(viagem.getCusto());
                    indicador++;
                }
                //salvando a planilha criada no diretorio do dispositivo
                File file = new File(getActivity().getFilesDir(),"Viagem.xls");
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
                        compartilharRel.putExtra(Intent.EXTRA_SUBJECT,  "Viagem");
                        compartilharRel.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        compartilharRel.putExtra(Intent.EXTRA_STREAM,patch);
                        startActivity(Intent.createChooser(compartilharRel, "compartilhar Dados de Viagem"));
                        progressoViagem.setVisibility(View.VISIBLE);
                    }

                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                progressoViagem.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
