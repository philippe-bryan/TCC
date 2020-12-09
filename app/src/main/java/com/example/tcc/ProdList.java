package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tcc.Adapters.CarAdapter;
import com.example.tcc.Adapters.ProdListAdapter;
import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.DataBasehelpers.DataBaseHelperCompra;
import com.example.tcc.DataBasehelpers.DataBaseHelperProd;
import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.Compra;

public class ProdList extends AppCompatActivity {

    DataBaseHelperProd mydb;
    DataBaseHelperCli mydbCli;
    RecyclerView rcvProd;
    Button btnComprar;
    String Email;
    Cliente cliente;
    int idCli, idComp;
    ProdListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_list);

        rcvProd = (RecyclerView) findViewById(R.id.rcvListProd);
        mydb = new DataBaseHelperProd(this);

        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(prefs != null) {
            Email = prefs.getString("Email", null);
            mydbCli = new DataBaseHelperCli(this);
            cliente = new Cliente();
            if(Email != null) {
                Cursor rs = mydbCli.getName(Email);
                rs.moveToFirst();
                cliente.setIdCli(rs.getInt(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_ID)));
                if (!rs.isClosed()) {
                    rs.close();
                }
                idCli = cliente.getIdCli();
            }
        }

        Intent intent = getIntent();
        idComp = intent.getExtras().getInt("id");

        adapter = new ProdListAdapter(this, mydb.getAll(idCli, idComp));
        rcvProd.setLayoutManager(new LinearLayoutManager(this));
        rcvProd.setHasFixedSize(true);
        rcvProd.setAdapter(adapter);
        rcvProd.setVisibility(View.VISIBLE);
    }
}