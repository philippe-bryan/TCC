package com.example.tcc.DataBasehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tcc.Models.Cliente;

import java.text.ParseException;
import java.util.ArrayList;

public class DataBaseHelperCli extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.dbFazenda";
    public static final String CLI_TABLE_NAME = "cliente";
    public static final String CLI_COLUMN_ID = "Id_Cliente";
    public static final String CLI_COLUMN_NAME = "Nome_Cli";
    public static final String CLI_COLUMN_CNPJ = "CNPJ_Cli";
    public static final String CLI_COLUMN_CEL = "Cel_Cli";
    public static final String CLI_COLUMN_TEL = "Tel_Cli";
    public static final String CLI_COLUMN_END = "End_Cli";
    public static final String CLI_COLUMN_RAZAO = "Razao_Social_Cli";
    public static final String CLI_COLUMN_SENHA = "Senha_Cli";
    public static final String CLI_COLUMN_EMAIL = "Email_Cli";

    public DataBaseHelperCli(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase dbFazenda) {
        dbFazenda.execSQL(
                "create table cliente " +
                        "(Id_Cliente integer primary key AUTOINCREMENT, Nome_Cli text,CNPJ_Cli integer, Cel_Cli interger, Tel_Cli interger, End_Cli text, Razao_Social_Cli text, Senha_Cli text, Email_Cli text unique)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbFazenda, int oldVersion, int newVersion) {
        dbFazenda.execSQL("DROP TABLE IF EXISTS cliente");
        onCreate(dbFazenda);
    }

    public boolean insertCli (Cliente c) {
        SQLiteDatabase dbFazenda = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLI_COLUMN_NAME, c.getNome());
        contentValues.put(CLI_COLUMN_CNPJ, c.getCNPJ());
        contentValues.put(CLI_COLUMN_CEL, c.getCelular());
        contentValues.put(CLI_COLUMN_TEL, c.getTelefone());
        contentValues.put(CLI_COLUMN_END, c.getEndereco());
        contentValues.put(CLI_COLUMN_RAZAO, c.getRazaoSocial());
        contentValues.put(CLI_COLUMN_SENHA, c.getSenha());
        contentValues.put(CLI_COLUMN_EMAIL, c.getEmail());
        dbFazenda.insert(CLI_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int Id_Cliente) {
        SQLiteDatabase dbFazenda = this.getReadableDatabase();
        Cursor res =  dbFazenda.rawQuery( "select * from cliente where Id_Cliente="+Id_Cliente+"", null );
        return res;
    }

    public Boolean getEmail(String Email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from cliente where Email_Cli = ?", new String[] {Email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean getLogin(String Email, String Senha){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from cliente where Email_Cli = ? and Senha_Cli = ?", new String[] {Email,Senha});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor getName(String Email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from cliente where Email_Cli = ?", new String[] {Email} );
        return cursor;
    }

    public int numberOfRows(){
        SQLiteDatabase dbFazenda = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(dbFazenda, CLI_TABLE_NAME);
        return numRows;
    }

    public boolean updateSenha (Cliente c) {
        SQLiteDatabase dbFazenda = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLI_COLUMN_SENHA, c.getSenha());
        dbFazenda.update(CLI_TABLE_NAME, contentValues, "Email_Cli = ? ", new String[] { c.getEmail() } );
        return true;
    }

    public boolean updateEmail (Cliente c) {
        SQLiteDatabase dbFazenda = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLI_COLUMN_EMAIL, c.getEmail());
        dbFazenda.update(CLI_TABLE_NAME, contentValues, "Id_Cliente = ? ", new String[] { Integer.toString(c.getIdCli()) } );
        return true;
    }

    public boolean updateCli (Cliente c) {
        SQLiteDatabase dbFazenda = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLI_COLUMN_NAME, c.getNome());
        contentValues.put(CLI_COLUMN_CNPJ, c.getCNPJ());
        contentValues.put(CLI_COLUMN_CEL, c.getCelular());
        contentValues.put(CLI_COLUMN_TEL, c.getTelefone());
        contentValues.put(CLI_COLUMN_END, c.getEndereco());
        contentValues.put(CLI_COLUMN_RAZAO, c.getRazaoSocial());
        dbFazenda.update(CLI_TABLE_NAME, contentValues, "Id_Cliente = ? ", new String[] { Integer.toString(c.getIdCli()) } );
        return true;
    }

    public boolean updateConta (Cliente c) {
        SQLiteDatabase dbFazenda = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLI_COLUMN_SENHA, c.getSenha());
        contentValues.put(CLI_COLUMN_EMAIL, c.getEmail());
        dbFazenda.update(CLI_TABLE_NAME, contentValues, "Id_Cliente = ? ", new String[] { Integer.toString(c.getIdCli()) } );
        return true;
    }

    public Integer deleteCli (Integer Id_Cliente) {
        SQLiteDatabase dbFazenda = this.getWritableDatabase();
        return dbFazenda.delete(CLI_TABLE_NAME,
                "Id_Cliente = ?",
                new String[] { Integer.toString(Id_Cliente) });
    }

    public Integer deleteAll () {
        SQLiteDatabase dbFazenda = this.getWritableDatabase();
        return dbFazenda.delete(CLI_TABLE_NAME,
                null,
                null);
    }

    public ArrayList<String> getAllCli() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase dbFazenda = this.getReadableDatabase();
        Cursor res =  dbFazenda.rawQuery( "select * from cliente", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CLI_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Cliente> getCliList() throws ParseException {
        ArrayList<Cliente> lista = new ArrayList<Cliente>() ;

        SQLiteDatabase dbFazenda = this.getReadableDatabase();
        Cursor res =  dbFazenda.rawQuery( "select * from cliente", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Cliente c = new Cliente();
            c.setNome(res.getString(res.getColumnIndex(CLI_COLUMN_NAME)));
            c.setIdCli(Integer.parseInt(res.getString(res.getColumnIndex(CLI_COLUMN_ID))));
            c.setCNPJ(res.getString(res.getColumnIndex(CLI_COLUMN_CNPJ)));
            c.setCelular(res.getString(res.getColumnIndex(CLI_COLUMN_CEL)));
            c.setTelefone(res.getString(res.getColumnIndex(CLI_COLUMN_TEL)));
            c.setEndereco(res.getString(res.getColumnIndex(CLI_COLUMN_END)));
            c.setRazaoSocial(res.getString(res.getColumnIndex(CLI_COLUMN_RAZAO)));
            c.setSenha(res.getString(res.getColumnIndex(CLI_COLUMN_SENHA)));
            c.setEmail(res.getString(res.getColumnIndex(CLI_COLUMN_EMAIL)));
            lista.add(c);

            res.moveToNext();
        }
        return lista;
    }
}
