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
