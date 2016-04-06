package br.com.gigascorp.ficalcidadao.modelo.wrapper;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.gigascorp.ficalcidadao.modelo.Denuncia;

public class DenunciasWrapper {

    @SerializedName("GetDenunciaByUsuarioResult")
    private List<Denuncia> getDenunciaByUsuarioResult;

    public List<Denuncia> getGetDenunciaByUsuarioResult() {
        return getDenunciaByUsuarioResult;
    }

    public void setGetDenunciaByUsuarioResult(List<Denuncia> getDenunciaByUsuarioResult) {
        this.getDenunciaByUsuarioResult = getDenunciaByUsuarioResult;
    }
}