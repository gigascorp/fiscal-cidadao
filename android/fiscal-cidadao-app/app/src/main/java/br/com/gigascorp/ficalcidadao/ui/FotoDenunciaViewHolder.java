package br.com.gigascorp.ficalcidadao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.DenunciarFragment;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;


public class FotoDenunciaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View view;
    private DenunciarFragment fragment;
    private FotoHolderWrap fotoWrap;
    protected ImageView imgFoto;
    private ImageButton imgBtnExcluir;

    public FotoDenunciaViewHolder(View v, DenunciarFragment fragment) {
        super(v);
        this.view = v;
        this.fragment = fragment;
        imgFoto = (ImageView) v.findViewById(R.id.imgFotoDenuncia);
        imgBtnExcluir = (ImageButton) v.findViewById(R.id.imgBtnExcluirFoto);
        imgBtnExcluir.setOnClickListener(this);
    }

    public void setFotoWrap(FotoHolderWrap fotoWrap) {
        this.fotoWrap = fotoWrap;
        imgFoto.setImageBitmap(fotoWrap.getImg());
    }

    @Override
    public void onClick(View v) {

        fragment.excluirFoto(fotoWrap.getPosicao());

    }
}
