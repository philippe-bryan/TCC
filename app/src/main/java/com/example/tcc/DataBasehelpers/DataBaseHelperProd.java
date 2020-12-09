package com.example.tcc.DataBasehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tcc.Models.Produtos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperProd extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.dbFazendaProdProd";
    public static final String PROD_TABLE_NAME = "produtos";
    public static final String PROD_COLUMN_ID = "Id_Prod";
    public static final String PROD_COLUMN_NAME = "Nome_Produc";
    public static final String PROD_COLUMN_DESC = "Descricao_Produc";
    public static final String PROD_COLUMN_TIPO = "Tipo_Ani_Produc";
    public static final String PROD_COLUMN_VAL = "Validade_Produc";
    public static final String PROD_COLUMN_QTD = "Quant_Produc";
    public static final String PROD_COLUMN_PRECO = "Valor_Produc";
    public static final String PROD_COLUMN_IMG = "Imagem_Prod";
    public static final String PROD_COLUMN_ID_CLI = "Id_Cli";
    public static final String PROD_COLUMN_ID_COMP = "Id_Compra";

    public DataBaseHelperProd(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase dbFazendaProd) {
        // TODO Auto-generated method stub
        dbFazendaProd.execSQL(
                "create table produtos " +
                        "(Id_Prod integer primary key AUTOINCREMENT, Nome_Produc text, Descricao_Produc text, Tipo_Ani_Produc text, " +
                        "Validade_Produc text, Quant_Produc integer, Valor_Produc text, Imagem_Prod integer, Id_Cli integer, Id_Compra integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbFazendaProd, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        dbFazendaProd.execSQL("DROP TABLE IF EXISTS produtos");
        onCreate(dbFazendaProd);
    }

    public boolean insertProd (Produtos p) {
        SQLiteDatabase dbFazendaProd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROD_COLUMN_NAME, p.getNome_Produc());
        contentValues.put(PROD_COLUMN_DESC, p.getDescricao_Produc());
        contentValues.put(PROD_COLUMN_TIPO, p.getTipo_Ani_Produc());
        contentValues.put(PROD_COLUMN_VAL, p.getValidade_Produc());
        contentValues.put(PROD_COLUMN_QTD, p.getQuant_Produc());
        contentValues.put(PROD_COLUMN_PRECO, p.getValor_Produc());
        contentValues.put(PROD_COLUMN_IMG, p.getImagem_Prod());
        contentValues.put(PROD_COLUMN_ID_CLI, p.getId_Cli());
        contentValues.put(PROD_COLUMN_ID_COMP, p.getId_Compra());
        dbFazendaProd.insert(PROD_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int Id_Prod) {
        SQLiteDatabase dbFazendaProd = this.getReadableDatabase();
        Cursor res =  dbFazendaProd.rawQuery( "select * from produtos where Id_Prod="+Id_Prod+"", null );
        return res;
    }

    public Cursor getList() {
        SQLiteDatabase dbFazendaProd = this.getReadableDatabase();
        Cursor res =  dbFazendaProd.rawQuery( "select * from produtos", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase dbFazendaProd = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(dbFazendaProd, PROD_TABLE_NAME);
        return numRows;
    }

    public boolean updateProd (Produtos p) {
        SQLiteDatabase dbFazendaProd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROD_COLUMN_NAME, p.getNome_Produc());
        contentValues.put(PROD_COLUMN_DESC, p.getDescricao_Produc());
        contentValues.put(PROD_COLUMN_TIPO, p.getTipo_Ani_Produc());
        contentValues.put(PROD_COLUMN_VAL, p.getValidade_Produc().toString());
        contentValues.put(PROD_COLUMN_QTD, p.getQuant_Produc());
        contentValues.put(PROD_COLUMN_PRECO, p.getValor_Produc());
        contentValues.put(PROD_COLUMN_ID_CLI, p.getId_Cli());
        contentValues.put(PROD_COLUMN_ID_COMP, p.getId_Compra());
        dbFazendaProd.update(PROD_TABLE_NAME, contentValues, "Id_Prod = ? ", new String[] { Integer.toString(p.getId_Prod()) } );
        return true;
    }

    public boolean updateIdComp (Produtos p) {
        SQLiteDatabase dbFazendaProd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROD_COLUMN_ID_COMP, p.getId_Compra());
        dbFazendaProd.update(PROD_TABLE_NAME, contentValues, "Id_Cli = ? and Id_Compra = 0", new String[] { Integer.toString(p.getId_Cli()) } );
        return true;
    }

    public Integer deleteProd (Integer Id_Prod) {
        SQLiteDatabase dbFazendaProd = this.getWritableDatabase();
        return dbFazendaProd.delete(PROD_TABLE_NAME,
                "Id_Prod = ?",
                new String[] { Integer.toString(Id_Prod) });
    }

    public Integer deleteAll () {
        SQLiteDatabase dbFazendaProd = this.getWritableDatabase();
        return dbFazendaProd.delete(PROD_TABLE_NAME,
                null,
                null);
    }

    public ArrayList<String> getAllProd() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase dbFazendaProd = this.getReadableDatabase();
        Cursor res =  dbFazendaProd.rawQuery( "select * from produtos", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(PROD_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Produtos> getProdList() throws ParseException {
        ArrayList<Produtos> lista = new ArrayList<Produtos>() ;

        SQLiteDatabase dbFazendaProd = this.getReadableDatabase();
        Cursor res =  dbFazendaProd.rawQuery( "select * from produtos", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Produtos p = new Produtos();
            p.setNome_Produc(res.getString(res.getColumnIndex(PROD_COLUMN_NAME)));
            p.setId_Prod(Integer.parseInt(res.getString(res.getColumnIndex(PROD_COLUMN_ID))));
            p.setDescricao_Produc(res.getString(res.getColumnIndex(PROD_COLUMN_DESC)));
            p.setTipo_Ani_Produc(res.getString(res.getColumnIndex(PROD_COLUMN_TIPO)));
            p.setValidade_Produc(res.getString(res.getColumnIndex(PROD_COLUMN_VAL)));
            p.setQuant_Produc(Integer.parseInt(res.getString(res.getColumnIndex(PROD_COLUMN_QTD))));
            p.setValor_Produc(res.getString(res.getColumnIndex(PROD_COLUMN_PRECO)));
            p.setImagem_Prod(Integer.parseInt(res.getString(res.getColumnIndex(PROD_COLUMN_IMG))));
            p.setId_Cli(Integer.parseInt(res.getString(res.getColumnIndex(PROD_COLUMN_ID_CLI))));
            p.setId_Compra(Integer.parseInt(res.getString(res.getColumnIndex(PROD_COLUMN_ID_COMP))));
            lista.add(p);

            res.moveToNext();
        }
        return lista;
    }

    public List<Produtos> getAll(int id_cli, int id_compra){
        List<Produtos> produtos = new ArrayList<>();
        SQLiteDatabase dbFazendaProd = this.getReadableDatabase();
        Cursor cursor = dbFazendaProd.rawQuery("select * from produtos where Id_Cli="+id_cli+" and Id_Compra="+id_compra+"", null);
        while(cursor.moveToNext()){
            int Id = cursor.getInt(cursor.getColumnIndex(PROD_COLUMN_ID));
            String Nome = cursor.getString(cursor.getColumnIndex(PROD_COLUMN_NAME));
            String Desc = cursor.getString(cursor.getColumnIndex(PROD_COLUMN_DESC));
            String Tipo = cursor.getString(cursor.getColumnIndex(PROD_COLUMN_TIPO));
            String Validad = cursor.getString(cursor.getColumnIndex(PROD_COLUMN_VAL));
            int qtd = Integer.parseInt(cursor.getString(cursor.getColumnIndex(PROD_COLUMN_QTD)));
            String preco = cursor.getString(cursor.getColumnIndex(PROD_COLUMN_PRECO));
            int imagem = Integer.parseInt(cursor.getString(cursor.getColumnIndex(PROD_COLUMN_IMG)));
            int Id_cli = Integer.parseInt(cursor.getString(cursor.getColumnIndex(PROD_COLUMN_ID_CLI)));
            int Id_compra = Integer.parseInt(cursor.getString(cursor.getColumnIndex(PROD_COLUMN_ID_COMP)));
            produtos.add(new Produtos(Id, Nome, Desc, Tipo, Validad, qtd, preco, imagem, Id_cli, Id_compra));
        }
        cursor.close();
        return produtos;
    }
}
