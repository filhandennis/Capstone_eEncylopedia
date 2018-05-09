package com.develop.filhan.eencyclopediaone;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

/*
* Activity Search (Pencarian Menu)
* */

public class SearchItem extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //Komponen View
    private Button btnCari;
    private EditText txtCari;
    private RecyclerView listCari;
    private TextView lblStatusCari;
    private SearchView txtSearch;

    //Data Collections + Adapter
    private ArrayList<MenuModel> datas;
    private ArrayAdapter dataAdapter;
    private AdapterRecyclerviewItem mAdapter;

    //Class Helper / Controller
    private ItemController itemController;

    //OnCreate Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchitem);
        setTitle("Pencarian");

        //Komponen View Iniate
        //btnCari=(Button)findViewById(R.id.btnCari);
        //txtCari=(EditText) findViewById(R.id.txtCari);
        listCari=(RecyclerView) findViewById(R.id.listCari);
        listCari.setLayoutManager(new LinearLayoutManager(this));
        lblStatusCari=(TextView)findViewById(R.id.lblStatusCari);
        txtSearch=(SearchView)findViewById(R.id.txtSearch);
        //View v = txtSearch.findViewById(android.support.v7.appcompat.R.id.search_plate);
        //v.setBackgroundColor(Color.TRANSPARENT);

        //Helper _ Collections
        itemController=new ItemController(this);
        datas = new ArrayList<>();

        txtSearch.setOnQueryTextListener(this);
    }


    //Method yang digunakan ntuk melakukan pencarian item pada Collection Data
    public void cari(String q){
//        String nama = txtCari.getText().toString();
        String nama = q;
        //Melakukan Pencarian dengan bantuan Class Helper
        ArrayList<MenuModel> cari = itemController.findItemByNama(nama);
        //Jika data Kosong, maka eksekusi method dihentikan
        if(cari==null){return;}
        //Keperluan untuk Adapter
        ArrayList<String> pencarian = new ArrayList<>();
        for(MenuModel item: cari){
            pencarian.add(item.getJudul());
        }
        lblStatusCari.setText("Ditemukan "+itemController.countItems(cari));
        this.datas=cari;
//        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pencarian);
        //Re Initiate Adapter
        mAdapter = new AdapterRecyclerviewItem(cari);
        listCari.setAdapter(mAdapter);
    }

    //Ambil Seluruh Data
    private void populateData(){
        datas = new ItemController(this).selectAll();
        mAdapter = new AdapterRecyclerviewItem(datas);
        listCari.setAdapter(mAdapter);
    }

    //Jika tombol cari ditekan atau saat enter ditekan
    @Override
    public boolean onQueryTextSubmit(String s) {
        cari(s);
        return false;
    }

    //Jika Input Search berubah
    @Override
    public boolean onQueryTextChange(String s) {
        cari(s);
        return false;
    }
}
