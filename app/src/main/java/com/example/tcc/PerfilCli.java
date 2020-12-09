package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.MarcarasDeTexto;

public class PerfilCli extends AppCompatActivity {

    private DataBaseHelperCli mydb;
    EditText txtNome, txtCNPJ, txtEnd, txtRazao, txtTel, txtCel;
    String nome, cnpj, end, razao, tel, cel;
    Button btnConcluir, btnCancelar;
    int id_To_Update = 0;
    Cliente cliente;
    int Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cli);

        txtNome = (EditText) findViewById(R.id.txtNomeCli);
        txtCNPJ = (EditText) findViewById(R.id.txtCNPJCli);
        txtEnd = (EditText) findViewById(R.id.txtEndCli);
        txtRazao = (EditText) findViewById(R.id.txtRazaoCli);
        txtTel = (EditText) findViewById(R.id.txtTelCli);
        txtCel = (EditText) findViewById(R.id.txtCelCli);
        btnConcluir = (Button) findViewById(R.id.btnConcluirEdit);
        btnCancelar = (Button) findViewById(R.id.btnCancelar3);
        txtCNPJ.addTextChangedListener(MarcarasDeTexto.mask(txtCNPJ, MarcarasDeTexto.FORMAT_CNPJ));
        txtTel.addTextChangedListener(MarcarasDeTexto.mask(txtTel, MarcarasDeTexto.FORMAT_TEL));
        txtCel.addTextChangedListener(MarcarasDeTexto.mask(txtCel, MarcarasDeTexto.FORMAT_CEL));

        mydb = new DataBaseHelperCli(this);
        Intent intent2 = getIntent();
        Bundle bundleEmail = intent2.getExtras();
        Id = bundleEmail.getInt("Id");
        if (Id > 0) {
            id_To_Update = Id;
            Cursor rs = mydb.getData(Id);
            rs.moveToFirst();
            cliente = new Cliente();
            cliente.setNome(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_NAME)));
            cliente.setCNPJ(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_CNPJ)));
            cliente.setEndereco(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_END)));
            cliente.setRazaoSocial(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_RAZAO)));
            cliente.setTelefone(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_TEL)));
            cliente.setCelular(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_CEL)));
            if (!rs.isClosed()) {
                rs.close();
            }
            txtNome.setText(cliente.getNome());
            txtCNPJ.setText(cliente.getCNPJ());
            txtEnd.setText(cliente.getEndereco());
            txtRazao.setText(cliente.getRazaoSocial());
            txtTel.setText(cliente.getTelefone());
            txtCel.setText(cliente.getCelular());
            txtNome.setEnabled(false);
            txtCNPJ.setEnabled(false);
            txtEnd.setEnabled(false);
            txtRazao.setEnabled(false);
            txtTel.setEnabled(false);
            txtCel.setEnabled(false);
            btnConcluir.setClickable(false);
            btnConcluir.setVisibility(View.GONE);
            btnCancelar.setClickable(false);
            btnCancelar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.mnExcluiPed:
                txtNome.setEnabled(true);
                txtCNPJ.setEnabled(true);
                txtEnd.setEnabled(true);
                txtRazao.setEnabled(true);
                txtTel.setEnabled(true);
                txtCel.setEnabled(true);
                btnConcluir.setClickable(true);
                btnConcluir.setVisibility(View.VISIBLE);
                btnCancelar.setClickable(true);
                btnCancelar.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void OnClickEditar(View v) {
        nome = txtNome.getText().toString();
        cnpj = txtCNPJ.getText().toString();
        end = txtEnd.getText().toString();
        razao = txtRazao.getText().toString();
        tel = txtTel.getText().toString();
        cel = txtCel.getText().toString();
        if (nome.equals("")) {
            txtNome.setError("O nome é obrigatorio!");
        } else {
            if (cnpj.equals("")) {
                txtCNPJ.setError("O CNPJ é obrigatorio!");
            } else {
                if (end.equals("")) {
                    txtEnd.setError("O endereço é obrigatorio!");
                } else {
                    if (razao.equals("")) {
                        txtRazao.setError("A Razão social é obrigatoria!");
                    } else {
                        if (tel.equals("")) {
                            txtTel.setError("O numero de telefone é obrigatorio!");
                        } else {
                            if (cel.equals("")) {
                                txtCel.setError("O numero de celular é obrigatorio!");
                            } else {
                                if (mydb.updateCli(new Cliente(id_To_Update, nome, cnpj, end, razao, tel, cel))) {
                                    Toast.makeText(getApplicationContext(), "Salvo atualizado com sucesso",
                                            Toast.LENGTH_SHORT).show();
                                    txtNome.setEnabled(false);
                                    txtCNPJ.setEnabled(false);
                                    txtEnd.setEnabled(false);
                                    txtRazao.setEnabled(false);
                                    txtTel.setEnabled(false);
                                    txtCel.setEnabled(false);
                                    btnConcluir.setClickable(false);
                                    btnConcluir.setVisibility(View.GONE);
                                    btnCancelar.setClickable(false);
                                    btnCancelar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(getApplicationContext(), "falha na atualização",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void OnClickCancelar(View v){
        txtNome.setText(cliente.getNome());
        txtCNPJ.setText(cliente.getCNPJ());
        txtEnd.setText(cliente.getEndereco());
        txtRazao.setText(cliente.getRazaoSocial());
        txtTel.setText(cliente.getTelefone());
        txtCel.setText(cliente.getCelular());
        txtNome.setEnabled(false);
        txtCNPJ.setEnabled(false);
        txtEnd.setEnabled(false);
        txtRazao.setEnabled(false);
        txtTel.setEnabled(false);
        txtCel.setEnabled(false);
        btnConcluir.setClickable(false);
        btnConcluir.setVisibility(View.GONE);
        btnCancelar.setClickable(false);
        btnCancelar.setVisibility(View.GONE);
    }
}