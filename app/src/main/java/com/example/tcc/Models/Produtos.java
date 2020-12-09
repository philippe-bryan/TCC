package com.example.tcc.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Produtos {

    private int Id_Prod;
    private String Nome_Produc;
    private String Descricao_Produc;
    private String Tipo_Ani_Produc;
    private String Validade_Produc;
    private int Quant_Produc;
    private String Valor_Produc;
    private int Imagem_Prod;
    private int Id_Cli;
    private int Id_Compra;

    public Produtos(){   }

    public Produtos(String Nome_Produc, String Descricao_Produc, String Tipo_Ani_Produc,
                    String Validade_Produc, int Quant_Produc, String Valor_Produc, int Id_Cli, int Id_Compra) {
        this.Nome_Produc = Nome_Produc;
        this.Descricao_Produc = Descricao_Produc;
        this.Tipo_Ani_Produc = Tipo_Ani_Produc;
        this.Validade_Produc = Validade_Produc;
        this.Quant_Produc = Quant_Produc;
        this.Valor_Produc = Valor_Produc;
        this.Id_Cli = Id_Cli;
        this.Id_Compra = Id_Compra;
    }

    public Produtos(String Nome_Produc, String Descricao_Produc, String Tipo_Ani_Produc,
                    String Validade_Produc, int Quant_Produc, String Valor_Produc, int Imagem_Prod, int Id_Cli, int Id_Compra) {
        this.Nome_Produc = Nome_Produc;
        this.Descricao_Produc = Descricao_Produc;
        this.Tipo_Ani_Produc = Tipo_Ani_Produc;
        this.Validade_Produc = Validade_Produc;
        this.Quant_Produc = Quant_Produc;
        this.Valor_Produc = Valor_Produc;
        this.Imagem_Prod = Imagem_Prod;
        this.Id_Cli = Id_Cli;
        this.Id_Compra = Id_Compra;
    }

    public Produtos(int Id_Prod, String Nome_Produc, String Descricao_Produc, String Tipo_Ani_Produc,
                    String Validade_Produc, int Quant_Produc, String Valor_Produc, int Id_Cli, int Id_Compra) {
        this.Id_Prod = Id_Prod;
        this.Nome_Produc = Nome_Produc;
        this.Descricao_Produc = Descricao_Produc;
        this.Tipo_Ani_Produc = Tipo_Ani_Produc;
        this.Validade_Produc = Validade_Produc;
        this.Quant_Produc = Quant_Produc;
        this.Valor_Produc = Valor_Produc;
        this.Id_Cli = Id_Cli;
        this.Id_Compra = Id_Compra;
    }

    public Produtos(int Id_Prod, String Nome_Produc, String Descricao_Produc, String Tipo_Ani_Produc,
                    String Validade_Produc, int Quant_Produc, String Valor_Produc, int Imagem_Prod, int Id_Cli, int Id_Compra) {
        this.Id_Prod = Id_Prod;
        this.Nome_Produc = Nome_Produc;
        this.Descricao_Produc = Descricao_Produc;
        this.Tipo_Ani_Produc = Tipo_Ani_Produc;
        this.Validade_Produc = Validade_Produc;
        this.Quant_Produc = Quant_Produc;
        this.Valor_Produc = Valor_Produc;
        this.Imagem_Prod = Imagem_Prod;
        this.Imagem_Prod = Imagem_Prod;
        this.Id_Cli = Id_Cli;
        this.Id_Compra = Id_Compra;
    }

    public Produtos(int Id_Cli, int Id_Compra) {
        this.Id_Cli = Id_Cli;
        this.Id_Compra = Id_Compra;
    }

    public Produtos(String Nome_Produc, String Valor_Produc, int Imagem_Prod) {
        this.Nome_Produc = Nome_Produc;
        this.Valor_Produc = Valor_Produc;
        this.Imagem_Prod = Imagem_Prod;
    }

    //Getter
    public int getId_Prod() {
        return Id_Prod;
    }

    public String getNome_Produc() {
        return Nome_Produc;
    }

    public String getDescricao_Produc() {
        return Descricao_Produc;
    }

    public String getTipo_Ani_Produc() {
        return Tipo_Ani_Produc;
    }

    public String getValidade_Produc() {
        return Validade_Produc;
    }

    public int getQuant_Produc() {
        return Quant_Produc;
    }

    public String getValor_Produc() {
        return Valor_Produc;
    }

    public int getImagem_Prod() {
        return Imagem_Prod;
    }

    public int getId_Cli() {
        return Id_Cli;
    }

    public int getId_Compra() {
        return Id_Compra;
    }

    //Setter
    public void setId_Prod(int id_Prod) {
        Id_Prod = id_Prod;
    }

    public void setNome_Produc(String nome_Produc) {
        Nome_Produc = nome_Produc;
    }

    public void setDescricao_Produc(String descricao_Produc) { Descricao_Produc = descricao_Produc; }

    public void setTipo_Ani_Produc(String tipo_Ani_Produc) {
        Tipo_Ani_Produc = tipo_Ani_Produc;
    }

    public void setValidade_Produc(String validade_Produc) {
        Validade_Produc = validade_Produc;
    }

    public void setQuant_Produc(int quant_Produc) {
        Quant_Produc = quant_Produc;
    }

    public void setValor_Produc(String valor_Produc) {
        Valor_Produc = valor_Produc;
    }

    public void setImagem_Prod(int imagem_Prod) { Imagem_Prod = imagem_Prod; }

    public void setId_Cli(int id_Cli) {
        Id_Cli = id_Cli;
    }

    public void setId_Compra(int id_Compra) {
        Id_Compra = id_Compra;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Id_Prod", this.Id_Prod);
            obj.put("Nome_Produc", this.Nome_Produc);
            obj.put("Descricao_Produc", this.Descricao_Produc);
            obj.put("Tipo_Ani_Produc", this.Tipo_Ani_Produc);
            obj.put("Validade_Produc", this.Validade_Produc);
            obj.put("Quant_Produc", this.Quant_Produc);
            obj.put("Valor_Produc", this.Valor_Produc);
            obj.put("Imagem_Prod", this.Imagem_Prod);
            obj.put("Id_Cli", this.Imagem_Prod);
            obj.put("Id_Compra", this.Imagem_Prod);
        } catch (JSONException e) {
            //trace("DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }
}
