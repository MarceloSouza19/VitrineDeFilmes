package com.example.android.vitrinedefilmes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.vitrinedefilmes.Entidades.Filme;
import com.example.android.vitrinedefilmes.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends BaseAdapter {


    private Context context;
    private List<Filme> listaDeFilmes;

    public GridAdapter(Context context, List<Filme> listaDeFilmes) {
        this.context = context;
        this.listaDeFilmes = listaDeFilmes;
    }

    @Override
    public int getCount() {
        return listaDeFilmes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeFilmes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_grid_filme, viewGroup, false);
        }

        ImageView cartazFilme = view.findViewById(R.id.imagemGridFilme);

        if (!listaDeFilmes.get(position).getCartazFilme().equals(context.getResources().getString(R.string.resposta_api_nulo))) {
            Picasso.with(context).load(context.getResources().getString(R.string.url_imagem_filme) + listaDeFilmes.get(position).getCartazFilme()).into(cartazFilme);
        } else{
            Picasso.with(context).load(context.getResources().getString(R.string.url_imagem_filme) + listaDeFilmes.get(position).getFundoImagemFilme()).into(cartazFilme);
        }
        return view;
    }
}
