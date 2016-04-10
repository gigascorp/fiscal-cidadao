package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;

public class Perfil {

    @SerializedName("Nome")
    private String nome;

    @SerializedName("Id")
    private String id;

    @SerializedName("Email")
    private String email;

    @SerializedName("Pontuacao")
    private int pontuacao;

    @SerializedName("CountDenuncias")
    private int totalDenuncias;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getTotalDenuncias() {
        return totalDenuncias;
    }

    public void setTotalDenuncias(int totalDenuncias) {
        this.totalDenuncias = totalDenuncias;
    }
}
