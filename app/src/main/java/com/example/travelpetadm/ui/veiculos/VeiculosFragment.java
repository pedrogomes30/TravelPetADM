package com.example.travelpetadm.ui.veiculos;

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
import com.example.travelpetadm.Model.Animal;
import com.example.travelpetadm.Model.TipoAnimal;
import com.example.travelpetadm.Model.Veiculo;
import com.example.travelpetadm.R;
import com.example.travelpetadm.helper.RecyclerItemClickListener;
import com.example.travelpetadm.ui.animais.AdapterListaAnimais;
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

public class VeiculosFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListaVeiculos adapterListaVeiculo;
    private ArrayList<Veiculo> veiculos =new ArrayList<>() ;
    private DatabaseReference veiculoRef;
    private ValueEventListener valueEventListenerVeiculo;
    private ProgressBar progressoVeiculo;

    public VeiculosFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_veiculos, container, false);
        setHasOptionsMenu(true);
        iniciarComponentes(view);
        iniciarReciclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarVeiculo();
    }

    @Override
    public void onStop(){
        super.onStop();
        veiculoRef.removeEventListener(valueEventListenerVeiculo);
    }

    public void iniciarComponentes(View view){
        recyclerView = view.findViewById(R.id.listVeiculo);
        veiculoRef  = Conexao.getFirebaseDatabase().child("veiculos");
        progressoVeiculo = view.findViewById(R.id.progressoVeiculo);
    }
    public void iniciarReciclerView(View view) {

        //configurar Adapter
        adapterListaVeiculo = new AdapterListaVeiculos(veiculos, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaVeiculo);

        //Configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Veiculo veiculoSel =  veiculos.get(position);
                                Intent i =  new Intent(getActivity(),InfoVeiculosActivity.class);
                                i.putExtra("ExibirVeiculo",veiculoSel);
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
    public void recuperarVeiculo (){
        valueEventListenerVeiculo = veiculoRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                veiculos.clear();
                //RECUPERA AS RACAS CADASTRADAS DENTRO DE AVES

                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    for (DataSnapshot veiculolDs : dados.getChildren()) {
                        Veiculo veiculo = veiculolDs.getValue(Veiculo.class);
                        veiculos.add(veiculo);
                        progressoVeiculo.setVisibility(View.VISIBLE);
                    }
                }
                adapterListaVeiculo.notifyDataSetChanged();
                progressoVeiculo.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
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
                gerarXLS();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void gerarXLS(){
        valueEventListenerVeiculo = veiculoRef.addValueEventListener(new ValueEventListener() {
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
                File file = new File(getActivity().getFilesDir(),"Veículos.xls");
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
                        compartilharRel.putExtra(Intent.EXTRA_SUBJECT,  "Veículos");
                        compartilharRel.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        compartilharRel.putExtra(Intent.EXTRA_STREAM,patch);
                        startActivity(Intent.createChooser(compartilharRel, "compartilhar Dados Veículos"));
                        progressoVeiculo.setVisibility(View.VISIBLE);
                    }


                }catch (IOException e) {
                    e.printStackTrace();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                progressoVeiculo.setVisibility(View.GONE);
            }@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
}
