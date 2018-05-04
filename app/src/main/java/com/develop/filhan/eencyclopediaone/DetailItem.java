package com.develop.filhan.eencyclopediaone;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class DetailItem extends AppCompatActivity implements OnMapReadyCallback {

    public final static String EXTRA_ITEM_DETAIL = "MENU_DETAIL_ITEM";
    public final static String EXTRA_POSITION = "MENU_ID_POSITION";
    public final static String EXTRA_DAERAH = "MENU_STR_DAERAH";

    private ImageView imgMakanan, imgDaerah, iconFavorite, btnGoogleImage, btnSendComment;
    private TextView lblJudul, lblDeskripsi, lblDaerah, lblKarakter, lblDidapatkan, lblFavorite;
    private Button btnDetailSellerAdd;
    private EditText txtComment;
    private LinearLayout listKarakter, btnFav, blockCommentInput;
    private MapView mapProvinsi;

    private int favState = R.drawable.loving;
    private int itemId;
    private MenuModel itemCurrent;
    private String sDaerah;

    //Map Object
    private GoogleMap gMap;

    private FavoriteHelper favHelper;
    private MenuFirebaseHelper menuHelper;

    //Firebase Object
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitem);

        //
        iconFavorite = (ImageView) findViewById(R.id.iconDetailItemFavorite);
        btnFav = (LinearLayout) findViewById(R.id.btnDetailItemFavorite);
        lblFavorite = (TextView) findViewById(R.id.lblDetailItemFavorite);
        imgMakanan = (ImageView) findViewById(R.id.DetailItemGambar);
        imgDaerah = (ImageView) findViewById(R.id.DetailItemGmbrDaerah);
        lblJudul = (TextView) findViewById(R.id.DetailItemJudul);
        lblDeskripsi = (TextView) findViewById(R.id.DetailItemDeskripsi);
        lblDaerah = (TextView) findViewById(R.id.DetailItemDaerah);
        lblKarakter = (TextView) findViewById(R.id.DetailItemKarakteristik);
        lblDidapatkan = (TextView) findViewById(R.id.DetailItemDidapatkan);
        btnDetailSellerAdd = (Button) findViewById(R.id.btnDetailSellerAdd);
        btnGoogleImage = (ImageView) findViewById(R.id.btnGSearch);
        txtComment=(EditText)findViewById(R.id.txtDetailComment);
        btnSendComment=(ImageView)findViewById(R.id.icoDetailComment);

        listKarakter = (LinearLayout) findViewById(R.id.listDetailItemKarakteristik);
        blockCommentInput = (LinearLayout) findViewById(R.id.blockDetailComment);

        //Map Object
        mapProvinsi = (MapView) findViewById(R.id.DetailItemMap);
        mapProvinsi.onCreate(savedInstanceState);
        mapProvinsi.getMapAsync(this);

        //
        favHelper = new FavoriteHelper(this);

        Intent ini = getIntent();
        final MenuModel item = (MenuModel) ini.getSerializableExtra(EXTRA_ITEM_DETAIL);
        itemId = item.getId();
        itemCurrent=item;
        int posId = ini.getIntExtra(EXTRA_POSITION, 0);
        //final MenuModel item = new ItemController(this).select(posId);
        setTitle(item.getJudul());
        lblJudul.setText(item.getJudul());
        lblDeskripsi.setText(item.getDeskripsi());
        lblDaerah.setText(item.getDaerah());
        sDaerah = item.getDaerah();

        imgMakanan.setImageResource(R.drawable.sate);
        iconFavorite.setImageResource(favState);

        packKarakter(item);

        lblDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daerah = new Intent(getApplicationContext(), ListItem.class);
                daerah.putExtra(EXTRA_DAERAH, item.getDaerah());
                startActivity(daerah);
            }
        });

        btnGoogleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoGoogle();
            }
        });

        checkFav();
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loving();
            }
        });

        btnDetailSellerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addSeller = new Intent(getApplicationContext(), SellerAddItemActivity.class);
                addSeller.putExtra(SellerAddItemActivity.SELLER_REGISTRATION_ITEMID, itemCurrent.getId());
                addSeller.putExtra(SellerAddItemActivity.SELLER_REGISTRATION_ITEM, itemCurrent.getJudul());
                startActivity(addSeller);
            }
        });

        //Firebase Object
        auth=FirebaseAuth.getInstance();
        menuHelper = new MenuFirebaseHelper(this);

        blockCommentInput.setVisibility(View.GONE);
        //Action if user already Login
        if(auth.getCurrentUser()!=null) {
            blockCommentInput.setVisibility(View.VISIBLE);
            final FirebaseUser userLogin = auth.getCurrentUser();

            //Wait Until 10 Second
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    menuHelper.addView("" + itemId, "" + userLogin.getUid());
                }
            },10000);

            //Comment Act
            btnSendComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String txt = txtComment.getText().toString();
                    menuHelper.addComment(""+itemId, ""+userLogin.getUid(), ""+txt);
                    txtComment.setText("");
                    Toast.makeText(DetailItem.this, "Comment Added", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void packKarakter(MenuModel item) {
        String karakters = item.getDeskripsi();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 8);
        for (String karakter : karakters.split("\\.")) {
            if (karakter.length() < 3) {
                continue;
            }
            TextView txt = new TextView(this);
            karakter = karakter.toLowerCase().trim();
            karakter = karakter.substring(0, 1).toUpperCase() + karakter.substring(1);
            txt.setText(Html.fromHtml("&#8226; " + karakter));
            txt.setTextSize(14);
            txt.setLayoutParams(params);
            listKarakter.addView(txt);
        }
    }

    private void loving() {
        int id = itemId;
        String lbl;
        if (favState == R.drawable.loving) {
            favState = R.drawable.lover;
            lbl = "Love it!";
            //Add Love to Firebase
            if(FirebaseAuth.getInstance().getCurrentUser()!=null){menuHelper.addLike(""+itemId,FirebaseAuth.getInstance().getCurrentUser().getUid());}
            if (favHelper.addFav(itemId) > -1) {
                Toast.makeText(this, lbl, Toast.LENGTH_SHORT).show();
            }
        } else {
            favState = R.drawable.loving;
            lbl = "Add to Favorite";
            //Delete Love to Firebase
            if(FirebaseAuth.getInstance().getCurrentUser()!=null){menuHelper.removeLike(""+itemId,FirebaseAuth.getInstance().getCurrentUser().getUid());}
            if (favHelper.deleteFav(itemId) == true) {
                Toast.makeText(this, "Ouch...", Toast.LENGTH_SHORT).show();
            }
        }
        lblFavorite.setText(lbl);
        iconFavorite.setImageResource(favState);
    }

    private void checkFav() {
        Cursor find = favHelper.findOne(itemId);
        if (find != null) {
            favState = R.drawable.lover;
            lblFavorite.setText("Love it!");
            iconFavorite.setImageResource(favState);
        }
    }

    public void gotoGoogle() {
        String baseUrl = "https://www.google.com/search?tbm=isch";
        String q = null;
        try {
            q = URLEncoder.encode(lblJudul.getText().toString() + " " + lblDaerah.getText().toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = baseUrl + "&q=" + q;
        Intent googleSearch = new Intent(this, BrowserActivity.class);
        googleSearch.putExtra(BrowserActivity.EXTRA_URL_ADDRESS, url);
        startActivity(googleSearch);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapProvinsi.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Tutorial by: http://www.zoftino.com/android-mapview-tutorial
        gMap = googleMap;
        gMap.setMinZoomPreference(4);
        gMap.getUiSettings().setAllGesturesEnabled(false);
        gMap.getUiSettings().setScrollGesturesEnabled(false);
        gMap.getUiSettings().setZoomControlsEnabled(true);
//        LatLng ny = new LatLng(40.7143528, -74.0059731);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(ny);
//        gMap.addMarker(markerOptions);
        //gMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        getLocationFromAddress(sDaerah);
        mapProvinsi.onResume();

        Log.d("GOOGLE::MAP", "Already Run");
    }

    public void getLocationFromAddress(String strAddress) {
        //Code Src: https://stackoverflow.com/questions/17835426/get-latitude-longitude-from-address-in-android
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 5);
            //check for null
            if (address == null) {return;}
            //Lets take first possibility from the all possibilities.
            Address location = address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //Put marker on map on that LatLng
            Marker srchMarker = gMap.addMarker(new MarkerOptions().position(latLng).title(sDaerah));
            //Animate and Zoon on that map location
            gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(7));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
