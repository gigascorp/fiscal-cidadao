package br.com.gigascorp.ficalcidadao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import br.com.gigascorp.ficalcidadao.GenericActivity;
import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ClienteApiFragment extends GenericFragment {

    protected static final String API_URI = "http://www.fiscalcidadao.site/FiscalCidadaoWCF.svc/";

    protected Retrofit retrofit = null;
    protected FiscalCidadaoApi fiscalApi = null;

    private Gson gson = null;
    private OkHttpClient okHttpClient = null;

    public ClienteApiFragment(){
        gson = new GsonBuilder().setDateFormat("dd/MM/yy").create();
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(180, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(180, TimeUnit.SECONDS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(gson == null || okHttpClient == null){

            Log.e(TAG, "GsonConverter ou OkHttpClient NULL");

            Toast.makeText(super.getActivity(), "Erro ao carregar a API", Toast.LENGTH_LONG).show();
            return null;
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

        return super.onCreateView(inflater,container, savedInstanceState);
    }
}
