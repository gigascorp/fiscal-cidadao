package br.com.gigascorp.ficalcidadao.modelo.wrapper;


import com.google.gson.annotations.SerializedName;

import br.com.gigascorp.ficalcidadao.modelo.Perfil;

public class PerfilWrapper {

    @SerializedName("GetUsuarioResult")
    private Perfil getUsuarioResult;

    public Perfil getGetUsuarioResult() {
        return getUsuarioResult;
    }

    public void setGetUsuarioResult(Perfil getUsuarioResult) {
        this.getUsuarioResult = getUsuarioResult;
    }
}
