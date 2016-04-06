package br.com.gigascorp.ficalcidadao.modelo.wrapper;

import android.graphics.Bitmap;

public class FotoHolderWrap {

    private Bitmap img;

    private int posicao;

    public FotoHolderWrap(Bitmap img, int pos) {
        this.img = img;
        this.posicao = pos;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}
