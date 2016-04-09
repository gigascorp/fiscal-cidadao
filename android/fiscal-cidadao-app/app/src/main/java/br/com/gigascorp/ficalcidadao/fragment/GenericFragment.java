package br.com.gigascorp.ficalcidadao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import br.com.gigascorp.ficalcidadao.FiscalCidadaoApp;
import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import br.com.gigascorp.ficalcidadao.modelo.Usuario;

public class GenericFragment extends Fragment {

    private FiscalCidadaoApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.app = ((FiscalCidadaoApp) getActivity().getApplication());
    }

    protected FiscalCidadaoApi getFiscalCidadaoApi(){
        return this.app.getFiscalCidadaoApi();
    }

    protected Usuario getUsuarioLogado(){
        return this.app.getCurrentUsuario();
    }

    protected Context getContextoFiscalCidadaoApp(){
        return app.getApplicationContext();
    }
}
