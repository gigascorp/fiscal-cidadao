package br.com.gigascorp.ficalcidadao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.gigascorp.ficalcidadao.R;

public class ConvenioViewHolder extends RecyclerView.ViewHolder{

    protected TextView txtCardJustificativa;
    protected TextView getTxtCardCoordenada;

    public ConvenioViewHolder(View v) {
        super(v);
        txtCardJustificativa = (TextView) v.findViewById(R.id.txtCardJustificativa);
        getTxtCardCoordenada = (TextView) v.findViewById(R.id.txtcardCoordenada);
    }
}
