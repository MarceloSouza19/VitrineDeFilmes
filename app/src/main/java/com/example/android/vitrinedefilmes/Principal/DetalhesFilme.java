package com.example.android.vitrinedefilmes.Principal;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.vitrinedefilmes.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetalhesFilme extends AppCompatActivity {

    private ImageView imagemViewFundoFilme;
    private ImageView imagemViewFilmeCartaz;
    private TextView tituloFilmeDetalhes;
    private TextView sinopseEnredoFilme;
    private TextView quantidadeEstrelas;
    private TextView dataLancamentoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhes);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        String tituloFilme = getIntent().getExtras().getString(getApplicationContext().getResources().getString(R.string.titulo_filme));
        String sinopseEnredo = getIntent().getExtras().getString(getApplicationContext().getResources().getString(R.string.sinopse_enredo));
        Double mediaDeVotos = getIntent().getExtras().getDouble(getApplicationContext().getResources().getString(R.string.media_votos));
        String dataLancamento = getIntent().getExtras().getString(getApplicationContext().getResources().getString(R.string.data_lancamento));
        String urlCartazFilme = getIntent().getExtras().getString(getApplicationContext().getResources().getString(R.string.url_cartaz));
        String urlFundoImagemFilme = getIntent().getExtras().getString(getApplicationContext().getResources().getString(R.string.url_fundo));

        SimpleDateFormat entrada = new SimpleDateFormat(getApplicationContext().getResources().getString(R.string.EE_MMM_DD), Locale.US);
        SimpleDateFormat saida = new SimpleDateFormat(getApplicationContext().getResources().getString(R.string.dd_MM_yy));

        String dataFormatada = "";
        Date date;

        try {
            date = entrada.parse(dataLancamento);
            dataFormatada = saida.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        imagemViewFundoFilme = findViewById(R.id.imagemFundoFilme);
        imagemViewFilmeCartaz = findViewById(R.id.imagemViewFilmeCartaz);
        tituloFilmeDetalhes = findViewById(R.id.tituloFilmeDetalhes);
        sinopseEnredoFilme = findViewById(R.id.sinopseEnredoFilme);
        quantidadeEstrelas = findViewById(R.id.quantidadeEstrelas);
        dataLancamentoView = findViewById(R.id.dataLancamento);

        if (!urlFundoImagemFilme.equals(getApplicationContext().getResources().getString(R.string.resposta_api_nulo))) {
            Picasso.with(getApplicationContext()).load(getApplicationContext().getString(R.string.url_imagem_filme) + urlFundoImagemFilme).into(imagemViewFundoFilme);
        } else if (!urlCartazFilme.equals(getApplicationContext().getResources().getString(R.string.resposta_api_nulo))) {
            Picasso.with(getApplicationContext()).load(getApplicationContext().getString(R.string.url_imagem_filme) + urlCartazFilme).into(imagemViewFundoFilme);
        }

        if (!urlCartazFilme.equals(getApplicationContext().getResources().getString(R.string.resposta_api_nulo)))
            Picasso.with(getApplicationContext()).load(getApplicationContext().getString(R.string.url_imagem_filme) + urlCartazFilme).into(imagemViewFilmeCartaz);
        else if (!urlFundoImagemFilme.equals(getApplicationContext().getResources().getString(R.string.resposta_api_nulo))) {
            Picasso.with(getApplicationContext()).load(getApplicationContext().getString(R.string.url_imagem_filme) + urlFundoImagemFilme).into(imagemViewFilmeCartaz);
        }
        tituloFilmeDetalhes.setText(tituloFilme);
        sinopseEnredoFilme.setText(sinopseEnredo.length() > 0 ? sinopseEnredo : getResources().getString(R.string.sem_sinopse));
        quantidadeEstrelas.setText(mediaDeVotos > 0 ? getResources().getString(R.string.nota)+" "+mediaDeVotos.toString() : getResources().getString(R.string.sem_classificacao));
        dataLancamentoView.setText(dataFormatada);

    }
}
