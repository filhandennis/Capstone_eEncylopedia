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

    private FavoriteHelper favHelper;

    private AdapterRecyclerviewItem mAdapter;
    private RecyclerView favRecyclerList;
    private ImageView lblFavNoData;

    private ArrayList<MenuModel> allList;
    private ArrayList<Integer> favIntList;
    private ArrayList<MenuModel> favList;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setHasOptionsMenu(true);
        //Set Title Name
        ((HomeActivity) getActivity()).setActionBarTitle("My Favorite");
        //
        favHelper=new FavoriteHelper(getActivity());
        allList=new ItemController(getActivity()).selectAll();
        favIntList=favHelper.selectAllItem();
        //
        lblFavNoData=(ImageView) v.findViewById(R.id.lblFavNoData);
        if(favIntList==null){return;}
        favRecyclerList=(RecyclerView) v.findViewById(R.id.RecyclerListItemFavorites);
        favRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        populateData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_favorites, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fav_refresh: populateData(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateData(){
        allList=new ItemController(getActivity()).selectAll();
        favIntList=favHelper.selectAllItem();
        if(favIntList==null){lblFavNoData.setVisibility(View.VISIBLE); favRecyclerList.setVisibility(View.GONE); return;}
        else{lblFavNoData.setVisibility(View.GONE); favRecyclerList.setVisibility(View.VISIBLE);}
        ArrayList<MenuModel> list=new ArrayList<>();
        for(MenuModel item: allList){
            for (int id: favIntList){
                if(item.getId()==id){
                    list.add(item);
                }
            }
        }
        favList=list;
        mAdapter=new AdapterRecyclerviewItem(favList);
        mAdapter.notifyDataSetChanged();
        favRecyclerList.setAdapter(mAdapter);
    }
}
