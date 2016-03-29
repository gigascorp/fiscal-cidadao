package br.com.gigascorp.ficalcidadao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import br.com.gigascorp.ficalcidadao.R;

public class PerfilFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_perfil, container, false);

        return layout;
    }
}
