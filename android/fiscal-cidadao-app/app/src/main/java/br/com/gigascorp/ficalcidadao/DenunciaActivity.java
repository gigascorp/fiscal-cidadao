package br.com.gigascorp.ficalcidadao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.ConveniosWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;
import br.com.gigascorp.ficalcidadao.ui.DenunciaGridLayoutManager;
import br.com.gigascorp.ficalcidadao.ui.FotoDenunciaAdapter;
import br.com.gigascorp.ficalcidadao.util.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class DenunciaActivity extends AppCompatActivity implements View.OnClickListener {

    /* Remover daqui as coisas do retrofit e botar numa superclasse */
    private static final String API_URI = "http://www.fiscalcidadao.site/FiscalCidadaoWCF.svc/";
    private static final String TAG = "FISCAL-CIDADAO";

    private Retrofit retrofit;
    private FiscalCidadaoApi fiscalApi;
    private Call<ResponseBody> enviarDenunciaCall;


    public static int CAMERA_RESULT = 1;
    public static String TEMP_IMAGE = "fiscal-cidadao-temp.jpg";

    private Convenio convenio;

    private RecyclerView reciclerView;
    private DenunciaGridLayoutManager layoutManager;
    private TextView txtObjeto;
    private EditText edTexto;
    private Button btnEnviar;

    private ArrayList<FotoHolderWrap> listaFotosThumb = new ArrayList<>();
    private ArrayList<String> listaFotosBase64 = new ArrayList<>();

    private Uri imageUri;

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

        File image=new File(Environment.getExternalStorageDirectory(),TEMP_IMAGE);
        imageUri=Uri.fromFile(image);

        txtObjeto = (TextView) findViewById(R.id.txtDenuciaConvenio);
        txtObjeto.setText(convenio.getObjeto());
        edTexto = (EditText) findViewById(R.id.txtDenunciaTexto);
        btnEnviar = (Button) findViewById(R.id.btnEnviarDenuncia);
        btnEnviar.setOnClickListener(this);

        reciclerView = (RecyclerView) findViewById(R.id.gridFotos);
        reciclerView.setHasFixedSize(false);
        layoutManager = new DenunciaGridLayoutManager(this, 3);
        reciclerView.setLayoutManager(layoutManager);

        //Inclui o botão de adicionar fotos
        listaFotosThumb.add(Util.criarBotaoAdicionarFoto(this));

        FotoDenunciaAdapter adapter = new FotoDenunciaAdapter(listaFotosThumb, this);
        reciclerView.setAdapter(adapter);


        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yy")
                .create();

        //Inicializando o retrofit para a url da API
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(180, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(180, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        fiscalApi = retrofit.create(FiscalCidadaoApi.class);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);

            try {
                Bitmap cameraBmp = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri );

                Matrix m = new Matrix();
                m.postRotate(Util.rotacaoNecessaria(new File(imageUri.getPath())));

                cameraBmp = Bitmap.createBitmap(cameraBmp,
                        0, 0, cameraBmp.getWidth(), cameraBmp.getHeight(),
                        m, true);

                Bitmap thumb = ThumbnailUtils.extractThumbnail(cameraBmp, 1024, 1024);

                listaFotosThumb = Util.adicionarFoto(this, listaFotosThumb, new FotoHolderWrap(thumb));

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                cameraBmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] imageBytes = baos.toByteArray();

                listaFotosBase64.add(Base64.encodeToString(imageBytes, Base64.DEFAULT));

                FotoDenunciaAdapter adapter = new FotoDenunciaAdapter(listaFotosThumb, this);
                reciclerView.setAdapter(adapter);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao recuperar a foto", Toast.LENGTH_SHORT).show();
            }

            return;
        }
    }

    @Override
    public void onClick(View v) {

        String texto = edTexto.getText().toString();

        if(texto == null || texto.trim().equals("")){
            Toast.makeText(this, "Nos envie algum texto junto com a sua denúncia.", Toast.LENGTH_LONG).show();
            return;
        }

        Denuncia denuncia = new Denuncia();
        denuncia.setConvenioId(convenio.getId());
        denuncia.setTexto(texto);
        denuncia.setFotos(listaFotosBase64);


        enviarDenunciaCall = fiscalApi.enviarDenuncia(denuncia);

        enviarDenunciaCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {
                    ResponseBody body = response.body();

                    Log.d(TAG, body.toString());

                } else {

                    Toast.makeText(DenunciaActivity.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(DenunciaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }
}
