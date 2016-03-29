package br.com.gigascorp.ficalcidadao;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class FiscalCidadaoApp extends Application{

    public static final String TAG = "FISCAL_CIDADAO_APP";
    public static final String API_URI = "http://www.fiscalcidadao.site/FiscalCidadaoWCF.svc/";

    private Retrofit retrofit = null;
    private FiscalCidadaoApi fiscalCidadaoApi = null;

    private Gson gson = null;
    private OkHttpClient okHttpClient = null;

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new GsonBuilder().setDateFormat("dd/MM/yy").create();
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(180, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(180, TimeUnit.SECONDS);

        if(gson == null || okHttpClient == null){

            Log.e(TAG, "GsonConverter ou OkHttpClient NULL");

            Toast.makeText(getApplicationContext(), "Erro ao carregar a API", Toast.LENGTH_LONG).show();
            return;
        }

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URI)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }

        if(fiscalCidadaoApi == null){
            fiscalCidadaoApi = retrofit.create(FiscalCidadaoApi.class);
        }

    }

    public FiscalCidadaoApi getFiscalCidadaoApi() {
        return fiscalCidadaoApi;
    }

}
