package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcc.Models.Cliente;
import com.example.tcc.Models.JavaMailAPI;

import java.util.ArrayList;

public class ConfirmCod extends AppCompatActivity {

    String queryString, queryString2;
    EditText txtConfCod;
    Button btnReenvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_cod);
        Intent intent2 = getIntent();
        Bundle bundleID = intent2.getExtras();
        queryString = bundleID.getString("Email");
        queryString2 = bundleID.getString("Codigo");
        txtConfCod = findViewById(R.id.txtConfCod);
        btnReenvio = (Button) findViewById(R.id.btnReenvio);
        btnReenvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    public void btnConfirmOnClick(View v) {
        String cod = "["+txtConfCod.getText().toString()+"]";

        if (cod.equals(queryString2)) {
            String intentEmail = queryString;
            Intent intent = new Intent(this, NovaSenha.class);
            Bundle bundleEmail = new Bundle();
            bundleEmail.putString("Email", String.valueOf(intentEmail));
            intent.putExtras(bundleEmail);
            startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Codigo incorreto",
                        Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMail() {
        String mail = queryString;
        String message = "Seu codigo de redefinição é:"+queryString2;
        String subject = "Codigo de redefinição";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
    }
}