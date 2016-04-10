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