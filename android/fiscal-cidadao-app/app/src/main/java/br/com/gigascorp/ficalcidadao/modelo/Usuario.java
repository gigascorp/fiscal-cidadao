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

/**
 * Created by wallashss on 29/03/16.
 */
public class Usuario
{
    private String name;
    private String id;
    private String token;
    private String imageUrl;

    public Usuario(String id){
        this.id = id;
    }

    public Usuario(String name, String id, String token, String imageUrl)
    {
        this.name = name;
        this.id = id;
        this.token = token;
        this.imageUrl = imageUrl;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getToken()
    {
        return token;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }


}
