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
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;

public class DenunciaAdapter extends RecyclerView.Adapter<DenunciaViewHolder> {

    private List<Denuncia> denuncias;

    public DenunciaAdapter(List<Denuncia> denuncias){
        this.denuncias = denuncias;
    }

    @Override
    public DenunciaViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.denuncia_item_view, parent, false);
        return new DenunciaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DenunciaViewHolder holder, int position) {
        Denuncia denuncia = denuncias.get(position);
        holder.setDenuncia(denuncia);
    }

    @Override
    public int getItemCount() {
        if(denuncias == null)
            return 0;

        return denuncias.size();
    }
}
