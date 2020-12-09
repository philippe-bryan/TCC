package com.example.tcc.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.Adapters.CarAdapter;
import com.example.tcc.Adapters.PedidosAdapter;
import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.DataBasehelpers.DataBaseHelperCompra;
import com.example.tcc.DataBasehelpers.DataBaseHelperProd;
import com.example.tcc.MenuPrincipalCli;
import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.Compra;
import com.example.tcc.ProdList;
import com.example.tcc.R;

import java.util.ArrayList;

public class PedidosFragment extends Fragment {

    RecyclerView rcvPedidos;
    TextView lblSemPedidos;
    String Email;
    DataBaseHelperProd mydb;
    DataBaseHelperCli mydbCli;
    DataBaseHelperCompra mydbComp;
    Cliente cliente;
    PedidosAdapter adapter;
    int idCli;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);

        rcvPedidos = root.findViewById(R.id.rcvPedidos);
        lblSemPedidos = root.findViewById(R.id.lblSemPedidos);

        mydbComp = new DataBaseHelperCompra(getActivity());
        mydb = new DataBaseHelperProd(getActivity());
        mydbCli = new DataBaseHelperCli(getActivity());

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

        if (mydbComp.getAll(idCli).isEmpty()) {
            lblSemPedidos.setVisibility(View.VISIBLE);
            rcvPedidos.setVisibility(View.GONE);
        } else {
            adapter = new PedidosAdapter(getActivity(), mydbComp.getAll(idCli));
            rcvPedidos.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcvPedidos.setHasFixedSize(true);
            rcvPedidos.setAdapter(adapter);
            rcvPedidos.setVisibility(View.VISIBLE);
            lblSemPedidos.setVisibility(View.GONE);
        }
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_pedidos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.mnExcluiPed:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.deleteAll)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydbComp.deleteAll();
                                Toast.makeText(getActivity(), R.string.delete_ok,
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MenuPrincipalCli.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle(R.string.deleteAll);
                d.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
