package br.com.gigascorp.ficalcidadao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;
import br.com.gigascorp.ficalcidadao.ui.DenunciaGridLayoutManager;
import br.com.gigascorp.ficalcidadao.ui.FotoDenunciaAdapter;
import br.com.gigascorp.ficalcidadao.util.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DenunciaActivity extends ClienteApiActivity implements View.OnClickListener {

    private Call<ResponseBody> enviarDenunciaCall;


    public static int CAMERA_RESULT = 1;
    public static String TEMP_IMAGE = "fiscal-cidadao-temp.jpg";

    private Convenio convenio;

    private RecyclerView recyclerView;
    private DenunciaGridLayoutManager layoutManager;
    private TextView txtObjeto;
    private EditText edTexto;
    private Button btnEnviar;

    private ScrollView tela;
    private ProgressBar progressBar;

    private ArrayList<FotoHolderWrap> listaFotosThumb = new ArrayList<>();
    private ArrayList<String> listaFotosBase64 = new ArrayList<>();

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        convenio = (Convenio) getIntent().getExtras().get("convenio");

        // Debug
//        if(convenio == null){
//            convenio = new Convenio();
//            convenio.setId(61);
//            convenio.setObjeto("Teste");
//        }

        File image=new File(Environment.getExternalStorageDirectory(),TEMP_IMAGE);
        imageUri=Uri.fromFile(image);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tela = (ScrollView) findViewById(R.id.tela);

        txtObjeto = (TextView) findViewById(R.id.txtDenuciaConvenio);
        txtObjeto.setText(convenio.getObjeto());
        edTexto = (EditText) findViewById(R.id.txtDenunciaTexto);
        btnEnviar = (Button) findViewById(R.id.btnEnviarDenuncia);
        btnEnviar.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.gridFotos);
        recyclerView.setHasFixedSize(false);
        layoutManager = new DenunciaGridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);


        //Inclui o botão de adicionar fotos
        listaFotosThumb.add(Util.criarBotaoAdicionarFoto(this));

        FotoDenunciaAdapter adapter = new FotoDenunciaAdapter(listaFotosThumb, this);
        recyclerView.setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);

            try {
                Bitmap cameraBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                Matrix m = new Matrix();
                m.postRotate(Util.rotacaoNecessaria(new File(imageUri.getPath())));

                cameraBmp = Bitmap.createBitmap(cameraBmp,
                        0, 0, cameraBmp.getWidth(), cameraBmp.getHeight(),
                        m, true);

                Bitmap thumb = ThumbnailUtils.extractThumbnail(cameraBmp, 1024, 1024);

                listaFotosThumb = Util.adicionarFoto(this, listaFotosThumb, new FotoHolderWrap(thumb));

                cameraBmp = Util.redimensionar(cameraBmp);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                cameraBmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] imageBytes = baos.toByteArray();

                listaFotosBase64.add(Base64.encodeToString(imageBytes, Base64.DEFAULT));

                cameraBmp.recycle();

                FotoDenunciaAdapter adapter = new FotoDenunciaAdapter(listaFotosThumb, this);
                recyclerView.setAdapter(adapter);

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

        // Add current User if any.
        FiscalCidadaoApp app = (FiscalCidadaoApp) this.getApplication();

        if(app.getCurrentUsuario() != null)
        {
            denuncia.setUsuarioId(app.getCurrentUsuario().getId());
        }

        progressBar.setVisibility(View.VISIBLE);
        tela.setVisibility(View.INVISIBLE);

        Log.d(TAG, "Vai enviar a requisição");
        enviarDenunciaCall = fiscalApi.enviarDenuncia(denuncia);
        Log.d(TAG, "Requisição enviada");

        enviarDenunciaCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

                Log.d(TAG, "Resposta recebida");

                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {

                    Toast.makeText(DenunciaActivity.this, "Denúncia enviada com sucesso", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(DenunciaActivity.this, MapaConveniosActivity.class);
                    intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity(intent);

                } else {
                    Toast.makeText(DenunciaActivity.this, "Erro ao realizara a denúncia.\n" + response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(DenunciaActivity.this, "Erro ao realizara a denúncia.", Toast.LENGTH_LONG).show();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
            }
        });

    }
}
