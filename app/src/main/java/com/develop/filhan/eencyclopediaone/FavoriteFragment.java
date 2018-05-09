package com.develop.filhan.eencyclopediaone;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    // Class FavoriteHelper untuk membantu Control Item Favorite
    private FavoriteHelper favHelper;

    //Adapter + Recycler
    private AdapterRecyclerviewItem mAdapter;
    private RecyclerView favRecyclerList;

    //Komponen View
    private ImageView lblFavNoData;

    //Data COllections
    private ArrayList<MenuModel> allList;
    private ArrayList<Integer> favIntList;
    private ArrayList<MenuModel> favList;

    //Constructor untuk Fragment
    public FavoriteFragment() {
        // Required empty public constructor
    }

    //Method yang dijalankan jika Layout View telah dibuat/pack
    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        //Set Fragment mempunyai Menu
        setHasOptionsMenu(true);
        //Set Title Name
        ((HomeActivity) getActivity()).setActionBarTitle("My Favorite");
        //Iniatiate Helper untuk melakukan COntrol data seperti Mengambil Seluruh data, FIlter data
        favHelper=new FavoriteHelper(getActivity());
        allList=new ItemController(getActivity()).selectAll();
        favIntList=favHelper.selectAllItem();
        //Initate KOmponen View
        lblFavNoData=(ImageView) v.findViewById(R.id.lblFavNoData);
        if(favIntList==null){return;}
        favRecyclerList=(RecyclerView) v.findViewById(R.id.RecyclerListItemFavorites);
        favRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Mengambil Seluruh Data (Mengisi) ke RecyclerView
        populateData();
    }

    //Membuat Layout di Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    //Untuk menambahkan/membuat menu pada Action Bar yang diambil dari menu.xml
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_favorites, menu);
    }

    //Aksi jika item menu dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fav_refresh: populateData(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method yang digunakan untuk mengisikan seluruh data yang ada dari Controller / Helper
    public void populateData(){
        //Mengambil Seluruh data Item ke List
        allList=new ItemController(getActivity()).selectAll();
        //Mengambil Seluruh data Favorite Item
        favIntList=favHelper.selectAllItem();
        //Cek Jika isi favorite belum ada
        if(favIntList==null){lblFavNoData.setVisibility(View.VISIBLE); favRecyclerList.setVisibility(View.GONE); return;}
        //Jika favorite terisi
        else{lblFavNoData.setVisibility(View.GONE); favRecyclerList.setVisibility(View.VISIBLE);}
        ArrayList<MenuModel> list=new ArrayList<>();
        for(MenuModel item: allList){
            for (int id: favIntList){
                if(item.getId()==id){
                    //Add to ArrayList
                    list.add(item);
                }
            }
        }
        //Insert temp (list) arraylist to Class List (FavList)
        favList=list;
        mAdapter=new AdapterRecyclerviewItem(favList);
        mAdapter.notifyDataSetChanged();
        favRecyclerList.setAdapter(mAdapter);
    }
}
