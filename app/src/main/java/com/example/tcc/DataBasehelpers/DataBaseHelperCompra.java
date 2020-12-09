package com.example.tcc.DataBasehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tcc.Models.Compra;
import com.example.tcc.Models.Produtos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperCompra extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.dbFazendaCompra";
    public static final String COMP_TABLE_NAME = "compra";
    public static final String COMP_COLUMN_ID = "Id_Compra";
    public static final String COMP_COLUMN_DATA = "Data";
    public static final String COMP_COLUMN_PRECO = "Preco";
    public static final String COMP_COLUMN_ID_CLI = "Id_Cli";

    public DataBaseHelperCompra(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase dbFazendaCompra) {
        dbFazendaCompra.execSQL(
                "create table compra " +
                        "(Id_Compra integer primary key AUTOINCREMENT, Data text, Preco text, Id_Cli interger)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbFazendaCompra, int oldVersion, int newVersion) {
        dbFazendaCompra.execSQL("DROP TABLE IF EXISTS compra");
        onCreate(dbFazendaCompra);
    }

    public boolean insertComp (Compra c) {
        SQLiteDatabase dbFazendaCompra = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMP_COLUMN_DATA, c.getData());
        contentValues.put(COMP_COLUMN_PRECO, c.getPrecoCompra());
        contentValues.put(COMP_COLUMN_ID_CLI, c.getId_Cli());
        dbFazendaCompra.insert(COMP_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int Id_Compra) {
        SQLiteDatabase dbFazendaCompra = this.getReadableDatabase();
        Cursor res =  dbFazendaCompra.rawQuery( "select * from compra where Id_Compra="+Id_Compra+"", null );
        return res;
    }

    public Cursor getId() {
        SQLiteDatabase dbFazendaCompra = this.getReadableDatabase();
        Cursor res =  dbFazendaCompra.rawQuery( "select * from compra order by Id_Compra desc limit 1", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase dbFazendaCompra = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(dbFazendaCompra, COMP_TABLE_NAME);
        return numRows;
    }

    public boolean updateComp (Compra c) {
        SQLiteDatabase dbFazendaCompra = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMP_COLUMN_DATA, c.getData());
        contentValues.put(COMP_COLUMN_PRECO, c.getPrecoCompra());
        contentValues.put(COMP_COLUMN_ID_CLI, c.getId_Cli());
        dbFazendaCompra.update(COMP_TABLE_NAME, contentValues, "Id_Compra = ? ", new String[] { Integer.toString(c.getId_Compra()) } );
        return true;
    }

    public Integer deleteComp (Integer Id_Compra) {
        SQLiteDatabase dbFazendaCompra = this.getWritableDatabase();
        return dbFazendaCompra.delete(COMP_TABLE_NAME,
                "Id_Compra = ?",
                new String[] { Integer.toString(Id_Compra) });
    }

    public Integer deleteAll () {
        SQLiteDatabase dbFazendaCompra = this.getWritableDatabase();
        return dbFazendaCompra.delete(COMP_TABLE_NAME,
                null,
                null);
    }

    public ArrayList<String> getAllComp() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase dbFazendaCompra = this.getReadableDatabase();
        Cursor res =  dbFazendaCompra.rawQuery( "select * from compra", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COMP_COLUMN_DATA)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Compra> getCompList(int idCli) throws ParseException {
        ArrayList<Compra> lista = new ArrayList<Compra>() ;

        SQLiteDatabase dbFazendaCompra = this.getReadableDatabase();
        Cursor res =  dbFazendaCompra.rawQuery( "select * from compra where Id_Cli="+idCli+" order by Id_Compra desc", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Compra c = new Compra();
            c.setData(res.getString(res.getColumnIndex(COMP_COLUMN_DATA)));
            c.setId_Compra(Integer.parseInt(res.getString(res.getColumnIndex(COMP_COLUMN_ID))));
            c.setPrecoCompra(res.getString(res.getColumnIndex(COMP_COLUMN_PRECO)));
            c.setId_Cli(res.getInt(res.getColumnIndex(COMP_COLUMN_ID_CLI)));
            lista.add(c);
            res.moveToNext();
        }
        return lista;
    }

    public List<Compra> getAll(int idCli){
        List<Compra> compras = new ArrayList<>();
        SQLiteDatabase dbFazendaProd = this.getReadableDatabase();
        Cursor cursor = dbFazendaProd.rawQuery("select * from compra where Id_Cli="+idCli+" order by Id_Compra desc", null);
        while(cursor.moveToNext()){
            int Id = cursor.getInt(cursor.getColumnIndex(COMP_COLUMN_ID));
            String Data = cursor.getString(cursor.getColumnIndex(COMP_COLUMN_DATA));
            String Preco = cursor.getString(cursor.getColumnIndex(COMP_COLUMN_PRECO));
            int IdCli = cursor.getInt(cursor.getColumnIndex(COMP_COLUMN_ID_CLI));
            compras.add(new Compra(Id, Data, Preco, IdCli));
        }
        cursor.close();
        return compras;
    }
}
