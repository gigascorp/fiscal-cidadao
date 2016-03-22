package br.com.gigascorp.ficalcidadao;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import retrofit.Retrofit;

public class GenericActivity extends AppCompatActivity {

    protected static final String API_URI = "http://www.fiscalcidadao.site/FiscalCidadaoWCF.svc/";
    protected static final String TAG = "FISCAL-CIDADAO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
