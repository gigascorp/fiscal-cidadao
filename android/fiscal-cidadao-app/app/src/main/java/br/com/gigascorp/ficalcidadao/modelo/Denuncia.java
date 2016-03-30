package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Denuncia {

    @SerializedName("Comentarios")
    private String texto;

    @SerializedName("ConvenioId")
    private int convenioId;

    @SerializedName("UsuarioId")
    private String usuarioId;

    @SerializedName("ListaFotos")
    private List<String> fotos;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getConvenioId() {
        return convenioId;
    }

    public void setConvenioId(int convenioId) {
        this.convenioId = convenioId;
    }

    public void setUsuarioId(String usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioId()
    {
        return usuarioId;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

}
