package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.Models.Cliente;

public class NovaSenha extends AppCompatActivity {

    EditText txtNovaSenha, txtConfNovaSenha;
    String senha, confSenha, email;
    private DataBaseHelperCli mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_senha);

        txtNovaSenha = findViewById(R.id.txtNovaSenha);
        txtConfNovaSenha = findViewById(R.id.txtConfNovaSenha);
        Intent intent2 = getIntent();
        Bundle bundleID = intent2.getExtras();
        email = bundleID.getString("Email");
        mydb = new DataBaseHelperCli(this);
    }

    public void btnCocluirOnClick(View v) {
        senha = txtNovaSenha.getText().toString();
        confSenha = txtConfNovaSenha.getText().toString();
        if (senha.length() < 6) {
            txtNovaSenha.setError("A senha tem que ter 6 ou mais caracteres!");
        } else {
            if (senha.equals(confSenha)) {
                if (mydb.updateSenha(new Cliente(email, senha))) {
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