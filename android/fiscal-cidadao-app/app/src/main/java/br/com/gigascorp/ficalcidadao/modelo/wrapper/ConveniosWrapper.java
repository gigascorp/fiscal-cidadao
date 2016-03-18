package br.com.gigascorp.ficalcidadao.modelo.wrapper;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.gigascorp.ficalcidadao.modelo.wrapper.GetConveniosByCoordinateResult;

public class ConveniosWrapper {

    @SerializedName("GetConveniosByCoordinateResult")
    private GetConveniosByCoordinateResult getConveniosByCoordinateResult;

    public GetConveniosByCoordinateResult getGetConveniosByCoordinateResult() {
        return getConveniosByCoordinateResult;
    }

    public void setGetConveniosByCoordinateResult(GetConveniosByCoordinateResult getConveniosByCoordinateResult) {
        this.getConveniosByCoordinateResult = getConveniosByCoordinateResult;
    }

}