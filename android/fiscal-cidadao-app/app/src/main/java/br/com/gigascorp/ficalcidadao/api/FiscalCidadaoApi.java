package br.com.gigascorp.ficalcidadao.api;

import com.squareup.okhttp.ResponseBody;

import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.ConveniosWrapper;
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

}
