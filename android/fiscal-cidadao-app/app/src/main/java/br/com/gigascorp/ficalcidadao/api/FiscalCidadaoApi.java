package br.com.gigascorp.ficalcidadao.api;

import java.util.List;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import retrofit.Call;
import retrofit.http.GET;

public interface FiscalCidadaoApi {

    @GET("/convenios")
    public Call<List<Convenio>> conveniosProximos();

}
