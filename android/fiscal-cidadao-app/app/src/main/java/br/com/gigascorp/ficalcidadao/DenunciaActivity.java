package br.com.gigascorp.ficalcidadao;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;
import br.com.gigascorp.ficalcidadao.ui.ConvenioAdapter;
import br.com.gigascorp.ficalcidadao.ui.ConvenioLinearLayoutManager;
import br.com.gigascorp.ficalcidadao.ui.FotoDenunciaAdapter;
import br.com.gigascorp.ficalcidadao.util.Util;

public class DenunciaActivity extends AppCompatActivity {

    public static int CAMERA_RESULT = 1;
    public static String TEMP_IMAGE = "fiscal-cidadao-temp.jpg";

    private Convenio convenio;

    private RecyclerView reciclerView;
    private GridLayoutManager layoutManager;
    private TextView txtObjeto;

    private ArrayList<FotoHolderWrap> listaFotos = new ArrayList<>();
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

        reciclerView = (RecyclerView) findViewById(R.id.gridFotos);
        reciclerView.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        reciclerView.setLayoutManager(layoutManager);

        //Inclui o botão de adicionar fotos
        listaFotos.add(Util.criarBotaoAdicionarFoto(this));

        FotoDenunciaAdapter adapter = new FotoDenunciaAdapter(listaFotos, this);
        reciclerView.setAdapter(adapter);
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

                listaFotos = Util.adicionarFoto(this, listaFotos, new FotoHolderWrap(thumb));

                FotoDenunciaAdapter adapter = new FotoDenunciaAdapter(listaFotos, this);
                reciclerView.setAdapter(adapter);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao recuperar a foto", Toast.LENGTH_SHORT).show();
            }

            return;
        }
    }

}
