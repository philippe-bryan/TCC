package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.Login;

public class MainActivity extends AppCompatActivity {
    String Email, Senha;
    EditText txtLogin, pswSenha;
    Button btnEntrar;
    private DataBaseHelperCli mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLogin = (EditText)findViewById(R.id.txtLogin);
        pswSenha = (EditText)findViewById(R.id.pswSenha);
        btnEntrar = (Button)findViewById(R.id.btnEntrar);

        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
            if(prefs != null){
                Email = prefs.getString("Email", null);
                Senha = prefs.getString("Senha", null);
                if (Email != null && Senha != null) {
                    String intentEmail = Email;
                    Intent intent = new Intent(getApplication(), MenuPrincipalCli.class);
                    Bundle bundleEmail = new Bundle();
                    bundleEmail.putString("Email", intentEmail);
                    intent.putExtras(bundleEmail);
                    startActivity(intent);
                    finish();
                }
            }
        mydb = new DataBaseHelperCli(this);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Email = txtLogin.getText().toString();
                Senha = pswSenha.getText().toString();

                Boolean checkuser = mydb.getLogin(Email, Senha);
                if (checkuser == false) {
                    Toast.makeText(MainActivity.this, "Login ou senha invalidos", Toast.LENGTH_SHORT).show();
                } else {
                    SalvaLogin();
                    Intent intentLogar = new Intent(getApplication(), MenuPrincipalCli.class);
                    intentLogar.putExtra("Email", Email);
                    startActivity(intentLogar);
                    finish();
                }
            }
        });
    }

    public void SalvaLogin(){
        Login login = new Login();
        login.setEmail(txtLogin.getText().toString());
        login.setSenha(pswSenha.getText().toString());
        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("Email", login.getEmail());
        ed.putString("Senha", login.getSenha());
        ed.apply();
    }

    public void btnCadastrarOnClick(View v) {
        Intent intentCadastrar = new Intent(this, CadastrarCli.class);
        startActivity(intentCadastrar);
    }

    public void esqueciOnClick(View v) {
        Intent intentEsqueci = new Intent(this, EsqueciSenha.class);
        startActivity(intentEsqueci);
    }
}