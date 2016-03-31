package br.com.gigascorp.ficalcidadao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.DenunciarFragment;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;


public class FotoDenunciaViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private DenunciarFragment fragment;
    private FotoHolderWrap fotoWrap;
    protected ImageView imgFoto;

    public FotoDenunciaViewHolder(View v, DenunciarFragment fragment) {
        super(v);
        this.view = v;
        this.fragment = fragment;
        imgFoto = (ImageView) v.findViewById(R.id.imgFotoDenuncia);
    }

    public void setFotoWrap(FotoHolderWrap fotoWrap) {
        this.fotoWrap = fotoWrap;
        imgFoto.setImageBitmap(fotoWrap.getImg());
    }

}
