package br.com.gigascorp.ficalcidadao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.fragment.RankingFragment;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;
import br.com.gigascorp.ficalcidadao.modelo.Ranking;

public class RankingAdapter extends RecyclerView.Adapter<RankingViewHolder> {

    private RankingFragment fragment;
    private List<Ranking> lista;

    public RankingAdapter(List<Ranking> lista, RankingFragment fragment){
        this.lista = lista;
        this.fragment = fragment;
    }

    @Override
    public RankingViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item_view, parent, false);
        return new RankingViewHolder(itemView, fragment);
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        Ranking r = lista.get(position);
        holder.setRanking(r);
    }

    @Override
    public int getItemCount() {
        if(lista == null)
            return 0;

        return lista.size();
    }
}
