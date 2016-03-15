package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;

public class Convenio {

    @SerializedName("Id")
    private int id;

    @SerializedName("ObjetoDescricao")
    private String objeto;

    @SerializedName("Latitude")
    private float lat;

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    @SerializedName("Longitude")
    private float lng;

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

    public Coordenada getCoordenada(){
        return new Coordenada(this.lat, this.lng);
    }
}
