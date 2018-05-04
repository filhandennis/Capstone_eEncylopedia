package com.develop.filhan.eencyclopediaone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class SellerRegistrationActivity extends AppCompatActivity{

    private EditText txtNama, txtAlamat;
    private Button btnReg;
    private MapView mapAlamat;
    private GoogleMap gMap;

    //Firebase Object
    private FirebaseAuth auth;

    //Helper Class
    private MenuFirebaseHelper cMenu;
    private SellerFirebaseHelper cSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        setTitle("Become a Seller");

        //Firebase Object
        auth=FirebaseAuth.getInstance();

        //Helper Object
        cSeller=new SellerFirebaseHelper();

        txtNama=(EditText)findViewById(R.id.txtSellerNama);
        txtAlamat=(EditText)findViewById(R.id.txtSellerAlamat);
        btnReg=(Button)findViewById(R.id.btnSellerReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaSeller = txtNama.getText().toString();
                String alamat = txtAlamat.getText().toString();
                cSeller.addNewSeller(auth.getCurrentUser().getUid(), namaSeller, alamat, "");
                Toast.makeText(SellerRegistrationActivity.this, "Seller Requested !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });


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
