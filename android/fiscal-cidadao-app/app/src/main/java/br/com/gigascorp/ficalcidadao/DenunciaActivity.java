package br.com.gigascorp.ficalcidadao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;
import br.com.gigascorp.ficalcidadao.ui.ConvenioAdapter;
import br.com.gigascorp.ficalcidadao.ui.ConvenioLinearLayoutManager;
import br.com.gigascorp.ficalcidadao.ui.FotoDenunciaAdapter;
import br.com.gigascorp.ficalcidadao.util.Util;

public class DenunciaActivity extends AppCompatActivity {

    private Convenio convenio;

    private RecyclerView reciclerView;
    private GridLayoutManager layoutManager;
    private TextView txtObjeto;

    private ArrayList<FotoHolderWrap> listaFotos = new ArrayList<>();

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


        reciclerView = (RecyclerView) findViewById(R.id.gridFotos);
        reciclerView.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        reciclerView.setLayoutManager(layoutManager);

        //Inclui o botão de adicionar fotos
        listaFotos.add(Util.criarBotaoAdicionarFoto(this));

        FotoDenunciaAdapter adapter = new FotoDenunciaAdapter(listaFotos);
        reciclerView.setAdapter(adapter);
    }

}
