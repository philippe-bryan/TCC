package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.MarcarasDeTexto;

public class CadastrarCli extends AppCompatActivity {

    private DataBaseHelperCli mydb;
    EditText txtNome, txtCNPJ, txtEnd, txtRazao, txtTel,
            txtCel, txtLogin2, txtSenha, txtConfSenha;

    String nome, cnpj, end, razao, tel, cel, login, senha, confSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cli);

        txtNome = (EditText)findViewById(R.id.txtNome);
        txtCNPJ = (EditText)findViewById(R.id.txtCNPJ);
        txtEnd = (EditText)findViewById(R.id.txtEnd);
        txtRazao = (EditText)findViewById(R.id.txtRazao);
        txtTel = (EditText)findViewById(R.id.txtTel);
        txtCel = (EditText)findViewById(R.id.txtCel);
        txtLogin2 = (EditText)findViewById(R.id.txtLogin2);
        txtSenha = (EditText)findViewById(R.id.txtSenha);
        txtConfSenha = (EditText)findViewById(R.id.txtConfSenha);

        txtCNPJ.addTextChangedListener(MarcarasDeTexto.mask(txtCNPJ, MarcarasDeTexto.FORMAT_CNPJ));
        txtTel.addTextChangedListener(MarcarasDeTexto.mask(txtTel, MarcarasDeTexto.FORMAT_TEL));
        txtCel.addTextChangedListener(MarcarasDeTexto.mask(txtCel, MarcarasDeTexto.FORMAT_CEL));

        mydb = new DataBaseHelperCli(this);
    }

    public void btnCocluirOnClick(View v) {
        nome = txtNome.getText().toString();
        cnpj = txtCNPJ.getText().toString();
        end = txtEnd.getText().toString();
        razao = txtRazao.getText().toString();
        tel = txtTel.getText().toString();
        cel = txtCel.getText().toString();
        login = txtLogin2.getText().toString();
        senha = txtSenha.getText().toString();
        confSenha = txtConfSenha.getText().toString();

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
                                if (login.equals("")) {
                                    txtLogin2.setError("O email é obrigatorio!");
                                } else {
                                    Boolean checkuser = mydb.getEmail(login);
                                    if (checkuser == true) {
                                        txtLogin2.setError("Este Email já está cadastrado!");
                                    } else {
                                        if (senha.equals("")) {
                                            txtSenha.setError("A senha é obrigatoria!");
                                        } else {
                                            if (senha.length() < 6) {
                                                txtSenha.setError("A senha tem que ter 6 ou mais caracteres!");
                                            } else {
                                                if (confSenha.equals("")) {
                                                    txtConfSenha.setError("A confirmação da senha é obrigatoria!");
                                                } else {
                                                    if (senha.equals(confSenha)) {
                                                        if (mydb.insertCli(new Cliente(nome, cnpj, end, razao, tel, cel, login, senha))) {
                                                            Toast.makeText(getApplicationContext(), "Salvo com sucesso",
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent intentConcluir = new Intent(this, MainActivity.class);
                                                            startActivity(intentConcluir);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "falha no salvamento",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        txtConfSenha.setError("A confirmação da senha está errada!");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void btnCancelarOnClick(View v) {
        Intent intentCancelar = new Intent(this, MainActivity.class);
        startActivity(intentCancelar);
    }
}