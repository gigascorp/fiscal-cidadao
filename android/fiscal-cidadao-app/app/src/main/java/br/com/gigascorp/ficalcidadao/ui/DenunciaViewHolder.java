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

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.FiscalCidadaoApp;
import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.ConvenioFragment;
import br.com.gigascorp.ficalcidadao.fragment.DenunciaFragment;
import br.com.gigascorp.ficalcidadao.fragment.DenunciarFragment;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;

public class DenunciaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Denuncia denuncia;

    private TextView txtDenunciaObjeto;
    private TextView txtDenuciaStatus;
    private TextView txtDenuciaData;

    public DenunciaViewHolder(View v) {
        super(v);
        v.setOnClickListener(this);
        txtDenunciaObjeto = (TextView) v.findViewById(R.id.txtDenunciaObjeto);
        txtDenuciaStatus = (TextView) v.findViewById(R.id.txtDenunciaStatus);
        txtDenuciaData = (TextView) v.findViewById(R.id.txtDenunciaData);
    }

    public void setDenuncia(Denuncia denuncia){
        this.denuncia = denuncia;
        txtDenunciaObjeto.setText(denuncia.getConvenio().getObjeto());
        txtDenuciaStatus.setText(denuncia.getParecer());
        txtDenuciaData.setText("Data da Denúncia: " + (new SimpleDateFormat("dd/MM/yyyy")).format(denuncia.getDataDenuncia()));
    }

    @Override
    public void onClick(View v) {
        try {
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();

            DenunciaFragment fragment = DenunciaFragment.getNewIntance(denuncia);

            fragmentManager.beginTransaction()
                    .replace(R.id.telaHome, fragment)
                    .addToBackStack(null)
                    .commit();

        } catch (ClassCastException e) {
            Log.e(FiscalCidadaoApp.TAG, "Can't get fragment manager");
            e.printStackTrace();
            return;
        }
    }
}
