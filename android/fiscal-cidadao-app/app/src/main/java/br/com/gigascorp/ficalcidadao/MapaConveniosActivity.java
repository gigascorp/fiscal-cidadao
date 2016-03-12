package br.com.gigascorp.ficalcidadao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.ui.ConvenioAdapter;
import br.com.gigascorp.ficalcidadao.util.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MapaConveniosActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String API_URI = "http://fcapptest-60143.onmodulus.net/";
    private static final String TAG = "FISCAL-CIDADAO";

    private List<Convenio> convenios;
    private Map<Marker, List<Convenio>> marcadoresConvenio = new HashMap<Marker, List<Convenio>>();

    private Retrofit retrofit;
    private FiscalCidadaoApi fiscalApi;

    private RecyclerView reciclerViewConvenios;
    private SlidingUpPanelLayout slidingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa_convenios);

        //Inicializando o slidingPanel e lista com cardviews
        slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        reciclerViewConvenios = (RecyclerView) findViewById(R.id.cardList);
        reciclerViewConvenios.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reciclerViewConvenios.setLayoutManager(layoutManager);

        //Inicializando o retrofit para a url da API
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fiscalApi = retrofit.create(FiscalCidadaoApi.class);

        Call<List<Convenio>> conveniosProximosCall = fiscalApi.conveniosProximos();

        conveniosProximosCall.enqueue(new Callback<List<Convenio>>() {
            @Override
            public void onResponse(Response<List<Convenio>> response, Retrofit retrofit) {
                if (response.body() != null && (response.code() >= 200 && response.code() < 300)) {

                    convenios = response.body();

                    SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
                    mapFragment.getMapAsync(MapaConveniosActivity.this);

                } else {
                    Toast.makeText(MapaConveniosActivity.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MapaConveniosActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.setOnMarkerClickListener(MapaConveniosActivity.this);
        map.setOnMapClickListener(MapaConveniosActivity.this);

        LatLngBounds.Builder builder = LatLngBounds.builder();

        for (Convenio convenio : convenios) {

            Marker marcador = Util.getConvenioNaMesmaLocalizacao(marcadoresConvenio, convenio);
            if(marcador == null){
                LatLng coord = new LatLng(convenio.getCoordenada().getLat(), convenio.getCoordenada().getLng());
                builder.include(coord);
                marcador = map.addMarker(new MarkerOptions().position(coord));

                List<Convenio> conveniosDoMarcador = new ArrayList<>();
                conveniosDoMarcador.add(convenio);
                marcadoresConvenio.put(marcador, conveniosDoMarcador);

            } else {
                List<Convenio> conveniosDoMarcador = marcadoresConvenio.get(marcador);
                conveniosDoMarcador.add(convenio);
                marcadoresConvenio.put(marcador, conveniosDoMarcador);
            }
        }

        LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        List<Convenio> selecionados = marcadoresConvenio.get(marker);

        ConvenioAdapter adapter = new ConvenioAdapter(selecionados);
        reciclerViewConvenios.setAdapter(adapter);

        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

}
