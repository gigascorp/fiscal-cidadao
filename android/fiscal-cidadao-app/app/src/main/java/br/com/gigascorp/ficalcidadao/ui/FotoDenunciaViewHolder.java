package br.com.gigascorp.ficalcidadao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;


public class FotoDenunciaViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private FotoHolderWrap fotoWrap;
    protected ImageView imgFoto;

    public FotoDenunciaViewHolder(View v) {
        super(v);
        this.view = v;
        imgFoto = (ImageView) v.findViewById(R.id.imgFotoDenuncia);
    }

    public void setFotoWrap(FotoHolderWrap fotoWrap) {
        this.fotoWrap = fotoWrap;
        imgFoto.setImageBitmap(fotoWrap.getImg());
        
        if(fotoWrap.isBotao()){
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicou na câmera", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicou na foto", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
