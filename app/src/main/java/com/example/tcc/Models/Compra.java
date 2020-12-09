package com.example.tcc.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Compra {

    private int id_Compra;
    private String data;
    private String precoCompra;
    private int id_Cli;

    public Compra(){   }
    public Compra(int id_Compra, String data, String precoCompra, int id_Cli) {
        this.id_Compra = id_Compra;
        this.data = data;
        this.precoCompra = precoCompra;
        this.id_Cli = id_Cli;
    }

    public Compra(String data, String precoCompra, int id_Cli) {
        this.data = data;
        this.precoCompra = precoCompra;
        this.id_Cli = id_Cli;
    }
    
    public int getId_Compra() {
        return id_Compra;
    }

    public void setId_Compra(int id_Compra) {
        this.id_Compra = id_Compra;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(String precoCompra) {
        this.precoCompra = precoCompra;
    }

    public int getId_Cli() {
        return id_Cli;
    }

    public void setId_Cli(int id_Cli) {
        this.id_Cli = id_Cli;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Id_Compra", this.id_Compra);
            obj.put("Data", this.data);
            obj.put("Preco", this.precoCompra);
            obj.put("Id_Cli", this.id_Cli);
        } catch (JSONException e) {
            //trace("DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }
}
