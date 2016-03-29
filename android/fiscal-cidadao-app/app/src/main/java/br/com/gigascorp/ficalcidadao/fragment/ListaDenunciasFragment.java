package br.com.gigascorp.ficalcidadao.fragment;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import br.com.gigascorp.ficalcidadao.R;

public class ListaDenunciasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout    = (RelativeLayout) inflater.inflate(R.layout.fragment_lista_denuncias, container, false);

        return layout;
    }
}
