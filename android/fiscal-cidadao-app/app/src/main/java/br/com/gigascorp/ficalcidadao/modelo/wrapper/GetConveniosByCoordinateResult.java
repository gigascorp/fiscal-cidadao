package br.com.gigascorp.ficalcidadao.modelo.wrapper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;

public class GetConveniosByCoordinateResult {

    @SerializedName("ListaConvenios")
    private List<Convenio> listaConvenios;

    @SerializedName("Quantidade")
    private int quantidade;

    public List<Convenio> getListaConvenios() {
        return listaConvenios;
    }

    public void setListaConvenios(List<Convenio> listaConvenios) {
        this.listaConvenios = listaConvenios;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
