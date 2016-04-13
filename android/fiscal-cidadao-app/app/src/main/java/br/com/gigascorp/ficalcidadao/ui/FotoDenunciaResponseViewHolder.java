/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guiherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

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
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.DenunciaFragment;

import static android.graphics.Bitmap.*;

public class FotoDenunciaResponseViewHolder extends RecyclerView.ViewHolder  {

    private View view;
    private DenunciaFragment fragment;
    private String foto;
    protected ImageView imgFoto;
    private ImageButton imgBtnExcluir;

    public FotoDenunciaResponseViewHolder(View v, DenunciaFragment fragment) {
        super(v);
        this.view = v;
        this.fragment = fragment;
        imgFoto = (ImageView) v.findViewById(R.id.imgFotoDenuncia);
        imgBtnExcluir = (ImageButton) v.findViewById(R.id.imgBtnExcluirFoto);
        imgBtnExcluir.setVisibility(View.GONE);
    }

    public void setFoto(String foto) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(fragment.getActivity()));

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_cached_black_36dp)
                .considerExifParams(true)
                .cacheInMemory(true)
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bmp) {
                        return ThumbnailUtils.extractThumbnail(bmp, 1024, 1024);
                    }
                })
                .build();

        imageLoader.displayImage(foto, imgFoto, options);
    }

}
