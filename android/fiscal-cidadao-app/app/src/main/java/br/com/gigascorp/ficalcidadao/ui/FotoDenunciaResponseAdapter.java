package br.com.gigascorp.ficalcidadao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.DenunciaFragment;

public class FotoDenunciaResponseAdapter extends RecyclerView.Adapter<FotoDenunciaResponseViewHolder> {

    private DenunciaFragment fragment;
    private String[] fotos;

    public FotoDenunciaResponseAdapter(String[] fotos, DenunciaFragment fragment){
        this.fotos = fotos;
        this.fragment = fragment;
    }

    @Override
    public FotoDenunciaResponseViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.foto_denuncia_card_view, parent, false);
        return new FotoDenunciaResponseViewHolder(itemView, fragment);
    }

    @Override
    public void onBindViewHolder(FotoDenunciaResponseViewHolder holder, int position) {
        String uri = fotos[position];
        holder.setFoto(uri);
    }


    @Override
    public int getItemCount() {
        if(fotos == null)
            return 0;

        return fotos.length;
    }
}
