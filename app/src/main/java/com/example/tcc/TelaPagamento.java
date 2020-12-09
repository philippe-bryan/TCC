package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.DataBasehelpers.DataBaseHelperCompra;
import com.example.tcc.DataBasehelpers.DataBaseHelperProd;
import com.example.tcc.Models.Cartao;
import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.Compra;
import com.example.tcc.Models.Login;
import com.example.tcc.Models.MarcarasDeTexto;
import com.example.tcc.Models.Produtos;
import com.example.tcc.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TelaPagamento extends AppCompatActivity {

    EditText txtCartao, txtNomeCard, txtCodSeg, txtValidade, txtBandeira;
    String validade, nomeCard, codSeg, card, bandeira, Email, strDate;
    DataBaseHelperCompra mydb;
    DataBaseHelperCli mydbCli;
    DataBaseHelperProd mydbProd;
    Cliente cliente;
    Compra compra;
    int idCli, idComp;
    Button btnConfirmar;
    Calendar cal;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pagamento);
        txtCartao = (EditText)findViewById(R.id.txtCartao);
        txtNomeCard = (EditText)findViewById(R.id.txtNomeCard);
        txtCodSeg = (EditText)findViewById(R.id.txtCodSeg);
        txtValidade = (EditText)findViewById(R.id.txtValidade);
        txtBandeira = (EditText)findViewById(R.id.txtBandeira);
        btnConfirmar = (Button)findViewById(R.id.btnConfirmar);

        txtValidade.addTextChangedListener(MarcarasDeTexto.mask(txtValidade, MarcarasDeTexto.FORMAT_DATE));
        txtCartao.addTextChangedListener(MarcarasDeTexto.mask(txtCartao, MarcarasDeTexto.FORMAT_COD_CARD));
        txtCodSeg.addTextChangedListener(MarcarasDeTexto.mask(txtCodSeg, MarcarasDeTexto.FORMAT_COD_SEG));

        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(prefs != null) {
            Email = prefs.getString("Email", null);
            mydbCli = new DataBaseHelperCli(getApplication());
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

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card = txtCartao.getText().toString();
                nomeCard = txtNomeCard.getText().toString();
                codSeg = txtCodSeg.getText().toString();
                validade = txtValidade.getText().toString();
                bandeira = txtBandeira.getText().toString();

                if (card.equals("")) {
                    txtCartao.setError("Digite o numero do cartão!");
                } else {
                    if (nomeCard.equals("")) {
                        txtNomeCard.setError("Digite o nome no cartão!");
                    } else {
                        if (codSeg.equals("")) {
                            txtCodSeg.setError("Digite o código de segurança!");
                        } else {
                            if (validade.equals("")) {
                                txtValidade.setError("Digite a validade do cartão!");
                            } else {
                                if (bandeira.equals("")) {
                                    txtBandeira.setError("Digite a bandeira do cartão!");
                                } else {
                                    SalvaCartao();
                                    PegaData();
                                    mydb = new DataBaseHelperCompra(getApplication());
                                    if (mydb.insertComp(new Compra(strDate,"200.00",idCli))) {
                                        Toast.makeText(getApplication(), "Compra Realizada", Toast.LENGTH_SHORT).show();
                                        Cursor crs = mydb.getId();
                                        crs.moveToLast();
                                        compra = new Compra();
                                        compra.setId_Compra(crs.getInt(crs.getColumnIndex(DataBaseHelperCompra.COMP_COLUMN_ID)));
                                        if (!crs.isClosed()) {
                                            crs.close();
                                        }
                                        idComp = compra.getId_Compra();
                                        mydbProd = new DataBaseHelperProd(getApplication());
                                        if(mydbProd.updateIdComp(new Produtos(idCli, idComp))){
                                            String intentEmail = Email;
                                            Intent intent = new Intent(getApplication(), MenuPrincipalCli.class);
                                            Bundle bundleEmail = new Bundle();
                                            bundleEmail.putString("Email", intentEmail);
                                            intent.putExtras(bundleEmail);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }else {
                                        Toast.makeText(getApplication(), "falha no pagamento", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void SalvaCartao(){
        Cartao cartao = new Cartao();
        cartao.setCard(card);
        cartao.setNomeCard(nomeCard);
        cartao.setCodSeg(codSeg);
        cartao.setValid(validade);
        cartao.setBandeira(bandeira);
        SharedPreferences prefs = getSharedPreferences("Cartao", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("CodCard", cartao.getCard());
        ed.putString("Nome", cartao.getNomeCard());
        ed.putString("CodSeg", cartao.getCodSeg());
        ed.putString("Valid", cartao.getValid());
        ed.putString("Bandeira", cartao.getBandeira());
        ed.apply();
    }

    public void PegaData(){
        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(cal.getTime());
    }
}