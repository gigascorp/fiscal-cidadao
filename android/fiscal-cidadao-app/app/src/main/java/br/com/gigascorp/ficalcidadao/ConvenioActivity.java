package br.com.gigascorp.ficalcidadao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;

public class ConvenioActivity extends AppCompatActivity implements View.OnClickListener {

    private Convenio convenio;

    private TextView txtObjeto;
    private TextView txtProponente;
    private TextView txtResponsavel;
    private TextView txtTelefone;
    private TextView txtPeriodo;
    private TextView txtSituacao;
    private TextView txtValor;
    private Button btnDenunciar;

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
        btnDenunciar = (Button) findViewById(R.id.btnDenunciar);
        btnDenunciar.setOnClickListener(this);

        setaCamposNaTela();
    }

    private void setaCamposNaTela(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance();

        numberFormat.setMinimumFractionDigits(2);

        txtObjeto.setText(convenio.getObjeto());
        txtProponente.setText(convenio.getProponente());
        txtResponsavel.setText(convenio.getResponsavel());
        txtTelefone.setText(convenio.getTelefone());
        txtPeriodo.setText("De " + dateFormat.format(convenio.getDataInicio()) + " à " + dateFormat.format(convenio.getDataFim()));
        txtSituacao.setText(convenio.getSituacao());
        txtValor.setText("R$ " + numberFormat.format(convenio.getValor()));

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ConvenioActivity.this, DenunciaActivity.class);
        intent.putExtra("convenio", convenio);
        startActivity(intent);
    }
}
