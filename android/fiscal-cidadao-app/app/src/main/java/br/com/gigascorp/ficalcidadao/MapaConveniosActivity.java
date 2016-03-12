package br.com.gigascorp.ficalcidadao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MapaConveniosActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String API_URI = "http://fcapptest-60143.onmodulus.net/";
    private static final String TAG = "FISCAL-CIDADAO";

    private List<Convenio> convenios;
    private Map<Marker, Convenio> marcadoresConvenio = new HashMap<Marker, Convenio>();

    private Retrofit retrofit;
    private FiscalCidadaoApi fiscalApi;

    private SlidingUpPanelLayout slidingLayout;
    private TextView txtSlideJustificativa;
    private TextView txtSlideCoordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa_convenios);

        txtSlideJustificativa = (TextView) findViewById(R.id.txtSlideJustificatica);
        txtSlideCoordenadas = (TextView) findViewById(R.id.txtSlideCoordenadas);

        slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

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
            LatLng coord = new LatLng(convenio.getCoordenada().getLat(), convenio.getCoordenada().getLng());
            builder.include(coord);

            Marker marcador = map.addMarker(new MarkerOptions().position(coord).title(convenio.getJustificativa()));
            marcadoresConvenio.put(marcador, convenio);
        }

        LatLngBounds bounds = builder.build();

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Convenio convenio = marcadoresConvenio.get(marker);

        txtSlideJustificativa.setText(convenio.getJustificativa());
        txtSlideCoordenadas.setText("(" + String.valueOf(convenio.getCoordenada().getLat()) + ", " + String.valueOf(convenio.getCoordenada().getLng()) + ")");

        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }
}
