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

package br.com.gigascorp.ficalcidadao.modelo.wrapper;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.gigascorp.ficalcidadao.modelo.Perfil;
import br.com.gigascorp.ficalcidadao.modelo.Ranking;

public class RankingWrapper {

    @SerializedName("Count")
    private int quantidade;

    @SerializedName("Lista")
    private List<Ranking> lista;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public List<Ranking> getLista() {
        return lista;
    }

    public void setLista(List<Ranking> lista) {
        this.lista = lista;
    }
}
