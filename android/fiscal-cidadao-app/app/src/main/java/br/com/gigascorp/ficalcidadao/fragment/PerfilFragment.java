/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guiherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

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

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.FiscalCidadaoApp;
import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Perfil;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.DenunciasWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.PerfilWrapper;
import br.com.gigascorp.ficalcidadao.ui.DenunciaAdapter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PerfilFragment extends GenericFragment {

    private Perfil perfil;

    private ImageView imgFotoPerfil;
    private TextView txtNome;
    private TextView txtDesde;
    private TextView txtDenunciasRealizadas;
    private TextView txtPontosSomados;

    private RelativeLayout tela;
    private ProgressBar progressBar;

    private Call<PerfilWrapper> perfilCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_perfil, container, false);

        progressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);
        tela = (RelativeLayout) layout.findViewById(R.id.tela);

        imgFotoPerfil = (ImageView) layout.findViewById(R.id.imgFotoPerfil);
        txtNome = (TextView) layout.findViewById(R.id.txtNome);
        txtDesde = (TextView) layout.findViewById(R.id.txtDesde);
        txtDenunciasRealizadas = (TextView) layout.findViewById(R.id.txtDenunciasRealizadas);
        txtPontosSomados = (TextView) layout.findViewById(R.id.txtPontosSomados);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);
        tela.setVisibility(View.INVISIBLE);

        Log.d(FiscalCidadaoApp.TAG, "Vai enviar a requisição");
        Log.d(FiscalCidadaoApp.TAG, "Perfil id: " + getUsuarioLogado().getId());
        perfilCall = getFiscalCidadaoApi().getPerfil(getUsuarioLogado().getId());
        Log.d(FiscalCidadaoApp.TAG, "Requisição enviada");

        perfilCall.enqueue(new Callback<PerfilWrapper>() {
            @Override
            public void onResponse(Response<PerfilWrapper> response, Retrofit retrofit) {

                Log.d(FiscalCidadaoApp.TAG, "Resposta recebida");

                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {

                    PerfilWrapper wrapper = response.body();

                    if (wrapper == null || wrapper.getGetUsuarioResult() == null) {
                        Toast.makeText(getContextoFiscalCidadaoApp(), "Erro ao recuperar o seu perfil", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    perfil = wrapper.getGetUsuarioResult();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    txtNome.setText(perfil.getNome());
                    txtDesde.setText("Fiscal Cidadão desde " + dateFormat.format(perfil.getDataCadastro()));
                    txtDenunciasRealizadas.setText("Realizou " + perfil.getTotalDenuncias() + " denúncias");
                    txtPontosSomados.setText("e somou " + perfil.getPontuacao() + " pontos até agora!!");

                    ImageLoader imageLoader = ImageLoader.getInstance();

                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .displayer(new RoundedBitmapDisplayer(1000))
                            .showImageOnLoading(R.drawable.ic_cached_black_36dp)
                            .considerExifParams(true)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .build();

                    imageLoader.displayImage(perfil.getFotoPerfil(), imgFotoPerfil, options);

                } else {
                    Toast.makeText(getContextoFiscalCidadaoApp(), "Erro ao recuperar as suas denúncias\n" + response.code() + "" + response.message(), Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContextoFiscalCidadaoApp(), "Erro ao recuperar o seu perfil", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });

    }
}
