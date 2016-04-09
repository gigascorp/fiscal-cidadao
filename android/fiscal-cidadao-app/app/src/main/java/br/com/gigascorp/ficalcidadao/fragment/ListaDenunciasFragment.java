package br.com.gigascorp.ficalcidadao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import br.com.gigascorp.ficalcidadao.FiscalCidadaoApp;
import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.DenunciasWrapper;
import br.com.gigascorp.ficalcidadao.ui.ConvenioAdapter;
import br.com.gigascorp.ficalcidadao.ui.DenunciaAdapter;
import br.com.gigascorp.ficalcidadao.ui.DividerItemDecoration;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ListaDenunciasFragment extends GenericFragment {

    private List<Denuncia> denuncias;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private RelativeLayout tela;
    private ProgressBar progressBar;

    private Call<DenunciasWrapper> denunciasWrapperCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout    = (RelativeLayout) inflater.inflate(R.layout.fragment_lista_denuncias, container, false);

        progressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);
        tela = (RelativeLayout) layout.findViewById(R.id.tela);

        recyclerView = (RecyclerView) layout.findViewById(R.id.listaDenuncias);
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(ActivityCompat.getDrawable(super.getActivity(), R.drawable.divisor)));
        layoutManager = new LinearLayoutManager(super.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Após recuperar a localização, chama a API para encontrar os convênios das proximidades
        progressBar.setVisibility(View.VISIBLE);
        tela.setVisibility(View.INVISIBLE);

        Log.d(FiscalCidadaoApp.TAG, "Vai enviar a requisição");
        denunciasWrapperCall = getFiscalCidadaoApi().getDenuncias(getUsuarioLogado().getId());
        Log.d(FiscalCidadaoApp.TAG, "Requisição enviada");

        denunciasWrapperCall.enqueue(new Callback<DenunciasWrapper>() {
            @Override
            public void onResponse(Response<DenunciasWrapper> response, Retrofit retrofit) {

                Log.d(FiscalCidadaoApp.TAG, "Resposta recebida");

                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {

                    DenunciasWrapper denunciasWrapper = response.body();

                    if (denunciasWrapper.getGetDenunciaByUsuarioResult() == null) {
                        Toast.makeText(ListaDenunciasFragment.super.getActivity(), "Erro ao recuperar denúncias", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    denuncias = denunciasWrapper.getGetDenunciaByUsuarioResult();

                    DenunciaAdapter adapter = new DenunciaAdapter(denuncias);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(ListaDenunciasFragment.super.getActivity(), "Erro ao recuperar as suas denúncias\n" + response.code() + "" + response.message(), Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContextoFiscalCidadaoApp(), "Erro ao recuperar os convênios da sua região", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });

    }
}
