package com.develop.filhan.eencyclopediaone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 28/04/2018.
 */

public class AdapterRecyclerviewItemTwo extends RecyclerView.Adapter<AdapterRecyclerviewItemTwo.ViewHolder> {

    private ArrayList<MenuModel> dataList;
    private Context context;

    public AdapterRecyclerviewItemTwo(ArrayList<MenuModel> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recyclerlist2, parent, false);
        context=view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuModel currentItem = dataList.get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lblNama, lblDaerah, lblView, lblLike;

        public ViewHolder(View itemView) {
            super(itemView);

            lblNama=(TextView)itemView.findViewById(R.id.lblRvTwoNama);
            lblDaerah=(TextView)itemView.findViewById(R.id.lblRvTwoDaerah);
            lblView=(TextView)itemView.findViewById(R.id.lblRvTwoViews);
            lblLike=(TextView)itemView.findViewById(R.id.lblRvTwoFavs);
        }

        public void bindTo(MenuModel item){
            lblNama.setText(item.getJudul());
            lblDaerah.setText(item.getDaerah());
            lblView.setText(""+item.getViewsCount());
            lblLike.setText(""+item.getFavoritesCount());
        }
    }
}
