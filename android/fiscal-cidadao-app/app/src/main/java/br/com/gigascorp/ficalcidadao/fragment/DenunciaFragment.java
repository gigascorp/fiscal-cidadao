package br.com.gigascorp.ficalcidadao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.Denuncia;

public class DenunciaFragment extends GenericFragment {

    private static final String DENUNCIA_KEY = "denuncia";

    private Denuncia denuncia;

    private TextView txtObjeto;
    private TextView txtProponente;
    private TextView txtResponsavel;
    private TextView txtTelefone;
    private TextView txtPeriodo;
    private TextView txtSituacao;
    private TextView txtValor;
    private TextView txtDenuncia;
    private TextView txtDataDenuncia;
    private TextView txtParecerDenuncia;

    public static DenunciaFragment getNewIntance(Denuncia denuncia){

        DenunciaFragment fragment = new DenunciaFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DENUNCIA_KEY, denuncia);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Convênio");

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_denuncia, container, false);

        denuncia = (Denuncia) getArguments().getSerializable(DENUNCIA_KEY);

        if(denuncia == null){
            Toast.makeText(super.getActivity(), "Erro ao recuperar a denúncia", Toast.LENGTH_SHORT).show();
            return null;
        }

        txtObjeto = (TextView) layout.findViewById(R.id.txtConvenioObjeto);
        txtProponente = (TextView) layout.findViewById(R.id.txtConvenioPromonente);
        txtResponsavel = (TextView) layout.findViewById(R.id.txtConvenioResponsavel);
        txtTelefone = (TextView) layout.findViewById(R.id.txtConvenioTelefone);
        txtPeriodo = (TextView) layout.findViewById(R.id.txtConvenioPeriodo);
        txtSituacao = (TextView) layout.findViewById(R.id.txtConvenioSituacao);
        txtValor = (TextView) layout.findViewById(R.id.txtConvenioValor);
        txtDenuncia = (TextView) layout.findViewById(R.id.txtDenuncia);
        txtDataDenuncia = (TextView) layout.findViewById(R.id.txtDataDenuncia);
        txtParecerDenuncia = (TextView) layout.findViewById(R.id.txtParecerDenuncia);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance();

        numberFormat.setMinimumFractionDigits(2);

        txtObjeto.setText(denuncia.getConvenio().getObjeto());
        txtProponente.setText(denuncia.getConvenio().getProponente());
        txtResponsavel.setText(denuncia.getConvenio().getResponsavel());
        txtTelefone.setText(denuncia.getConvenio().getTelefone());
        txtPeriodo.setText("De " + dateFormat.format(denuncia.getConvenio().getDataInicio()) + " à " + dateFormat.format(denuncia.getConvenio().getDataFim()));
        txtSituacao.setText(denuncia.getConvenio().getSituacao());
        txtValor.setText("R$ " + numberFormat.format(denuncia.getConvenio().getValor()));
        txtDenuncia.setText(denuncia.getTexto());
        txtDataDenuncia.setText(dateFormat.format(denuncia.getDataDenuncia()));
        txtParecerDenuncia.setText(denuncia.getParecer());

        return layout;
    }

}
