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

package br.com.gigascorp.ficalcidadao;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import br.com.gigascorp.ficalcidadao.modelo.Usuario;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class FiscalCidadaoApp extends Application {

    public static final String TAG = "FISCAL_CIDADAO_APP";
    public static final String API_URI = "http://www.fiscalcidadao.site/FiscalCidadaoWCF.svc/";
    public static int MINIMO_CARACTERES_DENUNCIA = 10;

    private Retrofit retrofit = null;
    private FiscalCidadaoApi fiscalCidadaoApi = null;

    private Gson gson = null;
    private OkHttpClient okHttpClient = null;

    private Usuario currentUsuario;

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

    public Usuario getCurrentUsuario() {
        return currentUsuario;
    }

    public void setCurrentUsuario(Usuario usuario) {
        this.currentUsuario = usuario;
    }

}
