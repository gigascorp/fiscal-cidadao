package br.com.gigascorp.ficalcidadao.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import br.com.gigascorp.ficalcidadao.DenunciaActivity;
import br.com.gigascorp.ficalcidadao.MainActivity;
import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;
import br.com.gigascorp.ficalcidadao.util.Util;


public class FotoDenunciaViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private DenunciaActivity activity;
    private FotoHolderWrap fotoWrap;
    protected ImageView imgFoto;

    public FotoDenunciaViewHolder(View v, DenunciaActivity activity) {
        super(v);
        this.view = v;
        this.activity = activity;
        imgFoto = (ImageView) v.findViewById(R.id.imgFotoDenuncia);
    }

    public void setFotoWrap(FotoHolderWrap fotoWrap) {
        this.fotoWrap = fotoWrap;
        imgFoto.setImageBitmap(fotoWrap.getImg());

        if(fotoWrap.isBotao()){
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File img = new File(Environment.getExternalStorageDirectory(),activity.TEMP_IMAGE);

                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(img));
                    activity.startActivityForResult(intent, DenunciaActivity.CAMERA_RESULT);
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
