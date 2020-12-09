package com.example.tcc.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tcc.MainActivity;
import com.example.tcc.Models.Login;
import com.example.tcc.MudarEmail;
import com.example.tcc.MudarSenha;
import com.example.tcc.R;


public class Configuracao extends Fragment {

    Button btnMudarEmail, btnMudarSenha, btnSair;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_configuracao, container, false);

        btnMudarEmail = root.findViewById(R.id.btnMudarEmail);
        btnMudarSenha = root.findViewById(R.id.btnMudarSenha);
        btnSair = root.findViewById(R.id.btnSair);

        btnMudarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MudarEmail.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnMudarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MudarSenha.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.Sair)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ApagarLogin();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), R.string.delete_cancel,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle(R.string.Sair);
                d.show();
            }
        });
        return root;
    }

    public void ApagarLogin(){
        SharedPreferences prefs = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("Email", null);
        ed.putString("Senha", null);
        ed.apply();
    }
}