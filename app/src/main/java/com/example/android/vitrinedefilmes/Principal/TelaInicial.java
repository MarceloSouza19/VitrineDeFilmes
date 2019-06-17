package com.example.android.vitrinedefilmes.Principal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.android.vitrinedefilmes.Adaptadores.AdaptadorFilmeRecyclerView;
import com.example.android.vitrinedefilmes.Adaptadores.GridAdapter;
import com.example.android.vitrinedefilmes.Entidades.Filme;
import com.example.android.vitrinedefilmes.Loader.FilmeLoader;
import com.example.android.vitrinedefilmes.Preferencias.Preferencias;
import com.example.android.vitrinedefilmes.R;

import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Filme>> {

    private static final int PRIMEIRA_POSICAO_LOADER = 0;
    private static final int SEGUNDA_POSICAO_LOADER = 1;
    private static final int TERCEIRA_POSICAO_LOADER = 2;

    StringBuilder linkAPI = new StringBuilder();

    List<Filme> vitrineFilmes;
    List<Filme> gridFilmes;
    List<Filme> vitrineNaoPopularFilmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        ScrollView scrollView = findViewById(R.id.scrollView);
        RelativeLayout relativeLayoutMensagem = findViewById(R.id.falha);

        if ((getResources().getString(R.string.api_key).contains(getResources().getString(R.string.codigo_api_vazia)))) {

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.mensagem_codigo_api), Toast.LENGTH_LONG).show();
            scrollView.setVisibility(View.GONE);
            relativeLayoutMensagem.setVisibility(View.VISIBLE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            relativeLayoutMensagem.setVisibility(View.GONE);

            getSupportLoaderManager().initLoader(PRIMEIRA_POSICAO_LOADER, null, this).forceLoad();
            getSupportLoaderManager().initLoader(SEGUNDA_POSICAO_LOADER, null, this).forceLoad();
            getSupportLoaderManager().initLoader(TERCEIRA_POSICAO_LOADER, null, this).forceLoad();
        }

        CardView cardFlutuante = (CardView) findViewById(R.id.alterarBuscaDaVitrine);
        cardFlutuante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Preferencias.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.carregandoConfigs), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public Loader<List<Filme>> onCreateLoader(int numeroLoader, Bundle bundle) {

        linkAPI = new StringBuilder();

        if (numeroLoader == 0) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            linkAPI.append(getResources().getString(R.string.url_cabecalho));
            linkAPI.append(sharedPrefs.getString(getResources().getString(R.string.tipo_busca_vitrine), ""));
            linkAPI.append(getResources().getString(R.string.api_key));
            linkAPI.append(getResources().getString(R.string.url_linguagem));
        } else {
            linkAPI.append(getResources().getString(R.string.url_cabecalho_descobrir));
            linkAPI.append(getResources().getString(R.string.api_key));
            linkAPI.append(getResources().getString(R.string.url_linguagem));
        }
        if (numeroLoader == 1) {
            linkAPI.append(getResources().getString(R.string.url_ordenar_data_lancamento));
        } else if (numeroLoader == 2) {
            linkAPI.append(getResources().getString(R.string.url_sem_popularidade));
        }

        return new FilmeLoader(getApplicationContext(), linkAPI.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Filme>> loader, List<Filme> filmes) {

        GridView gridViewFilme = findViewById(R.id.gridLayoutFilme);

        if (loader.getId() == 0) {
            vitrineFilmes = filmes;
            RecyclerView recyclerViewFilme = findViewById(R.id.recyclerViewFilme);
            recyclerViewFilme.setAdapter(new AdaptadorFilmeRecyclerView(getApplicationContext(), filmes));
            recyclerViewFilme.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        } else if (loader.getId() == 1) {
            gridFilmes = filmes;
            gridViewFilme.setAdapter(new GridAdapter(getApplicationContext(), filmes));
        } else if (loader.getId() == 2) {
            vitrineNaoPopularFilmes = filmes;
            RecyclerView recyclerViewFilme = findViewById(R.id.recyclerViewFilmeNaoPopular);
            recyclerViewFilme.setAdapter(new AdaptadorFilmeRecyclerView(getApplicationContext(), filmes));
            recyclerViewFilme.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        }

        gridViewFilme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent telaDetalhes = new Intent(getApplicationContext(), DetalhesFilme.class);

                telaDetalhes.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                telaDetalhes.putExtra(getResources().getString(R.string.titulo_filme), gridFilmes.get(position).getTituloFilme());
                telaDetalhes.putExtra(getResources().getString(R.string.sinopse_enredo), gridFilmes.get(position).getSinopseDoEnredo());
                telaDetalhes.putExtra(getResources().getString(R.string.media_votos), gridFilmes.get(position).getMediaDeVotos());
                telaDetalhes.putExtra(getResources().getString(R.string.data_lancamento), gridFilmes.get(position).getDataLancamento().toString());
                telaDetalhes.putExtra(getResources().getString(R.string.url_cartaz), gridFilmes.get(position).getCartazFilme());
                telaDetalhes.putExtra(getResources().getString(R.string.url_fundo), gridFilmes.get(position).getFundoImagemFilme());

                startActivity(telaDetalhes);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<List<Filme>> loader) {

        getSupportLoaderManager().destroyLoader(PRIMEIRA_POSICAO_LOADER);
        getSupportLoaderManager().destroyLoader(SEGUNDA_POSICAO_LOADER);
        getSupportLoaderManager().destroyLoader(TERCEIRA_POSICAO_LOADER);

    }

    @Override
    protected void onResume() {

        getSupportLoaderManager().restartLoader(PRIMEIRA_POSICAO_LOADER, null, this).forceLoad();
        getSupportLoaderManager().restartLoader(SEGUNDA_POSICAO_LOADER, null, this).forceLoad();
        getSupportLoaderManager().restartLoader(TERCEIRA_POSICAO_LOADER, null, this).forceLoad();

        super.onResume();
    }
}
