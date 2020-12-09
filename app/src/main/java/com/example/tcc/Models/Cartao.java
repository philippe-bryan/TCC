package com.example.tcc.Models;

public class Cartao {

    private String Card;
    private String NomeCard;
    private String CodSeg;
    private String Valid;
    private String Bandeira;

    public String getCard() {
        return Card;
    }

    public void setCard(String card) {
        Card = card;
    }

    public String getNomeCard() {
        return NomeCard;
    }

    public void setNomeCard(String nome) {
        NomeCard = nome;
    }

    public String getCodSeg() {
        return CodSeg;
    }

    public void setCodSeg(String codSeg) {
        CodSeg = codSeg;
    }

    public String getValid() {
        return Valid;
    }

    public void setValid(String valid) {
        Valid = valid;
    }

    public String getBandeira() {
        return Bandeira;
    }

    public void setBandeira(String bandeira) {
        Bandeira = bandeira;
    }

}
