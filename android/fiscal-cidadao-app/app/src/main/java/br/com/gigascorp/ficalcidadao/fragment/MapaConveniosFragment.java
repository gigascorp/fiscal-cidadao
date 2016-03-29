package br.com.gigascorp.ficalcidadao.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.ConveniosWrapper;
import br.com.gigascorp.ficalcidadao.ui.ConvenioAdapter;
import br.com.gigascorp.ficalcidadao.ui.DividerItemDecoration;
import br.com.gigascorp.ficalcidadao.util.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.google.android.gms.location.LocationServices.API;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class MapaConveniosFragment extends ClienteApiFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener, SlidingUpPanelLayout.PanelSlideListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private List<Convenio> convenios = null;
    private Map<Marker, List<Convenio>> marcadoresConvenio = new HashMap<Marker, List<Convenio>>();

    private Call<ConveniosWrapper> conveniosProximosCall;
    private GoogleApiClient googleApiClient = null;

    private RecyclerView reciclerViewConvenios;
    private LinearLayoutManager layoutManager;
    private SlidingUpPanelLayout slidingLayout;

    private RelativeLayout tela;
    private ProgressBar progressBar;

    private SlidingUpPanelLayout.PanelState ultimoEstado = SlidingUpPanelLayout.PanelState.HIDDEN;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_mapa_convenios, container, false);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(super.getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(API)
                    .build();
        }

        progressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);
        tela = (RelativeLayout) layout.findViewById(R.id.tela);

        //Inicializando o slidingPanel e lista com cardviews
        slidingLayout = (SlidingUpPanelLayout) layout.findViewById(R.id.sliding_layout);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        slidingLayout.addPanelSlideListener(this);

        reciclerViewConvenios = (RecyclerView) layout.findViewById(R.id.cardList);
        reciclerViewConvenios.setHasFixedSize(false);
        reciclerViewConvenios.addItemDecoration(new DividerItemDecoration(ActivityCompat.getDrawable(super.getActivity(), R.drawable.divisor)));
        layoutManager = new LinearLayoutManager(super.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reciclerViewConvenios.setLayoutManager(layoutManager);

        return layout;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //Se a lista já tiver sido carregada, não carrega novamente
        if(convenios != null && convenios.size() > 0){
            return;
        }

        Location localizacao;

        localizacao = FusedLocationApi.getLastLocation(googleApiClient);
        if (localizacao == null) {
            Toast.makeText(super.getActivity(), "Localização indisponível", Toast.LENGTH_SHORT).show();
            return;
        }

        //Após recuperar a localização, chama a API para encontrar os convênios das proximidades
        progressBar.setVisibility(View.VISIBLE);
        tela.setVisibility(View.INVISIBLE);

        Log.d(TAG, "Vai enviar a requisição");
        conveniosProximosCall = fiscalApi.conveniosProximos(localizacao.getLatitude(), localizacao.getLongitude());
        Log.d(TAG, "Requisição enviada");

        conveniosProximosCall.enqueue(new Callback<ConveniosWrapper>() {
            @Override
            public void onResponse(Response<ConveniosWrapper> response, Retrofit retrofit) {

                Log.d(TAG, "Resposta recebida");

                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {

                    ConveniosWrapper conveniosWrapper = response.body();

                    if (conveniosWrapper.getGetConveniosByCoordinateResult() == null) {
                        Toast.makeText(MapaConveniosFragment.super.getActivity(), "Erro ao recuperar convênios", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    convenios = conveniosWrapper.getGetConveniosByCoordinateResult().getListaConvenios();


                    SupportMapFragment mapFragment = new SupportMapFragment(){
                        @Override
                        public void onActivityCreated(Bundle savedInstanceState) {
                            super.onActivityCreated(savedInstanceState);
                            getMapAsync(MapaConveniosFragment.this);
                        }
                    };

                    getChildFragmentManager().beginTransaction().add(R.id.tela, mapFragment).commit();



                } else {
                    Toast.makeText(MapaConveniosFragment.super.getActivity(), "Erro ao recuperar os convênios da sua região\n" + response.code() + "" + response.message(), Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MapaConveniosFragment.super.getActivity(), "Erro ao recuperar os convênios da sua região", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                tela.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);

        LatLngBounds.Builder builder = LatLngBounds.builder();

        for (Convenio convenio : convenios) {

            Marker marcador = Util.getConvenioNaMesmaLocalizacao(marcadoresConvenio, convenio);
            List<Convenio> conveniosDoMarcador = null;

            //Se não houver (ainda) nenhum convênio para esta mesma localização, cria o marcador
            if (marcador == null) {
                LatLng coord = new LatLng(convenio.getLat(), convenio.getLng());
                builder.include(coord);
                marcador = map.addMarker(new MarkerOptions().position(coord));

                conveniosDoMarcador = new ArrayList<>();
            } else { //Se já houver, recupera os convênios deste marcador
                conveniosDoMarcador = marcadoresConvenio.get(marcador);
            }

            //Adiciona o novo marcador
            conveniosDoMarcador.add(convenio);
            marcadoresConvenio.put(marcador, conveniosDoMarcador);
        }

        if (marcadoresConvenio != null && marcadoresConvenio.size() > 0) {

            if (marcadoresConvenio.size() == 1) {
                //Se tiver somente um marcador, dá um Zoom num nível da cidade
                //Obs.: Como só há um marcador, todos os convênios apontam para a mesma locaçlização
                if (convenios.get(0) != null) {
                    Convenio c = convenios.get(0);
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(c.getLat(), c.getLng()), 10F));
                } else {
                    //Se houver mais de um marcador, dá um zoom considerando todos os pontos (bound)
                    LatLngBounds bounds = builder.build();
                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                }
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        List<Convenio> selecionados = marcadoresConvenio.get(marker);

        ConvenioAdapter adapter = new ConvenioAdapter(selecionados);
        reciclerViewConvenios.setAdapter(adapter);

        //Seta a altura do slidepanel
        int height = Util.dpToPx(80);
        height = height * selecionados.size();

        DisplayMetrics dm = new DisplayMetrics();
        super.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightTela = dm.heightPixels;

        if (height > heightTela * 2 / 5) {
            height = heightTela * 2 / 5;
        }

        slidingLayout.setPanelHeight(height);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        return false;
    }

    @Override
    public void onPanelSlide(View view, float v) {

    }

    @Override
    public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

        if(ultimoEstado == SlidingUpPanelLayout.PanelState.COLLAPSED && newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
            ultimoEstado = SlidingUpPanelLayout.PanelState.HIDDEN;
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            return;
        }

        if(newState != SlidingUpPanelLayout.PanelState.DRAGGING){
            ultimoEstado = newState;
        }

    }

    @Override
    public void onStart() {
        if (googleApiClient != null)
            googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onPause() {
        if (slidingLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (conveniosProximosCall != null)
            conveniosProximosCall.cancel();

        if (googleApiClient != null)
            googleApiClient.disconnect();

        super.onStop();
    }
}
