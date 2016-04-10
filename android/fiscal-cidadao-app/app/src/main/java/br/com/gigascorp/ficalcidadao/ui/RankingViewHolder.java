package br.com.gigascorp.ficalcidadao.ui;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.FiscalCidadaoApp;
import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.DenunciaFragment;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.Ranking;

public class RankingViewHolder extends RecyclerView.ViewHolder {

    private Ranking ranking;

    private TextView txtRankingNome;
    private TextView txtRankingPontuacao;

    public RankingViewHolder(View v) {
        super(v);
        txtRankingNome = (TextView) v.findViewById(R.id.txtRankingNome);
        txtRankingPontuacao = (TextView) v.findViewById(R.id.txtRankingPontuacao);
    }

    public void setRanking(Ranking ranking){
        this.ranking = ranking;
        txtRankingNome.setText(ranking.getNome());
        txtRankingPontuacao.setText("Pontuação: " + ranking.getPontuacao());
    }

}
