package br.com.gigascorp.ficalcidadao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;

public class ConvenioActivity extends AppCompatActivity {

    private Convenio convenio;

    private TextView txtObjeto;
    private TextView txtProponente;
    private TextView txtResponsavel;
    private TextView txtTelefone;
    private TextView txtPeriodo;
    private TextView txtSituacao;
    private TextView txtValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenio);

        convenio = (Convenio) getIntent().getExtras().get("convenio");

        if(convenio == null){
            Toast.makeText(this, "Erro ao recuperar o convênio", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtObjeto = (TextView) findViewById(R.id.txtConvenioObjeto);
        txtProponente = (TextView) findViewById(R.id.txtConvenioPromonente);
        txtResponsavel = (TextView) findViewById(R.id.txtConvenioResponsavel);
        txtTelefone = (TextView) findViewById(R.id.txtConvenioTelefone);
        txtPeriodo = (TextView) findViewById(R.id.txtConvenioPeriodo);
        txtSituacao = (TextView) findViewById(R.id.txtConvenioSituacao);
        txtValor = (TextView) findViewById(R.id.txtConvenioValor);

        setaCamposNaTela();
    }

    private void setaCamposNaTela(){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        txtObjeto.setText(convenio.getObjeto());
        txtProponente.setText(convenio.getProponente());
        txtResponsavel.setText(convenio.getResponsavel());
        txtTelefone.setText(convenio.getTelefone());
        txtPeriodo.setText("De " + format.format(convenio.getDataInicio()) + " à " + format.format(convenio.getDataFim()));
        txtSituacao.setText(convenio.getSituacao());
        txtValor.setText(String.valueOf(convenio.getValor()));

    }

}
