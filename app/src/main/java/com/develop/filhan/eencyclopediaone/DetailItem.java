package com.develop.filhan.eencyclopediaone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailItem extends AppCompatActivity {

    public final static String EXTRA_POSITION = "MENU_ID_POSITION";
    public final static String EXTRA_DAERAH = "MENU_STR_DAERAH";

    private ImageView imgMakanan, imgDaerah;
    private TextView lblJudul, lblDeskripsi, lblDaerah, lblKarakter, lblDidapatkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitem);

        imgMakanan=(ImageView)findViewById(R.id.DetailItemGambar);
        imgDaerah=(ImageView)findViewById(R.id.DetailItemGmbrDaerah);
        lblJudul=(TextView)findViewById(R.id.DetailItemJudul);
        lblDeskripsi=(TextView)findViewById(R.id.DetailItemDeskripsi);
        lblDaerah=(TextView)findViewById(R.id.DetailItemDaerah);
        lblKarakter=(TextView)findViewById(R.id.DetailItemKarakteristik);
        lblDidapatkan=(TextView)findViewById(R.id.DetailItemDidapatkan);

        Intent ini = getIntent();
        int posId = ini.getIntExtra(EXTRA_POSITION,0);
        final MenuModel item = new ItemController(this).select(posId);
        setTitle(item.getJudul());
        lblJudul.setText(item.getJudul());
        lblDeskripsi.setText(item.getDeskripsi());
        lblDaerah.setText(item.getDaerah());

        lblDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daerah = new Intent(getApplicationContext(), ListItem.class);
                daerah.putExtra(EXTRA_DAERAH,item.getDaerah());
                startActivity(daerah);
            }
        });
    }
}
