package com.develop.filhan.eencyclopediaone;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DetailItem extends AppCompatActivity {

    public final static String EXTRA_ITEM_DETAIL = "MENU_DETAIL_ITEM";
    public final static String EXTRA_POSITION = "MENU_ID_POSITION";
    public final static String EXTRA_DAERAH = "MENU_STR_DAERAH";

    private ImageView imgMakanan, imgDaerah, iconFavorite, btnGoogleImage;
    private TextView lblJudul, lblDeskripsi, lblDaerah, lblKarakter, lblDidapatkan, lblFavorite;
    private Button btnBecomeItemSeller;
    private LinearLayout listKarakter, btnFav;

    private int favState = R.drawable.loving;
    private int itemId;

    private FavoriteHelper favHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitem);

        //
        iconFavorite=(ImageView)findViewById(R.id.iconDetailItemFavorite);
        btnFav=(LinearLayout) findViewById(R.id.btnDetailItemFavorite);
        lblFavorite=(TextView) findViewById(R.id.lblDetailItemFavorite);
        imgMakanan=(ImageView)findViewById(R.id.DetailItemGambar);
        imgDaerah=(ImageView)findViewById(R.id.DetailItemGmbrDaerah);
        lblJudul=(TextView)findViewById(R.id.DetailItemJudul);
        lblDeskripsi=(TextView)findViewById(R.id.DetailItemDeskripsi);
        lblDaerah=(TextView)findViewById(R.id.DetailItemDaerah);
        lblKarakter=(TextView)findViewById(R.id.DetailItemKarakteristik);
        lblDidapatkan=(TextView)findViewById(R.id.DetailItemDidapatkan);
        btnBecomeItemSeller=(Button)findViewById(R.id.btnDetailItemBecomeSeller);
        btnGoogleImage=(ImageView)findViewById(R.id.btnGSearch);

        listKarakter=(LinearLayout)findViewById(R.id.listDetailItemKarakteristik);

        //
        favHelper=new FavoriteHelper(this);

        Intent ini = getIntent();
        final MenuModel item = (MenuModel)ini.getSerializableExtra(EXTRA_ITEM_DETAIL);
        itemId=item.getId();
        int posId = ini.getIntExtra(EXTRA_POSITION,0);
        //final MenuModel item = new ItemController(this).select(posId);
        setTitle(item.getJudul());
        lblJudul.setText(item.getJudul());
        lblDeskripsi.setText(item.getDeskripsi());
        lblDaerah.setText(item.getDaerah());

        imgMakanan.setImageResource(R.drawable.sate);
        iconFavorite.setImageResource(favState);

        packKarakter(item);

        lblDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daerah = new Intent(getApplicationContext(), ListItem.class);
                daerah.putExtra(EXTRA_DAERAH,item.getDaerah());
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
    }

    private void packKarakter(MenuModel item){
        String karakters = item.getDeskripsi();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,8);
        for(String karakter: karakters.split("\\.")) {
            if(karakter.length()<3){continue;}
            TextView txt = new TextView(this);
            karakter = karakter.toLowerCase().trim();
            karakter = karakter.substring(0, 1).toUpperCase() + karakter.substring(1);
            txt.setText(Html.fromHtml("&#8226; "+karakter));
            txt.setTextSize(14);
            txt.setLayoutParams(params);
            listKarakter.addView(txt);
        }
    }

    private void loving(){
        int id = itemId;
        String lbl;
        if(favState==R.drawable.loving){
            favState=R.drawable.lover;
            lbl="Love it!";
            if(favHelper.addFav(itemId)>-1){
                Toast.makeText(this, lbl, Toast.LENGTH_SHORT).show();
            }
        }else{
            favState=R.drawable.loving;
            lbl="Add to Favorite";
            if(favHelper.deleteFav(itemId)==true){
                Toast.makeText(this, "Ouch...", Toast.LENGTH_SHORT).show();
            }
        }
        lblFavorite.setText(lbl);
        iconFavorite.setImageResource(favState);
    }
    private void checkFav(){
        Cursor find = favHelper.findOne(itemId);
        if(find!=null){
            favState=R.drawable.lover;
            lblFavorite.setText("Love it!");
            iconFavorite.setImageResource(favState);
        }
    }

    public void gotoGoogle(){
        String baseUrl = "https://www.google.com/search?tbm=isch";
        String q = null;
        try {
            q = URLEncoder.encode(lblJudul.getText().toString()+" "+lblDaerah.getText().toString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = baseUrl+"&q="+ q;
        Intent googleSearch = new Intent(this, BrowserActivity.class);
        googleSearch.putExtra(BrowserActivity.EXTRA_URL_ADDRESS, url);
        startActivity(googleSearch);
    }
}
