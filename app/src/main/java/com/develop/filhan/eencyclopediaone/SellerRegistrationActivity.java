package com.develop.filhan.eencyclopediaone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SellerRegistrationActivity extends AppCompatActivity{

    private EditText txtNama, txtAlamat;
    private MapView mapAlamat;
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        setTitle("Become a Seller");

        txtNama=(EditText)findViewById(R.id.txtSellerNama);
        txtAlamat=(EditText)findViewById(R.id.txtSellerAlamat);

        mapAlamat = (MapView) findViewById(R.id.SellerMap);
        mapAlamat.onCreate(savedInstanceState);
        mapAlamat.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                gMap.getUiSettings().setAllGesturesEnabled(true);
                LatLng ind = new LatLng(-0.789275, 113.921327);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(ind);
                gMap.addMarker(markerOptions);

                gMap.moveCamera(CameraUpdateFactory.newLatLng(ind));

                mapAlamat.onResume();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapAlamat.onResume();
    }
}
