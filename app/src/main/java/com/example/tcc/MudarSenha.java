package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.Models.Cliente;

public class MudarSenha extends AppCompatActivity {

    EditText txtNovaSenha, txtConfNovaSenha;
    String senha, confSenha, email;
    private DataBaseHelperCli mydb;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mudar_senha);
        txtNovaSenha = findViewById(R.id.txtNovaSenha2);
        txtConfNovaSenha = findViewById(R.id.txtConfNovaSenha2);
        mydb = new DataBaseHelperCli(this);

        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(prefs != null) {
            email = prefs.getString("Email", null);
            mydb = new DataBaseHelperCli(this);
            cliente = new Cliente();
            if (email != null) {
                Cursor rs = mydb.getName(email);
                rs.moveToFirst();
                cliente.setEmail(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_EMAIL)));
                if (!rs.isClosed()) {
                    rs.close();
                }
            }
        }
    }

    public void btnCocluirOnClick(View v) {
        senha = txtNovaSenha.getText().toString();
        confSenha = txtConfNovaSenha.getText().toString();
        if (senha.length() < 6) {
            txtNovaSenha.setError("A senha tem que ter 6 ou mais caracteres!");
        } else {
            if (senha.equals(confSenha)) {
                if (mydb.updateSenha(new Cliente(cliente.getEmail(), senha))) {
                    Toast.makeText(getApplicationContext(), "Alterada com sucesso",
                            Toast.LENGTH_SHORT).show();
                    Intent intentConcluir = new Intent(this, MainActivity.class);
                    startActivity(intentConcluir);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocorreu um erro",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                txtConfNovaSenha.setError("A confirmação da senha está errada!");
            }
        }
    }
}