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
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.Ranking;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.DenunciasWrapper;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.RankingResultWrapper;
import br.com.gigascorp.ficalcidadao.ui.DenunciaAdapter;
import br.com.gigascorp.ficalcidadao.ui.DividerItemDecoration;
import br.com.gigascorp.ficalcidadao.ui.RankingAdapter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RankingFragment extends GenericFragment {

    private List<Ranking> lista;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private RelativeLayout tela;
    private ProgressBar progressBar;

    private Call<RankingResultWrapper> rankingResultWrapperCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout    = (RelativeLayout) inflater.inflate(R.layout.fragment_ranking, container, false);

        progressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);
        tela = (RelativeLayout) layout.findViewById(R.id.tela);

        recyclerView = (RecyclerView) layout.findViewById(R.id.listaRanking);
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

        progressBar.setVisibility(View.VISIBLE);
        tela.setVisibility(View.INVISIBLE);

        Log.d(FiscalCidadaoApp.TAG, "Vai enviar a requisição");
        rankingResultWrapperCall = getFiscalCidadaoApi().getRanking(getUsuarioLogado().getId());
        Log.d(FiscalCidadaoApp.TAG, "Requisição enviada");

        rankingResultWrapperCall.enqueue(new Callback<RankingResultWrapper>() {
            @Override
            public void onResponse(Response<RankingResultWrapper> response, Retrofit retrofit) {

                Log.d(FiscalCidadaoApp.TAG, "Resposta recebida");

                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {

                    RankingResultWrapper wrapper = response.body();

                    if (wrapper.getGetRankingResult() == null ) {
                        Toast.makeText(RankingFragment.super.getActivity(), "Erro ao recuperar o ranking", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    lista = wrapper.getGetRankingResult().getLista();

                    RankingAdapter adapter = new RankingAdapter(lista);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(RankingFragment.super.getActivity(), "Erro ao recuperar o ranking\n" + response.code() + "" + response.message(), Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContextoFiscalCidadaoApp(), "Erro ao recuperar o ranking", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });

    }
}
