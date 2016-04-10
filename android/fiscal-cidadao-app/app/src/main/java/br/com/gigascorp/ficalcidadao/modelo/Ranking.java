package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Ranking {

    @SerializedName("Nome")
    private String nome;

    @SerializedName("Id")
    private String id;

    @SerializedName("Pontuacao")
    private int pontuacao;

    @SerializedName("UrlFoto")
    private String fotoPerfil;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
