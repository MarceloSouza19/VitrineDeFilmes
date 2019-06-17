package com.example.android.vitrinedefilmes.Negocios;

import android.content.Context;

import com.example.android.vitrinedefilmes.DAO.FilmeDAO;
import com.example.android.vitrinedefilmes.Entidades.Filme;
import com.example.android.vitrinedefilmes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilmeNegocios {

    private Context context;

    public FilmeNegocios(Context context) {
        this.context = context;
    }

    private List<Filme> listaDeFilmes = new ArrayList<Filme>();

    FilmeDAO filmeDAO;

    public List<Filme> buscarFilmesAPI(URL url) {

        filmeDAO = new FilmeDAO(context);

        String resultadoAPI = "";

        try {
            resultadoAPI = filmeDAO.filmeConexaoAPI(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!resultadoAPI.isEmpty()) {

            try {
                listaDeFilmes = parserListaDeFilmes(resultadoAPI);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return listaDeFilmes;
    }

    public List<Filme> parserListaDeFilmes(String listaFilmesEmString) throws JSONException {

        listaDeFilmes = new ArrayList<Filme>();
        JSONObject jsonTotal = new JSONObject(listaFilmesEmString);
        JSONArray jsonArray = jsonTotal.getJSONArray(context.getResources().getString(R.string.results));

        SimpleDateFormat dataFormatada = new SimpleDateFormat(context.getResources().getString(R.string.yyyy_MM_dd));

        if (jsonArray.length() > 0) {

            filmeDAO = new FilmeDAO(context);

            for (int linhaFilme = 0; linhaFilme < jsonArray.length(); linhaFilme++) {

                JSONObject itemFilmeJson = jsonArray.getJSONObject(linhaFilme);

                String tituloFilme;
                String dataLancamento;
                String imagemFundo;
                String imagemPoster;
                double mediaDeVotos;
                String sinopseDoEnredo;

                tituloFilme = itemFilmeJson.getString(context.getResources().getString(R.string.title));
                dataLancamento = itemFilmeJson.getString(context.getResources().getString(R.string.release_date));
                mediaDeVotos = itemFilmeJson.getDouble(context.getResources().getString(R.string.vote_average));
                sinopseDoEnredo = itemFilmeJson.getString(context.getResources().getString(R.string.overview));
                imagemPoster = itemFilmeJson.getString(context.getResources().getString(R.string.poster_path));
                imagemFundo = itemFilmeJson.getString(context.getResources().getString(R.string.backdrop_path));

                if (!imagemFundo.equals(context.getResources().getString(R.string.resposta_api_nulo)) || !imagemPoster.equals(context.getResources().getString(R.string.resposta_api_nulo))) {
                    try {
                        Date data = dataFormatada.parse(dataLancamento);
                        Filme filme = new Filme(tituloFilme, data, imagemPoster, mediaDeVotos, sinopseDoEnredo, imagemFundo);
                        listaDeFilmes.add(filme);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    continue;
                }
            }
        }
        return listaDeFilmes;
    }
}
