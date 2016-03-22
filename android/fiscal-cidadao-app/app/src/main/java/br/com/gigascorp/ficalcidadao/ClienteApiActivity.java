package br.com.gigascorp.ficalcidadao;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ClienteApiActivity extends GenericActivity{

    protected Retrofit retrofit = null;
    protected FiscalCidadaoApi fiscalApi = null;

    private Gson gson = null;
    private OkHttpClient okHttpClient = null;

    public ClienteApiActivity(){
        gson = new GsonBuilder().setDateFormat("dd/MM/yy").create();
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(180, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(180, TimeUnit.SECONDS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(gson == null || okHttpClient == null){
            Log.e(TAG, "GsonConverter ou OkHttpClient NULL");

            Toast.makeText(this, "Erro ao carregar a API", Toast.LENGTH_LONG).show();

            finish();
            return;
        }

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URI)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }

        if(fiscalApi == null){
            fiscalApi = retrofit.create(FiscalCidadaoApi.class);
        }

    }
}
