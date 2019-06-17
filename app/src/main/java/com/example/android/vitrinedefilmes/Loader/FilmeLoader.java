package com.example.android.vitrinedefilmes.Loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.vitrinedefilmes.Entidades.Filme;
import com.example.android.vitrinedefilmes.Negocios.FilmeNegocios;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FilmeLoader extends AsyncTaskLoader<List<Filme>> {

    String url;

    public FilmeLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }

    @Nullable
    @Override
    public List<Filme> loadInBackground() {

        List<Filme> listaDeFilme = new ArrayList<>();

        try {
            FilmeNegocios filmeNegocios = new FilmeNegocios(getContext());
            listaDeFilme = filmeNegocios.buscarFilmesAPI(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return listaDeFilme;
    }
}
