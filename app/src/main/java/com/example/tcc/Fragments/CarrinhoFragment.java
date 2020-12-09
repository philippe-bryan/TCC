package com.example.tcc.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.Adapters.CarAdapter;
import com.example.tcc.Adapters.ProdAdapter;
import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.DataBasehelpers.DataBaseHelperCompra;
import com.example.tcc.DataBasehelpers.DataBaseHelperProd;
import com.example.tcc.MainActivity;
import com.example.tcc.MenuPrincipalCli;
import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.Compra;
import com.example.tcc.Models.Produtos;
import com.example.tcc.R;
import com.example.tcc.TelaPagamento;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoFragment extends Fragment {

    View root;
    CarAdapter adapter;
    DataBaseHelperProd mydb;
    DataBaseHelperCli mydbCli;
    DataBaseHelperCompra mydbComp;
    RecyclerView rcvCar;
    Button btnComprar;
    TextView lblSemCompra, lblPrecoTotal;
    String Email, Card, Nome, CodSeg, Valid, Band;
    Cliente cliente;
    Compra compra;
    int idCli, idComp;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_carrinho, container, false);
        lblSemCompra = root.findViewById(R.id.lblSemCompra);
        mydb = new DataBaseHelperProd(getActivity());
        rcvCar = (RecyclerView) root.findViewById(R.id.rcvCar);
        lblPrecoTotal = root.findViewById(R.id.lblPrecoTotal);
        btnComprar = root.findViewById(R.id.btnComprar);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(prefs != null) {
            Email = prefs.getString("Email", null);
            mydbCli = new DataBaseHelperCli(getActivity());
            cliente = new Cliente();
            if(Email != null) {
                Cursor rs = mydbCli.getName(Email);
                rs.moveToFirst();
                cliente.setEmail(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_EMAIL)));
                cliente.setIdCli(rs.getInt(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_ID)));
                if (!rs.isClosed()) {
                    rs.close();
                }
                idCli = cliente.getIdCli();
            }
        }

        if (mydb.getAll(idCli, 0).isEmpty()) {
            lblSemCompra.setVisibility(View.VISIBLE);
            rcvCar.setVisibility(View.GONE);
            lblPrecoTotal.setVisibility(View.GONE);
            btnComprar.setVisibility(View.GONE);
        } else {
            btnComprar.setVisibility(View.VISIBLE);
            adapter = new CarAdapter(getActivity(), mydb.getAll(idCli, 0));
            rcvCar.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcvCar.setHasFixedSize(true);
            rcvCar.setAdapter(adapter);
            lblSemCompra.setVisibility(View.GONE);
            rcvCar.setVisibility(View.VISIBLE);
            lblPrecoTotal.setVisibility(View.GONE);
        }

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getActivity().getSharedPreferences("Cartao", Context.MODE_PRIVATE);
                if(prefs != null){
                    Card = prefs.getString("CodCard", null);
                    Nome = prefs.getString("Nome", null);
                    CodSeg = prefs.getString("CodSeg", null);
                    Valid = prefs.getString("Valid", null);
                    Band = prefs.getString("Bandeira", null);
                    if (Card != null && Nome != null && CodSeg != null && Valid != null && Band != null) {
                        mydbComp = new DataBaseHelperCompra(getActivity());
                        if (mydbComp.insertComp(new Compra("10/12/2020","200.00",idCli))) {
                            Toast.makeText(getActivity(), "Compra Realizada", Toast.LENGTH_SHORT).show();
                            Cursor crs = mydbComp.getId();
                            crs.moveToLast();
                            compra = new Compra();
                            compra.setId_Compra(crs.getInt(crs.getColumnIndex(DataBaseHelperCompra.COMP_COLUMN_ID)));
                            if (!crs.isClosed()) {
                                crs.close();
                            }
                            idComp = compra.getId_Compra();
                            mydb = new DataBaseHelperProd(getActivity());
                            if(mydb.updateIdComp(new Produtos(idCli, idComp))){
                                String intentEmail = Email;
                                Intent intent = new Intent(getActivity(), MenuPrincipalCli.class);
                                Bundle bundleEmail = new Bundle();
                                bundleEmail.putString("Email", intentEmail);
                                intent.putExtras(bundleEmail);
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(getActivity(), "falha no pagamento", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Intent intentPagar = new Intent(getActivity(), TelaPagamento.class);
                        startActivity(intentPagar);
                    }
                }
                Intent intentPagar = new Intent(getActivity(), TelaPagamento.class);
                startActivity(intentPagar);
            }
        });
        return root;
    }
}
