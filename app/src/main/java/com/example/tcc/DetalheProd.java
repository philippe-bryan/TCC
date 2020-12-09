package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
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
import com.example.tcc.R;

public class DetalheProd extends AppCompatActivity {

    TextView lblNomeProd, lblDesc, lblDataValid, lblQtd, lblPreco;
    ImageView imgProd;
    DataBaseHelperProd mydb;
    DataBaseHelperCli mydbCli;
    String nome, desc, valid, preco, tipoAni, Email;
    int qtd, imagem, idCli;
    Cliente cliente;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_prod);

        lblNomeProd = (TextView) findViewById(R.id.lblNomeProd);
        lblDesc = (TextView) findViewById(R.id.lblDesc);
        lblDataValid = (TextView) findViewById(R.id.lblDataValid);
        lblQtd = (TextView) findViewById(R.id.lblQtd);
        lblPreco = (TextView) findViewById(R.id.lblPreco);
        imgProd = (ImageView) findViewById(R.id.imgProd);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        Intent intent = getIntent();
        nome = intent.getExtras().getString("Nome");
        desc = intent.getExtras().getString("Desc");
        valid = intent.getExtras().getString("valid");
        qtd = intent.getExtras().getInt("qtd");
        preco = intent.getExtras().getString("Preco");
        imagem = intent.getExtras().getInt("Imagem");
        tipoAni = intent.getExtras().getString("tipoAni");

        String Spreco = "R$"+preco;
        String valida = "Data de validade:"+valid;
        String Sqtd = Integer.toString(qtd);

        lblNomeProd.setText(nome);
        lblDesc.setText(desc);
        lblDataValid.setText(valida);
        lblQtd.setText("Quantidade disponivel: "+Sqtd);
        lblPreco.setText(Spreco);
        imgProd.setImageResource(imagem);

        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(prefs != null) {
            Email = prefs.getString("Email", null);
            mydbCli = new DataBaseHelperCli(this);
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

        mydb = new DataBaseHelperProd(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                if (mydb.insertProd(new Produtos(nome, desc, tipoAni, valid, qtd, preco, imagem, idCli, 0))) {
                    Toast.makeText(getApplicationContext(), "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    String intentEmail = Email;
                    Intent intent = new Intent(getApplication(), MenuPrincipalCli.class);
                    Bundle bundleEmail = new Bundle();
                    bundleEmail.putString("Email", intentEmail);
                    intent.putExtras(bundleEmail);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "falha",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
