package br.com.gigascorp.ficalcidadao.modelo.wrapper;

import android.graphics.Bitmap;

public class FotoHolderWrap {

    private Bitmap img;
    private boolean botao;

    public FotoHolderWrap(Bitmap img) {
        this.img = img;
        botao = false;
    }

    public FotoHolderWrap(Bitmap img, boolean botao) {
        this.img = img;
        this.botao = botao;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public boolean isBotao() {
        return botao;
    }

    public void setBotao(boolean botao) {
        this.botao = botao;
    }
}
