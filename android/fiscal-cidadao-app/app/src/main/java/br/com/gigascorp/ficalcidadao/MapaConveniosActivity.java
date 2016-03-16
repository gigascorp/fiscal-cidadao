package br.com.gigascorp.ficalcidadao;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.ConveniosWrapper;
import br.com.gigascorp.ficalcidadao.ui.ConvenioAdapter;
import br.com.gigascorp.ficalcidadao.ui.ConvenioLinearLayoutManager;
import br.com.gigascorp.ficalcidadao.util.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.google.android.gms.location.LocationServices.*;

public class MapaConveniosActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, SlidingUpPanelLayout.PanelSlideListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String API_URI = "http://www.emilioweba.com/FiscalCidadaoWCF.svc/";
    private static final String TAG = "FISCAL-CIDADAO";

    private List<Convenio> convenios;
    private Map<Marker, List<Convenio>> marcadoresConvenio = new HashMap<Marker, List<Convenio>>();

    private Retrofit retrofit;
    private FiscalCidadaoApi fiscalApi;
    private Call<ConveniosWrapper> conveniosProximosCall;

    private RecyclerView reciclerViewConvenios;
    private ConvenioLinearLayoutManager layoutManager;
    private SlidingUpPanelLayout slidingLayout;

    private GoogleApiClient googleApiClient = null;

    private SlidingUpPanelLayout.PanelState ultimoEstado = SlidingUpPanelLayout.PanelState.HIDDEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa_convenios);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(API)
                    .build();
        }

        //Inicializando o slidingPanel e lista com cardviews
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        slidingLayout.addPanelSlideListener(this);

        reciclerViewConvenios = (RecyclerView) findViewById(R.id.cardList);
        reciclerViewConvenios.setHasFixedSize(false);
        layoutManager = new ConvenioLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reciclerViewConvenios.setLayoutManager(layoutManager);

        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yy")
                .create();

        //Inicializando o retrofit para a url da API
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        fiscalApi = retrofit.create(FiscalCidadaoApi.class);

    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.setOnMarkerClickListener(MapaConveniosActivity.this);
        map.setOnMapClickListener(MapaConveniosActivity.this);

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

        int height = Util.dpToPx(80);
        height = height * selecionados.size();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightTela = dm.heightPixels;

        if (height > heightTela * 2 / 5) {
            height = heightTela * 2 / 5;
        }

        slidingLayout.setPanelHeight(height);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }


    @Override
    public void onBackPressed() {
        if (slidingLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        if (googleApiClient != null)
            googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onPause() {
        if (slidingLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (conveniosProximosCall != null)
            conveniosProximosCall.cancel();

        if (googleApiClient != null)
            googleApiClient.disconnect();

        super.onStop();
    }

    @Override
    public void onPanelSlide(View view, float slideOffset) {
    }

    @Override
    public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

        Log.i(TAG, "OnPanelStateChanged " + previousState + "->" + newState);

        if(ultimoEstado == SlidingUpPanelLayout.PanelState.COLLAPSED && newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
            ultimoEstado = SlidingUpPanelLayout.PanelState.HIDDEN;
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            return;
        }

        if(newState != SlidingUpPanelLayout.PanelState.DRAGGING){
            ultimoEstado = newState;
        }

        if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            layoutManager.scrollToPositionWithOffset(0, 0);
            layoutManager.setScrool(false);
            return;
        }

        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            layoutManager.setScrool(true);
            return;
        }
    }

    //Eventos do Google API Client
    @Override
    public void onConnected(Bundle bundle) {
        Location localizacao;

        localizacao = FusedLocationApi.getLastLocation(googleApiClient);
        if (localizacao == null) {
            Toast.makeText(this, "Localização indisponível", Toast.LENGTH_SHORT).show();
            return;
        }

        //Após recuperar a localização, chama a API para encontrar os convênios das proximidades
        conveniosProximosCall = fiscalApi.conveniosProximos(localizacao.getLatitude(), localizacao.getLongitude());

        conveniosProximosCall.enqueue(new Callback<ConveniosWrapper>() {
            @Override
            public void onResponse(Response<ConveniosWrapper> response, Retrofit retrofit) {
                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {

                    ConveniosWrapper conveniosWrapper = response.body();

                    if (conveniosWrapper.getGetConveniosByCoordinateResult() == null) {
                        Toast.makeText(MapaConveniosActivity.this, "Erro ao recuperar convênios", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    convenios = conveniosWrapper.getGetConveniosByCoordinateResult().getListaConvenios();

                    SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
                    mapFragment.getMapAsync(MapaConveniosActivity.this);

                } else {
                    Toast.makeText(MapaConveniosActivity.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MapaConveniosActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
}
