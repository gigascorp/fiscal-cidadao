package br.com.gigascorp.ficalcidadao.modelo.wrapper;

import android.graphics.Bitmap;

public class FotoHolderWrap {

    private Bitmap img;

    public FotoHolderWrap(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

}
