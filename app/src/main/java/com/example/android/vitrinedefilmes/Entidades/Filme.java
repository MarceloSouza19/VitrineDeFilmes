package com.example.android.vitrinedefilmes.Entidades;

import java.util.Date;

public class Filme {

    private String tituloFilme;
    private Date dataLancamento;
    private String cartazFilme;
    private String fundoImagemFilme;
    private double mediaDeVotos;
    private String sinopseDoEnredo;


    public Filme(String tituloFilme, Date dataLancamento, String cartazFilme, double mediaDeVotos, String sinopseDoEnredo, String fundoImagemFilme) {
        this.tituloFilme = tituloFilme;
        this.dataLancamento = dataLancamento;
        this.cartazFilme = cartazFilme;
        this.mediaDeVotos = mediaDeVotos;
        this.sinopseDoEnredo = sinopseDoEnredo;
        this.fundoImagemFilme = fundoImagemFilme;
    }

    public String getTituloFilme() {

        return tituloFilme;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public String getCartazFilme() {
        return cartazFilme;
    }

    public double getMediaDeVotos() {
        return mediaDeVotos;
    }

    public String getFundoImagemFilme() {
        return fundoImagemFilme;
    }

    public String getSinopseDoEnredo() {
        return sinopseDoEnredo;

    }
}
