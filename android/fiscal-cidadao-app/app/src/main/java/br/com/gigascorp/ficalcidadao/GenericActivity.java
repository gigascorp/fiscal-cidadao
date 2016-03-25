package br.com.gigascorp.ficalcidadao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GenericActivity extends AppCompatActivity {

    protected static final String API_URI = "http://www.fiscalcidadao.site/FiscalCidadaoWCF.svc/";
    protected static final String TAG = "FISCAL-CIDADAO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
