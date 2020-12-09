package com.example.tcc.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Cliente {

        private int idCli;
        private String Nome;
        private String RazaoSocial;
        private String CNPJ;
        private String Endereco;
        private String Telefone;
        private String Celular;
        private String Email;
        private String Senha;

        public Cliente(){   }
        public Cliente(String nome, String CNPJ, String Endereco, String RazaoSocial,
                       String Telefone, String Celular, String Email, String Senha)
        {
            this.Nome = nome;
            this.CNPJ = CNPJ;
            this.Endereco = Endereco;
            this.RazaoSocial = RazaoSocial;
            this.Telefone = Telefone;
            this.Celular = Celular;
            this.Email = Email;
            this.Senha = Senha;
        }

        public Cliente(int idCli, String nome, String CNPJ, String Endereco, String RazaoSocial,
                       String Telefone, String Celular)
        {
            this.idCli = idCli;
            this.Nome = nome;
            this.CNPJ = CNPJ;
            this.Endereco = Endereco;
            this.RazaoSocial = RazaoSocial;
            this.Telefone = Telefone;
            this.Celular = Celular;
        }

        public Cliente(int idCli, String nome, String CNPJ, String Endereco, String RazaoSocial,
                       String Telefone, String Celular, String Email, String Senha)
        {
            this.idCli = idCli;
            this.Nome = nome;
            this.CNPJ = CNPJ;
            this.Endereco = Endereco;
            this.RazaoSocial = RazaoSocial;
            this.Telefone = Telefone;
            this.Celular = Celular;
            this.Email = Email;
            this.Senha = Senha;
        }

        public Cliente(String Email, String Senha){
            this.Email = Email;
            this.Senha = Senha;
        }

        public Cliente(String Email, int idCli){
            this.idCli = idCli;
            this.Email = Email;
        }

        //Getters
        public int getIdCli() { return idCli; }

        public String getNome() {
            return Nome;
        }

        public String getCNPJ() {
            return CNPJ;
        }

        public String getEndereco() {
            return Endereco;
        }

        public String getRazaoSocial() {
        return RazaoSocial;
    }

        public String getTelefone() {
            return Telefone;
        }

        public String getCelular() {
            return Celular;
        }

        public String getEmail() {
            return Email;
        }

        public String getSenha() {
            return Senha;
        }

        //Setters
        public void setIdCli(int idCli) { this.idCli = idCli; }

        public void setNome(String Nome) {
            this.Nome = Nome;
        }

        public void setCNPJ(String CNPJ) {
            this.CNPJ = CNPJ;
        }

        public void setEndereco(String Endereco) {
            this.Endereco = Endereco;
        }

        public void setRazaoSocial(String RazaoSocial) {
            this.RazaoSocial = RazaoSocial;
    }

        public void setTelefone(String Telefone) {
            this.Telefone = Telefone;
        }

        public void setCelular(String Celular) {
            this.Celular = Celular;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public void setSenha(String Senha) {
            this.Senha = Senha;
        }

        public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Id_Cliente", this.idCli);
            obj.put("Nome_Cli", this.Nome);
            obj.put("CNPJ_Cli", this.CNPJ);
            obj.put("End_Cli", this.Endereco);
            obj.put("Razao_Social_Cli", this.RazaoSocial);
            obj.put("Tel_Cli", this.Telefone);
            obj.put("Cel_Cli", this.Celular);
            obj.put("Email_Cli", this.Email);
            obj.put("Senha_Cli", this.Senha);
        } catch (JSONException e) {
            //trace("DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }
}
