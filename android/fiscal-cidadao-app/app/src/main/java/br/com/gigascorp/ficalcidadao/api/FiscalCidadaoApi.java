package br.com.gigascorp.ficalcidadao.api;

import br.com.gigascorp.ficalcidadao.modelo.wrapper.ConveniosWrapper;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface FiscalCidadaoApi {

    @GET("GetConveniosByCoordinate/{lat}/{lng}")
    public Call<ConveniosWrapper> conveniosProximos(@Path("lat") double lat, @Path("lng") double lng);

}
