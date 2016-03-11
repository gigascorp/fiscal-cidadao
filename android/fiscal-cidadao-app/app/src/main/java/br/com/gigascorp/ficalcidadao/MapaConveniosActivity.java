package br.com.gigascorp.ficalcidadao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.gigascorp.ficalcidadao.api.FiscalCidadaoApi;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MapaConveniosActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String API_URI = "http://192.168.0.17:3000";
    private static final String TAG = "FISCAL-CIDADAO";

    private List<LatLng> coordenadas;
    private Retrofit retrofit;
    private FiscalCidadaoApi fiscalApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa_convenios);

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
                    List<Convenio> convenios = response.body();

                    coordenadas = new ArrayList<>();

                    for (Convenio c : convenios) {
                        Log.d(TAG, c.getJustificativa() + " [" + String.valueOf(c.getCoordenada().getLat()) + ", " + String.valueOf(c.getCoordenada().getLng()) + "]");
                        coordenadas.add(new LatLng(c.getCoordenada().getLat(), c.getCoordenada().getLng()));
                    }

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
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for(LatLng coord : coordenadas){
            builder.include(coord);
            map.addMarker(new MarkerOptions().position(coord).title(coord.toString()));
        }

        LatLngBounds bounds = builder.build();

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}
