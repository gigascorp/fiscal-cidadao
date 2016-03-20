package br.com.gigascorp.ficalcidadao.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Classe criada exclusivamente para controlar se as fotos podem scroolar
 */
public class DenunciaGridLayoutManager extends GridLayoutManager {

    private boolean scrool = false;

    public DenunciaGridLayoutManager(Context context, int columns) {
        super(context, columns, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public boolean canScrollVertically() {
        return this.scrool;
    }

    public void setScrool(boolean scrool) {
        this.scrool = scrool;
    }
}
