/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guilherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

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
