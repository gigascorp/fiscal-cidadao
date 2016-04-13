/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guiherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

   This file is part of Fiscal Cidadão.

   Fiscal Cidadão is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Fiscal Cidadão is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Fiscal Cidadão.  If not, see <http://www.gnu.org/licenses/>.
*/

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
