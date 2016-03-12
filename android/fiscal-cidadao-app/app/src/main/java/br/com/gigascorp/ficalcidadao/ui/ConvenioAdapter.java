package br.com.gigascorp.ficalcidadao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;

public class ConvenioAdapter extends RecyclerView.Adapter<ConvenioViewHolder> {

    private List<Convenio> convenios;

    public ConvenioAdapter(List<Convenio> convenios){
        this.convenios = convenios;
    }

    @Override
    public ConvenioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.convenio_card_view, parent, false);
        return new ConvenioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConvenioViewHolder holder, int position) {
        Convenio convenio = convenios.get(position);
        holder.txtCardJustificativa.setText(convenio.getJustificativa());
        holder.getTxtCardCoordenada.setText("(" + String.valueOf(convenio.getCoordenada().getLat()) + ", " + String.valueOf(convenio.getCoordenada().getLng()) + ")");
    }

    @Override
    public int getItemCount() {
        if(convenios == null)
            return 0;

        return convenios.size();
    }
}
