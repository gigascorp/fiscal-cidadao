package br.com.gigascorp.ficalcidadao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class ConvenioFragment extends GenericFragment implements View.OnClickListener {

    private static final String CONVENIO_KEY = "convenio";

    private Convenio convenio;

    private TextView txtObjeto;
    private TextView txtProponente;
    private TextView txtResponsavel;
    private TextView txtTelefone;
    private TextView txtPeriodo;
    private TextView txtSituacao;
    private TextView txtValor;
    private Button btnDenunciar;

    public static ConvenioFragment getNewIntance(Convenio convenio){

        ConvenioFragment fragment = new ConvenioFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CONVENIO_KEY, convenio);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Convênio");

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_convenio, container, false);

        convenio = (Convenio) getArguments().getSerializable(CONVENIO_KEY);

        if(convenio == null){
            Toast.makeText(super.getActivity(), "Erro ao recuperar o convênio", Toast.LENGTH_SHORT).show();
            return null;
        }

        txtObjeto = (TextView) layout.findViewById(R.id.txtConvenioObjeto);
        txtProponente = (TextView) layout.findViewById(R.id.txtConvenioPromonente);
        txtResponsavel = (TextView) layout.findViewById(R.id.txtConvenioResponsavel);
        txtTelefone = (TextView) layout.findViewById(R.id.txtConvenioTelefone);
        txtPeriodo = (TextView) layout.findViewById(R.id.txtConvenioPeriodo);
        txtSituacao = (TextView) layout.findViewById(R.id.txtConvenioSituacao);
        txtValor = (TextView) layout.findViewById(R.id.txtConvenioValor);
        btnDenunciar = (Button) layout.findViewById(R.id.btnDenunciar);
        btnDenunciar.setOnClickListener(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance();

        numberFormat.setMinimumFractionDigits(2);

        txtObjeto.setText(convenio.getObjeto());
        txtProponente.setText(convenio.getProponente());
        txtResponsavel.setText(convenio.getResponsavel());
        txtTelefone.setText(convenio.getTelefone());
        txtPeriodo.setText("De " + dateFormat.format(convenio.getDataInicio()) + " à " + dateFormat.format(convenio.getDataFim()));
        txtSituacao.setText(convenio.getSituacao());
        txtValor.setText("R$ " + numberFormat.format(convenio.getValor()));

        return layout;
    }

    @Override
    public void onClick(View v) {

        DenunciarFragment fragment = DenunciarFragment.getNewIntance(convenio);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.telaHome, fragment)
                .addToBackStack(null)
                .commit();
    }
}
