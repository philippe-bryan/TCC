package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.Models.JavaMailAPI;

import java.util.ArrayList;
import java.util.Random;

public class EsqueciSenha extends AppCompatActivity {

    EditText txtEmail;
    Button btnEnvia;
    Bundle bundleCod;
    private DataBaseHelperCli mydb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        txtEmail = (EditText)findViewById(R.id.txtConfCod);
        btnEnvia = (Button) findViewById(R.id.btnEnvia);
        mydb = new DataBaseHelperCli(this);
        btnEnvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = txtEmail.getText().toString();
                Boolean checkuser = mydb.getEmail(Email);
                if(checkuser==false){
                    Toast.makeText(EsqueciSenha.this, "Este Email ainda não está cadastrado!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String intentEmail = txtEmail.getText().toString();
                    Intent intent = new Intent(getApplication(), ConfirmCod.class);
                    Bundle bundleEmail = new Bundle();
                    bundleEmail.putString("Email", String.valueOf(intentEmail));
                    sendMail();
                    intent.putExtras(bundleEmail);
                    intent.putExtras(bundleCod);
                    startActivity(intent);
                }
            }
        });
    }

    public static int getRandomInt(int min, int max) {
        Random random = new Random();

        return random.nextInt((max - min) + 1) + min;
    }

    public static ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min,
                                                                   int max) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        while (numbers.size() < size) {
            int random = getRandomInt(min, max);

            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }

        return numbers;
    }

    private void sendMail() {
        String mail = txtEmail.getText().toString().trim();
        String message;
        String subject = "Codigo de redefinição";
        ArrayList<Integer> list = getRandomNonRepeatingIntegers(1 , 10000, 99999);
        for (int i = 0; i < list.size(); i++) {
            list.get(i);
        }
        message = ("Seu codigo é:"+ list);
        //Send Mail
        bundleCod = new Bundle();
        bundleCod.putString("Codigo", String.valueOf(list));
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
    }
}