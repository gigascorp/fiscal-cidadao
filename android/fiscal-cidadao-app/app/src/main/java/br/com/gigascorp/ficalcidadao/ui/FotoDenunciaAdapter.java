package br.com.gigascorp.ficalcidadao.ui;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.gigascorp.ficalcidadao.DenunciaActivity;
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
