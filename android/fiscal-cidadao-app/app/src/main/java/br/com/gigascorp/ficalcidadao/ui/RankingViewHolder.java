/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guilherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

   This file is part of Fiscal Cidadão.

   Fiscal Cidadão is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Fiscal Cidadão is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Fiscal Cidadão.  If not, see <http://www.gnu.org/licenses/>.
*/

package br.com.gigascorp.ficalcidadao.ui;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.FiscalCidadaoApp;
import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.DenunciaFragment;
import br.com.gigascorp.ficalcidadao.fragment.RankingFragment;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.Ranking;

public class RankingViewHolder extends RecyclerView.ViewHolder {

    private RankingFragment fragment;

    private Ranking ranking;

    private TextView txtRankingNome;
    private TextView txtRankingPontuacao;
    private ImageView imgFotoPerfilAmigo;

    public RankingViewHolder(View v, RankingFragment fragment) {
        super(v);
        this.fragment = fragment;
        txtRankingNome = (TextView) v.findViewById(R.id.txtRankingNome);
        txtRankingPontuacao = (TextView) v.findViewById(R.id.txtRankingPontuacao);
        imgFotoPerfilAmigo = (ImageView) v.findViewById(R.id.imgFotoPerfilAmigo);

    }

    public void setRanking(Ranking ranking){
        this.ranking = ranking;
        txtRankingNome.setText(ranking.getNome());
        txtRankingPontuacao.setText("Pontuação: " + ranking.getPontuacao());

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(fragment.getActivity()));

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(1000))
                .showImageOnLoading(R.drawable.ic_cached_black_36dp)
                .considerExifParams(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader.displayImage(ranking.getFotoPerfil(), imgFotoPerfilAmigo, options);
    }

}
