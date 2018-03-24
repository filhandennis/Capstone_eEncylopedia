package com.develop.filhan.eencyclopediaone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListItem extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterRecyclerviewItem mAdapter;

    private ArrayList<MenuModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem);
        setTitle("Makanan Khas");

        Intent ini = getIntent();
        if(ini.getStringExtra(DetailItem.EXTRA_DAERAH)==null) {
            list = new ItemController(this).selectAll();
        }else{
            String daerah = ini.getStringExtra(DetailItem.EXTRA_DAERAH);
            setTitle(daerah);
            list = new ItemController(this).getItemByDaerah(daerah);
        }
        //list = new ArrayList<>();
        //list.add(new MenuModel("Soto","Daerah","Soto enak",""));

        recyclerView=(RecyclerView)findViewById(R.id.RecyclerListItem);
        mAdapter = new AdapterRecyclerviewItem(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }
}
