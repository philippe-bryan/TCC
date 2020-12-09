package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.DataBasehelpers.DataBaseHelperProd;
import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.Produtos;

public class DetalProdCar extends AppCompatActivity {

    TextView lblNomeProd, lblDesc, lblDataValid, lblQtd, lblPreco;
    ImageView imgProd;
    private DataBaseHelperProd mydb;
    private DataBaseHelperCli mydbCli;
    Produtos produtos;
    int id_To_Update = 0;
    int Value;
    String Email;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_prod_car);

        lblNomeProd = (TextView)findViewById(R.id.lblNomeProd2);
        lblDesc = (TextView)findViewById(R.id.lblDesc2);
        lblDataValid = (TextView)findViewById(R.id.lblDataValid2);
        lblQtd = (TextView)findViewById(R.id.lblQtd2);
        lblPreco = (TextView)findViewById(R.id.lblPreco2);
        imgProd = (ImageView) findViewById(R.id.imgProd2);

        mydb = new DataBaseHelperProd(this);

        Intent intent = getIntent();
        Value = intent.getExtras().getInt("id");
        if(Value>0){
            id_To_Update = Value;
            Cursor rs = mydb.getData(Value);
            rs.moveToFirst();
            produtos = new Produtos();
            produtos.setNome_Produc(rs.getString(rs.getColumnIndex(DataBaseHelperProd.PROD_COLUMN_NAME)));
            produtos.setDescricao_Produc(rs.getString(rs.getColumnIndex(DataBaseHelperProd.PROD_COLUMN_DESC)));
            produtos.setValidade_Produc(rs.getString(rs.getColumnIndex(DataBaseHelperProd.PROD_COLUMN_VAL)));
            produtos.setQuant_Produc(rs.getInt(rs.getColumnIndex(DataBaseHelperProd.PROD_COLUMN_QTD)));
            produtos.setValor_Produc(rs.getString(rs.getColumnIndex(DataBaseHelperProd.PROD_COLUMN_PRECO)));
            produtos.setImagem_Prod(rs.getInt(rs.getColumnIndex(DataBaseHelperProd.PROD_COLUMN_IMG)));
            if (!rs.isClosed())  {
                rs.close();
            }
            lblNomeProd.setText(produtos.getNome_Produc());
            lblDesc.setText(produtos.getDescricao_Produc());
            lblDataValid.setText("Data de validade: "+produtos.getValidade_Produc());
            String Sqtd = "Quantidade disponivel:"+Integer.toString(produtos.getQuant_Produc());
            lblQtd.setText(Sqtd);
            lblPreco.setText("R$:"+produtos.getValor_Produc());
            imgProd.setImageResource(produtos.getImagem_Prod());

            SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
            if(prefs != null) {
                Email = prefs.getString("Email", null);
                mydbCli = new DataBaseHelperCli(this);
                cliente = new Cliente();
                if(Email != null) {
                    Cursor crs = mydbCli.getName(Email);
                    crs.moveToFirst();
                    cliente.setEmail(crs.getString(crs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_EMAIL)));
                    if (!crs.isClosed()) {
                        crs.close();
                    }
                }
            }
        }
    }

    public void btnRemoveOnclick(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteCarAviso)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.deleteProd(id_To_Update);
                        Toast.makeText(getApplicationContext(), R.string.delete_ok, Toast.LENGTH_SHORT).show();
                        String intentEmail = Email;
                        Intent intent = new Intent(getApplication(), MenuPrincipalCli.class);
                        Bundle bundleEmail = new Bundle();
                        bundleEmail.putString("Email", intentEmail);
                        intent.putExtras(bundleEmail);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), R.string.delete_cancel,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog d = builder.create();
        d.setTitle(R.string.deleteCar);
        d.show();
    }
}