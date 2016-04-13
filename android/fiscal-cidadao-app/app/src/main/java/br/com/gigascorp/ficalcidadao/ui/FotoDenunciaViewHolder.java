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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.DenunciarFragment;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;


public class FotoDenunciaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View view;
    private DenunciarFragment fragment;
    private FotoHolderWrap fotoWrap;
    protected ImageView imgFoto;
    private ImageButton imgBtnExcluir;

    public FotoDenunciaViewHolder(View v, DenunciarFragment fragment) {
        super(v);
        this.view = v;
        this.fragment = fragment;
        imgFoto = (ImageView) v.findViewById(R.id.imgFotoDenuncia);
        imgBtnExcluir = (ImageButton) v.findViewById(R.id.imgBtnExcluirFoto);
        imgBtnExcluir.setOnClickListener(this);
    }

    public void setFotoWrap(FotoHolderWrap fotoWrap) {
        this.fotoWrap = fotoWrap;
        imgFoto.setImageBitmap(fotoWrap.getImg());
    }

    @Override
    public void onClick(View v) {

        fragment.excluirFoto(fotoWrap.getPosicao());

    }
}
