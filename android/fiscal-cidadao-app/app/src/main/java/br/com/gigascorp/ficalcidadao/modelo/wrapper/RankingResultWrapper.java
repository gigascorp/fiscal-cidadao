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

package br.com.gigascorp.ficalcidadao.modelo.wrapper;


import com.google.gson.annotations.SerializedName;

public class RankingResultWrapper {

    @SerializedName("GetRankingResult")
    private RankingWrapper getRankingResult;

    public RankingWrapper getGetRankingResult() {
        return getRankingResult;
    }

    public void setGetRankingResult(RankingWrapper getRankingResult) {
        this.getRankingResult = getRankingResult;
    }
}