package br.com.gigascorp.ficalcidadao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;

public class DenunciaActivity extends AppCompatActivity {

    private Convenio convenio;

    private TextView txtObjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        convenio = (Convenio) getIntent().getExtras().get("convenio");

        if(convenio == null){
            Toast.makeText(this, "Erro ao recuperar o convênio", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtObjeto = (TextView) findViewById(R.id.txtDenuciaConvenio);
        txtObjeto.setText(convenio.getObjeto());
    }

}
