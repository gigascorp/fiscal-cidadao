package br.com.gigascorp.ficalcidadao.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.ConvenioActivity;
import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;

public class ConvenioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Convenio convenio;

    private TextView txtCardObjeto;
    private TextView txtCardDataFim;
    private TextView txtCardSituacao;

    public ConvenioViewHolder(View v) {
        super(v);
        v.setOnClickListener(this);
        txtCardObjeto = (TextView) v.findViewById(R.id.txtCardObjeto);
        txtCardDataFim = (TextView) v.findViewById(R.id.txtCardDataFim);
        txtCardSituacao = (TextView) v.findViewById(R.id.txtCardSituacao);
    }

    public void setConvenio(Convenio convenio){
        this.convenio = convenio;
        txtCardObjeto.setText(convenio.getObjeto());
        txtCardDataFim.setText("Conclusão: " + (new SimpleDateFormat("dd/MM/yyyy")).format(convenio.getDataFim()));
        txtCardSituacao.setText("Situação: " + convenio.getSituacao());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ConvenioActivity.class);
        intent.putExtra("convenio", convenio);
        v.getContext().startActivity(intent);
    }
}
