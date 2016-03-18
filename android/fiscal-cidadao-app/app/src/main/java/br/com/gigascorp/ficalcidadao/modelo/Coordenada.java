package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;

public class Coordenada {

    private float lat;

    @SerializedName("long")
    private float lng;

    public Coordenada(float lat, float lng){
        this.lat = lat;
        this.lng = lng;
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

    public boolean equals(Coordenada other) {
        return this.getLat() == other.getLat() && this.getLng() == other.getLng();
    }
}
