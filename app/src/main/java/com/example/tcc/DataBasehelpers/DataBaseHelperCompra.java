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

    public Cursor getId() {
        SQLiteDatabase dbFazendaCompra = this.getReadableDatabase();
        Cursor res =  dbFazendaCompra.rawQuery( "select * from compra order by Id_Compra desc limit 1", null );
        return res;
    }

    public Integer deleteAll () {
        SQLiteDatabase dbFazendaCompra = this.getWritableDatabase();
        return dbFazendaCompra.delete(COMP_TABLE_NAME,
                null,
                null);
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
