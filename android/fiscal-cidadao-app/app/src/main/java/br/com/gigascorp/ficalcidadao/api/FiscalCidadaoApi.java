/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guiherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

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

package br.com.gigascorp.ficalcidadao.api;

import com.squareup.okhttp.ResponseBody;

import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.FacebookLogin;
import br.com.gigascorp.ficalcidadao.modelo.LoginResponse;
import br.com.gigascorp.ficalcidadao.modelo.Perfil;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.ConveniosWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.DenunciasWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.PerfilWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.RankingResultWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.RankingWrapper;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface FiscalCidadaoApi {

    @GET("GetConveniosByCoordinate/{lat}/{lng}")
    public Call<ConveniosWrapper> conveniosProximos(@Path("lat") double lat, @Path("lng") double lng);

    @POST("FazerDenuncia")
    public Call<ResponseBody> enviarDenuncia(@Body Denuncia denuncia);

    @POST("Login")
    public Call<LoginResponse> login(@Body FacebookLogin loginData);

    @GET("GetDenunciaByUsuario/{usuarioid}")
    public Call<DenunciasWrapper> getDenuncias(@Path("usuarioid") String id);

    @GET("GetUsuario/{usuarioid}")
    public Call<PerfilWrapper> getPerfil(@Path("usuarioid") String id);

    @GET("GetRanking/{usuarioid}")
    public Call<RankingResultWrapper> getRanking(@Path("usuarioid") String id);

}
