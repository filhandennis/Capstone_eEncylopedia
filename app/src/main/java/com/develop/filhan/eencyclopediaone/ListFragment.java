package com.develop.filhan.eencyclopediaone;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RadioGroup;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private AdapterRecyclerviewItem mAdapter;

    private ArrayList<MenuModel> list;
    private RecyclerView recyclerView1;
    private FastScroller fastScroller;

    private int sortOptionChecked;

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
        // By: https://github.com/timusus/RecyclerView-FastScroll
        fastScroller = (FastScroller)v.findViewById(R.id.fastscroll);

        list = new ItemController(getActivity()).selectAll();
        sortOptionChecked=R.id.rbSortNama;

        populateData(list);
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
            case R.id.menu_listitem_sort: changeSort(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateData(ArrayList<MenuModel> listing){
        mAdapter = new AdapterRecyclerviewItem(listing);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView1.setAdapter(mAdapter);
        fastScroller.setRecyclerView(recyclerView1);
    }

    private void changeSort(){
        AlertDialog.Builder dSort = new AlertDialog.Builder(getActivity());
        View dSortView = getActivity().getLayoutInflater().inflate(R.layout.fragment_item_sort, null);
        final RadioGroup rgSort =(RadioGroup)dSortView.findViewById(R.id.rgSorts);
        rgSort.check(sortOptionChecked);
        dSort.setView(dSortView);
        dSort.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ItemController cItem = new ItemController(getActivity());
                switch (rgSort.getCheckedRadioButtonId()){
                    case R.id.rbSortNama: populateData(cItem.sortByName(list, true)); sortOptionChecked=R.id.rbSortNama; break;
                    case R.id.rbSortDaerah: populateData(cItem.sortByDaerah(list, true)); sortOptionChecked=R.id.rbSortDaerah; break;
                    case R.id.rbSortLike: break;
                    case R.id.rbSortComment: break;
                    default: break;
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        dSort.show();
    }

}
