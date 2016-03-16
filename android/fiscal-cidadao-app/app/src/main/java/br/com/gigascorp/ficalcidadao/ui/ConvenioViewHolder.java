package br.com.gigascorp.ficalcidadao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.gigascorp.ficalcidadao.R;

public class ConvenioViewHolder extends RecyclerView.ViewHolder{

    protected TextView txtCardObjeto;
    protected TextView txtCardDataFim;
    protected TextView txtCardSituacao;

    public ConvenioViewHolder(View v) {
        super(v);
        txtCardObjeto = (TextView) v.findViewById(R.id.txtCardObjeto);
        txtCardDataFim = (TextView) v.findViewById(R.id.txtCardDataFim);
        txtCardSituacao = (TextView) v.findViewById(R.id.txtCardSituacao);
    }
}
