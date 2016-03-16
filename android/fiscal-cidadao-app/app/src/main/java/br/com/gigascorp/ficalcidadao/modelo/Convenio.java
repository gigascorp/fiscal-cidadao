package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Convenio {

    @SerializedName("Id")
    private int id;

    @SerializedName("ObjetoDescricao")
    private String objeto;

    @SerializedName("Latitude")
    private float lat;

    @SerializedName("Longitude")
    private float lng;

    @SerializedName("DataInicio")
    private Date dataInicio;

    @SerializedName("DataFim")
    private Date dataFim;

    @SerializedName("ProponenteNome")
    private String proponente;

    @SerializedName("ProponenteResponsavel")
    private String responsavel;

    @SerializedName("ProponenteTelefone")
    private String telefone;

    @SerializedName("Situacao")
    private String situacao;

    @SerializedName("Valor")
    private float valor;

    public Coordenada getCoordenada(){
        return new Coordenada(this.lat, this.lng);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getProponente() {
        return proponente;
    }

    public void setProponente(String proponente) {
        this.proponente = proponente;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
