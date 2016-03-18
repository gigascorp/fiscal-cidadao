package br.com.gigascorp.ficalcidadao.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Classe criada exclusivamente para controlar se a lista de convênios do SlidePanel pode scroolar
 */
public class ConvenioLinearLayoutManager extends LinearLayoutManager{

    private boolean scrool = false;

    public ConvenioLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return this.scrool;
    }

    public void setScrool(boolean scrool) {
        this.scrool = scrool;
    }
}
