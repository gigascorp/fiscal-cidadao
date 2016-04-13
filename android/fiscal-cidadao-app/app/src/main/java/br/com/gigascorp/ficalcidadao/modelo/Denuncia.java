/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guilherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

   This file is part of Fiscal Cidadão.

   Fiscal Cidadão is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Fiscal Cidadão is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Fiscal Cidadão.  If not, see <http://www.gnu.org/licenses/>.
*/

package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.io.SerializablePermission;
import java.util.Date;
import java.util.List;

public class Denuncia implements Serializable {

    @SerializedName("Comentarios")
    private String texto;

    @SerializedName("ConvenioId")
    private int convenioId;

    @SerializedName("UsuarioId")
    private String usuarioId;

    @SerializedName("ListaFotos")
    private List<String> fotos;

    @SerializedName("Convenio")
    private Convenio convenio;

    @SerializedName("Parecer")
    private String parecer;

    @SerializedName("DataDenuncia")
    private Date dataDenuncia;

    @SerializedName("Fotos")
    private String[] fotosUrl;

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

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public Date getDataDenuncia() {
        return dataDenuncia;
    }

    public void setDataDenuncia(Date dataDenuncia) {
        this.dataDenuncia = dataDenuncia;
    }

    public String[] getFotosUrl() {
        return fotosUrl;
    }

    public void setFotosUrl(String[] fotosUrl) {
        this.fotosUrl = fotosUrl;
    }
}
