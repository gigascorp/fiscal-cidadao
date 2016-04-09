package br.com.gigascorp.ficalcidadao.api;

import com.squareup.okhttp.ResponseBody;

import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.FacebookLogin;
import br.com.gigascorp.ficalcidadao.modelo.LoginResponse;
import br.com.gigascorp.ficalcidadao.modelo.Perfil;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.ConveniosWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.DenunciasWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.PerfilWrapper;
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

}
