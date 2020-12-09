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
import com.example.tcc.Models.Login;

public class MudarEmail extends AppCompatActivity {

    EditText txtNovoEmail;
    String email, novoEmail;
    DataBaseHelperCli mydb;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mudar_email);

        txtNovoEmail = findViewById(R.id.txtNovoEmail);
        mydb = new DataBaseHelperCli(this);

        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(prefs != null) {
            email = prefs.getString("Email", null);
            mydb = new DataBaseHelperCli(this);
            cliente = new Cliente();
            if(email != null) {
                Cursor rs = mydb.getName(email);
                rs.moveToFirst();
                cliente.setEmail(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_EMAIL)));
                cliente.setIdCli(rs.getInt(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_ID)));
                cliente.setSenha(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_SENHA)));
                if (!rs.isClosed()) {
                    rs.close();
                }
            }
        }
    }

    public void btnCocluirOnClick(View v) {
        novoEmail = txtNovoEmail.getText().toString();
        if (novoEmail.equals("")) {
            txtNovoEmail.setError("Digite o novo email!");
        } else {
            if (mydb.updateEmail(new Cliente(novoEmail, cliente.getIdCli()))) {
                Toast.makeText(getApplicationContext(), "Alterada com sucesso", Toast.LENGTH_SHORT).show();
                SalvaLogin();
                Intent intentLogar = new Intent(getApplication(), MenuPrincipalCli.class);
                intentLogar.putExtra("Email", novoEmail);
                startActivity(intentLogar);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void SalvaLogin(){
        Login login = new Login();
        login.setEmail(novoEmail);
        login.setSenha(cliente.getSenha());
        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("Email", login.getEmail());
        ed.putString("Senha", login.getSenha());
        ed.apply();
    }
}