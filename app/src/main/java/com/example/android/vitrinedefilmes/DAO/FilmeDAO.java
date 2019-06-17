package com.example.android.vitrinedefilmes.DAO;


import android.content.Context;

import com.example.android.vitrinedefilmes.Negocios.InputStreamNegocios;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FilmeDAO {

    private Context context;

    public FilmeDAO(Context context) {
        this.context = context;
    }

    private String jsonResponse = "";
    private static final String METODO_REQUISICAO_GET = "GET";
    public static final int TIMEOUT_LEITURA = 10000;
    public static final int TIMEOUT_CONEXAO = 15000;
    public static final int CODIGO_RESPOSTA_OK = 200;

    public String filmeConexaoAPI(URL url) throws IOException {

        if (url == null) {
            return jsonResponse; //Retornado o Constantes.STRING_VAZIO;
        }

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT_CONEXAO);
            urlConnection.setRequestMethod(METODO_REQUISICAO_GET);
            urlConnection.setReadTimeout(TIMEOUT_LEITURA);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == CODIGO_RESPOSTA_OK) {

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamNegocios inputStreamNegocios = new InputStreamNegocios(context);
                jsonResponse = inputStreamNegocios.parserObjetoString(inputStream);

                if (!jsonResponse.isEmpty()) {
                    return jsonResponse; // retornado o valor real do objeto.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse; // retornado o Constantes.STRING_VAZIO;
    }
}
