package com.manuel.rosaurazapatainv.ActividadesPrincipales;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manuel.rosaurazapatainv.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng jardin = new LatLng(19.421777, -102.063284);
        googleMap.addMarker(new MarkerOptions().position(jardin).title("Jardín De Niños Rosaura Zapata | Clave: 16DJN0145C").snippet("García Ortiz 3, Centro, 60000 Uruapan, Mich."));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jardin, 17));
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}