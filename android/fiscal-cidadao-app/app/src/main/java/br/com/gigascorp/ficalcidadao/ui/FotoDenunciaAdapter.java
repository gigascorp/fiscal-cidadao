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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.DenunciarFragment;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;

public class FotoDenunciaAdapter extends RecyclerView.Adapter<FotoDenunciaViewHolder> {

    private DenunciarFragment fragment;
    private List<FotoHolderWrap> fotos;

    public FotoDenunciaAdapter(List<FotoHolderWrap> fotos, DenunciarFragment fragment){
        this.fotos = fotos;
        this.fragment = fragment;
    }

    @Override
    public FotoDenunciaViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.foto_denuncia_card_view, parent, false);
        return new FotoDenunciaViewHolder(itemView, fragment);
    }

    @Override
    public void onBindViewHolder(FotoDenunciaViewHolder holder, int position) {
        FotoHolderWrap fotoWrap = fotos.get(position);
        holder.setFotoWrap(fotoWrap);
    }

    @Override
    public int getItemCount() {
        if(fotos == null)
            return 0;

        return fotos.size();
    }
}
