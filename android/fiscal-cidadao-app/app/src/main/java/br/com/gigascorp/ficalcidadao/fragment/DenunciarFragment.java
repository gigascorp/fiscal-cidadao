/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guilherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

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

package br.com.gigascorp.ficalcidadao.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import br.com.gigascorp.ficalcidadao.FiscalCidadaoApp;
import br.com.gigascorp.ficalcidadao.HomeActivity;
import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.Usuario;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;
import br.com.gigascorp.ficalcidadao.ui.DenunciaGridLayoutManager;
import br.com.gigascorp.ficalcidadao.ui.FotoDenunciaAdapter;
import br.com.gigascorp.ficalcidadao.util.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DenunciarFragment extends GenericFragment implements View.OnClickListener {

    private static final String CONVENIO_KEY = "convenio";
    private static final int CAMERA_RESULT = 1;
    private static final int GALERIA_RESULT = 2;
    public static final String TEMP_IMAGE = "fiscal-cidadao-temp.jpg";

    private Call<ResponseBody> enviarDenunciaCall;

    private Convenio convenio;

    private RecyclerView recyclerView;
    private DenunciaGridLayoutManager layoutManager;
    private EditText edTexto;
    private Button btnEnviar;

    private ScrollView tela;
    private ProgressBar progressBar;

    private ArrayList<FotoHolderWrap> listaFotosThumb = new ArrayList<>();
    private ArrayList<String> listaFotosBase64 = new ArrayList<>();

    private Uri imageUri;

    public static DenunciarFragment getNewIntance(Convenio convenio){

        DenunciarFragment fragment = new DenunciarFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CONVENIO_KEY, convenio);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Denunciar");
        setHasOptionsMenu(true);

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_denunciar, container, false);

        convenio = (Convenio) getArguments().getSerializable(CONVENIO_KEY);

        if (convenio == null) {
            Toast.makeText(super.getActivity(), "Erro ao recuperar o convênio", Toast.LENGTH_SHORT).show();
            return null;
        }

        File image=new File(Environment.getExternalStorageDirectory(),TEMP_IMAGE);
        imageUri=Uri.fromFile(image);

        progressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);
        tela = (ScrollView) layout.findViewById(R.id.tela);

        edTexto = (EditText) layout.findViewById(R.id.txtDenunciaTexto);
        btnEnviar = (Button) layout.findViewById(R.id.btnEnviarDenuncia);
        btnEnviar.setOnClickListener(this);

        recyclerView = (RecyclerView) layout.findViewById(R.id.gridFotos);
        recyclerView.setHasFixedSize(false);
        layoutManager = new DenunciaGridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

        return layout;
    }

    @Override
    public void onClick(View v) {

        String texto = edTexto.getText().toString();

        if(texto == null || texto.trim().equals("") || texto.trim().length() < FiscalCidadaoApp.MINIMO_CARACTERES_DENUNCIA){
            Toast.makeText(getActivity(), "Nos envie um texto com pelo menos " + String.valueOf(FiscalCidadaoApp.MINIMO_CARACTERES_DENUNCIA) +
                    " caracteres junto com a sua denúncia.", Toast.LENGTH_LONG).show();
            return;
        }

        Denuncia denuncia = new Denuncia();
        denuncia.setConvenioId(convenio.getId());
        denuncia.setTexto(texto);
        denuncia.setFotos(listaFotosBase64);

        Usuario usuario = getUsuarioLogado();

        if(usuario != null){
            denuncia.setUsuarioId(usuario.getId());
        }

        progressBar.setVisibility(View.VISIBLE);
        tela.setVisibility(View.INVISIBLE);

        Log.d(FiscalCidadaoApp.TAG, "Vai enviar a requisição");
        enviarDenunciaCall = getFiscalCidadaoApi().enviarDenuncia(denuncia);
        Log.d(FiscalCidadaoApp.TAG, "Requisição enviada");

        enviarDenunciaCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

                Log.d(FiscalCidadaoApp.TAG, "Resposta recebida");

                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {

                    Toast.makeText(DenunciarFragment.this.getActivity(), "Denúncia enviada com sucesso", Toast.LENGTH_LONG).show();

                    HomeActivity activity = (HomeActivity) getActivity();
                    activity.selecionarAba(1);


                } else {
                    Toast.makeText(DenunciarFragment.this.getActivity(), "Erro ao realizara a denúncia.\n" + response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                }

                /*progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);*/
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContextoFiscalCidadaoApp(), "Erro ao realizara a denúncia.", Toast.LENGTH_LONG).show();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            super.onActivityResult(requestCode, resultCode, data);

            try {

                Bitmap cameraBmp = null;

                if( requestCode == CAMERA_RESULT ){
                    cameraBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    Matrix m = new Matrix();
                    m.postRotate(Util.rotacaoNecessaria(new File(imageUri.getPath())));

                    cameraBmp = Bitmap.createBitmap(cameraBmp,
                            0, 0, cameraBmp.getWidth(), cameraBmp.getHeight(),
                            m, true);
                }

                if( requestCode == GALERIA_RESULT ){
                    Uri selectedImage = data.getData();
                    cameraBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                }

                if(cameraBmp == null){
                    Toast.makeText(getActivity(), "Erro ao recuperar a imagem", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap thumb = ThumbnailUtils.extractThumbnail(cameraBmp, 1024, 1024);

                listaFotosThumb = Util.adicionarFoto(listaFotosThumb, new FotoHolderWrap(thumb, listaFotosThumb.size()));

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
                Toast.makeText(getActivity(), "Erro ao recuperar a foto", Toast.LENGTH_SHORT).show();
            }

            return;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.denunciar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.btnActionCamera:

                File img = new File(Environment.getExternalStorageDirectory(), TEMP_IMAGE);

                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(img));
                startActivityForResult(intent, CAMERA_RESULT);

                return true;

            case R.id.btnActionGaleria:

                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALERIA_RESULT);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void excluirFoto(int posicao){

        listaFotosThumb.remove(posicao);
        listaFotosBase64.remove(posicao);

        //Atualiza as posições das fotos na tela
        for(int i=0; i<listaFotosThumb.size(); i++){
            listaFotosThumb.get(i).setPosicao(i);
        }

        FotoDenunciaAdapter adapter = new FotoDenunciaAdapter(listaFotosThumb, this);
        recyclerView.setAdapter(adapter);
    }
}
