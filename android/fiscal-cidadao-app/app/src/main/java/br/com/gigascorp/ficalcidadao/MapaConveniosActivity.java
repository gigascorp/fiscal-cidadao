package br.com.gigascorp.ficalcidadao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapaConveniosActivity extends AppCompatActivity implements OnMapReadyCallback {

    private List<LatLng> coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fixme
        coordenadas = new ArrayList<>();
        coordenadas.add(new LatLng(-2.532783, -44.291549));
        coordenadas.add(new LatLng(-2.533962,-44.293427));
        coordenadas.add(new LatLng(-2.530779,-44.294467));
        coordenadas.add(new LatLng(-2.535677, -44.289585));

        setContentView(R.layout.activity_mapa_convenios);

        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for(LatLng coord : coordenadas){
            builder.include(coord);
            map.addMarker(new MarkerOptions().position(coord).title(coord.toString()));
        }

        LatLngBounds bounds = builder.build();

        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 40));
    }
}
