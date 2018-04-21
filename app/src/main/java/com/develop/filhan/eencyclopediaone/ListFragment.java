package com.develop.filhan.eencyclopediaone;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private AdapterRecyclerviewItem mAdapter;

    private ArrayList<MenuModel> list;
    private RecyclerView recyclerView1;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setHasOptionsMenu(true);
        //Set Title Name
        ((HomeActivity) getActivity()).setActionBarTitle("Daftar Menu Khas");

        recyclerView1=(RecyclerView)v.findViewById(R.id.fragment_list_recyclerview1);

        populateData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_listitem, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_listitem_search:
                getActivity().startActivity(new Intent(getActivity(),SearchItem.class));
                break;
            case R.id.menu_listitem_favorite: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateData(){
        list = new ItemController(getActivity()).selectAll();
        mAdapter = new AdapterRecyclerviewItem(list);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView1.setAdapter(mAdapter);
    }

}
