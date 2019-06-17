package com.example.android.vitrinedefilmes.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.vitrinedefilmes.Entidades.Filme;
import com.example.android.vitrinedefilmes.Principal.DetalhesFilme;
import com.example.android.vitrinedefilmes.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorFilmeRecyclerView extends RecyclerView.Adapter<AdaptadorFilmeRecyclerView.ViewHolder> {

    private Context context;
    private List<Filme> listaDeFilmes;

    public AdaptadorFilmeRecyclerView(Context context, List<Filme> listaDeFilmes) {
        this.context = context;
        this.listaDeFilmes = listaDeFilmes;
    }

    @NonNull
    @Override

    public AdaptadorFilmeRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_filme, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFilmeRecyclerView.ViewHolder viewHolder, int i) {

        final Filme filme = this.listaDeFilmes.get(i);

        int tamanho  = filme.getTituloFilme().length();
        viewHolder.tituloFilme.setText(tamanho > 16? filme.getTituloFilme().substring(0,16) : filme.getTituloFilme());

        if(!filme.getCartazFilme().equals(context.getResources().getString(R.string.resposta_api_nulo))) {
            Picasso.with(context).load(context.getResources().getString(R.string.url_imagem_filme) + filme.getCartazFilme()).into(viewHolder.imagemFilme);
        } else if(!filme.getFundoImagemFilme().equals(context.getResources().getString(R.string.resposta_api_nulo))){
            Picasso.with(context).load(context.getResources().getString(R.string.url_imagem_filme) + filme.getFundoImagemFilme()).into(viewHolder.imagemFilme);
        }
        viewHolder.itemFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent telaDetalhes = new Intent(context,DetalhesFilme.class);

                telaDetalhes.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                telaDetalhes.putExtra(context.getResources().getString(R.string.titulo_filme),filme.getTituloFilme());
                telaDetalhes.putExtra(context.getResources().getString(R.string.sinopse_enredo),filme.getSinopseDoEnredo());
                telaDetalhes.putExtra(context.getResources().getString(R.string.media_votos),filme.getMediaDeVotos());
                telaDetalhes.putExtra(context.getResources().getString(R.string.data_lancamento),filme.getDataLancamento().toString());
                telaDetalhes.putExtra(context.getResources().getString(R.string.url_cartaz),filme.getCartazFilme());
                telaDetalhes.putExtra(context.getResources().getString(R.string.url_fundo),filme.getFundoImagemFilme());

                context.startActivity(telaDetalhes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listaDeFilmes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagemFilme;
        private TextView tituloFilme;
        private LinearLayout itemFilme;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemFilme = itemView.findViewById(R.id.imagemFilme);
            tituloFilme = itemView.findViewById(R.id.tituloFilme);
            itemFilme = itemView.findViewById(R.id.itemFilme);
        }
    }
}
