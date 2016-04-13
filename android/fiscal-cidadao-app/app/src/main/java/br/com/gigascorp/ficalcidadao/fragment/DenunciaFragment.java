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

package br.com.gigascorp.ficalcidadao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.ui.DenunciaGridLayoutManager;
import br.com.gigascorp.ficalcidadao.ui.FotoDenunciaAdapter;
import br.com.gigascorp.ficalcidadao.ui.FotoDenunciaResponseAdapter;

public class DenunciaFragment extends GenericFragment {

    private static final String DENUNCIA_KEY = "denuncia";

    private Denuncia denuncia;

    private TextView txtObjeto;
    private TextView txtProponente;
    private TextView txtResponsavel;
    private TextView txtTelefone;
    private TextView txtPeriodo;
    private TextView txtSituacao;
    private TextView txtValor;
    private TextView txtDenuncia;
    private TextView txtDataDenuncia;
    private TextView txtParecerDenuncia;

    private RecyclerView recyclerView;
    private DenunciaGridLayoutManager layoutManager;

    public static DenunciaFragment getNewIntance(Denuncia denuncia){

        DenunciaFragment fragment = new DenunciaFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DENUNCIA_KEY, denuncia);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Denúncia");

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_denuncia, container, false);

        denuncia = (Denuncia) getArguments().getSerializable(DENUNCIA_KEY);

        if(denuncia == null){
            Toast.makeText(super.getActivity(), "Erro ao recuperar a denúncia", Toast.LENGTH_SHORT).show();
            return null;
        }

        txtObjeto = (TextView) layout.findViewById(R.id.txtConvenioObjeto);
        txtProponente = (TextView) layout.findViewById(R.id.txtConvenioPromonente);
        txtResponsavel = (TextView) layout.findViewById(R.id.txtConvenioResponsavel);
        txtTelefone = (TextView) layout.findViewById(R.id.txtConvenioTelefone);
        txtPeriodo = (TextView) layout.findViewById(R.id.txtConvenioPeriodo);
        txtSituacao = (TextView) layout.findViewById(R.id.txtConvenioSituacao);
        txtValor = (TextView) layout.findViewById(R.id.txtConvenioValor);
        txtDenuncia = (TextView) layout.findViewById(R.id.txtDenuncia);
        txtDataDenuncia = (TextView) layout.findViewById(R.id.txtDataDenuncia);
        txtParecerDenuncia = (TextView) layout.findViewById(R.id.txtParecerDenuncia);

        recyclerView = (RecyclerView) layout.findViewById(R.id.gridFotos);
        recyclerView.setHasFixedSize(false);
        layoutManager = new DenunciaGridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance();

        numberFormat.setMinimumFractionDigits(2);

        txtObjeto.setText(denuncia.getConvenio().getObjeto());
        txtProponente.setText(denuncia.getConvenio().getProponente());
        txtResponsavel.setText(denuncia.getConvenio().getResponsavel());
        txtTelefone.setText(denuncia.getConvenio().getTelefone());
        txtPeriodo.setText("De " + dateFormat.format(denuncia.getConvenio().getDataInicio()) + " à " + dateFormat.format(denuncia.getConvenio().getDataFim()));
        txtSituacao.setText(denuncia.getConvenio().getSituacao());
        txtValor.setText("R$ " + numberFormat.format(denuncia.getConvenio().getValor()));
        txtDenuncia.setText(denuncia.getTexto());
        txtDataDenuncia.setText(dateFormat.format(denuncia.getDataDenuncia()));
        txtParecerDenuncia.setText(denuncia.getParecer());

        FotoDenunciaResponseAdapter adapter = new FotoDenunciaResponseAdapter(denuncia.getFotosUrl(), DenunciaFragment.this);
        recyclerView.setAdapter(adapter);

        return layout;
    }

}
