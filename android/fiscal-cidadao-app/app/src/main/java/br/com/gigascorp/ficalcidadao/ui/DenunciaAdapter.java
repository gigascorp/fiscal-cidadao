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
