package br.com.gigascorp.ficalcidadao.api;

import br.com.gigascorp.ficalcidadao.modelo.wrapper.ConveniosWrapper;
import retrofit.Call;
import retrofit.http.GET;

public interface FiscalCidadaoApi {

    @GET("GetConveniosByCoordinate/-2.510954/-44.285454")
    public Call<ConveniosWrapper> conveniosProximos();

}
