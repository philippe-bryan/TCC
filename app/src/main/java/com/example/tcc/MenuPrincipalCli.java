package com.example.tcc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tcc.DataBasehelpers.DataBaseHelperCli;
import com.example.tcc.Models.Cliente;
import com.google.android.material.navigation.NavigationView;

public class MenuPrincipalCli extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DataBaseHelperCli mydb;
    String Email;
    Cliente cliente;
    TextView txtUsuario, txtEmailUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_cli);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_produto, R.id.nav_carrinho, R.id.nav_pedidos, R.id.nav_configuracao)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);

        txtUsuario = (TextView) headerView.findViewById(R.id.txtUsuario);
        txtEmailUsu = (TextView) headerView.findViewById(R.id.txtEmailUsu);

        mydb = new DataBaseHelperCli(this);

        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(prefs != null) {
            Email = prefs.getString("Email", null);
            cliente = new Cliente();
            if (Email != null) {
                Cursor rs = mydb.getName(Email);
                rs.moveToFirst();
                cliente = new Cliente();
                cliente.setNome(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_NAME)));
                cliente.setEmail(rs.getString(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_EMAIL)));
                cliente.setIdCli(rs.getInt(rs.getColumnIndex(DataBaseHelperCli.CLI_COLUMN_ID)));
                if (!rs.isClosed()) {
                    rs.close();
                }
                txtUsuario.setText(cliente.getNome());
                txtEmailUsu.setText(cliente.getEmail());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void OnClickPerfil(View v){
        Intent intent = new Intent(getApplication(), PerfilCli.class);
        intent.putExtra("Id", cliente.getIdCli());
        startActivity(intent);
    }
}