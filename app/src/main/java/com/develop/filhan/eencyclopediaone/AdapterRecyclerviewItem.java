package com.develop.filhan.eencyclopediaone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;

/**
 * Created by ASUS on 24/03/2018.
 */

public class AdapterRecyclerviewItem extends RecyclerView.Adapter<AdapterRecyclerviewItem.ItemViewHolder> implements SectionTitleProvider {

    private ArrayList<MenuModel> dataList;
    private Context context;
    private FavoriteHelper favHelper;

    public AdapterRecyclerviewItem(ArrayList<MenuModel> dataList) {
        this.dataList = dataList;
        Log.d("DATALIST"," n "+getItemCount());
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recyclerlist, parent, false);
        context=view.getContext();
        favHelper=new FavoriteHelper(context);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final MenuModel currentMenu = dataList.get(position);
        holder.bindTo(currentMenu);
        //if(favHelper.findOne(currentMenu.getId())!=null){holder.iconFav.setImageResource(R.drawable.lover);}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(view.getContext(), DetailItem.class);
                //detail.putExtra(DetailItem.EXTRA_POSITION, position);
                detail.putExtra(DetailItem.EXTRA_ITEM_DETAIL, currentMenu);
                view.getContext().startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size()>0?dataList.size():0;
    }

    @Override
    public String getSectionTitle(int position) {
        return dataList.get(position).getJudul().substring(0, 1);
    }

    /*
    *
    */
    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView lblDaerah, lblJudul;
        private ImageView iconFav;

        public ItemViewHolder(View itemView) {
            super(itemView);
            lblDaerah=(TextView)itemView.findViewById(R.id.RecyclerItemDaerah);
            lblJudul=(TextView)itemView.findViewById(R.id.RecyclerItemNama);
            iconFav=(ImageView) itemView.findViewById(R.id.RecyclerItemInfoIcon);
        }

        public void bindTo(MenuModel item){
            MenuModel currentItem = item;
            lblDaerah.setText(currentItem.getDaerah());
            lblJudul.setText(currentItem.getJudul());
        }
    }
}
