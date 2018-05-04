package com.develop.filhan.eencyclopediaone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 05/05/2018.
 */

public class AdapterRecyclerSeller extends RecyclerView.Adapter<AdapterRecyclerSeller.ViewHolder> {
    private ArrayList<SellerItemModel> sellers;
    private Context context;

    public AdapterRecyclerSeller(ArrayList<SellerItemModel> sellers) {
        this.sellers = sellers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_seller, parent, false);
        context=view.getContext();
        return new AdapterRecyclerSeller.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SellerItemModel item = sellers.get(position);
        holder.bindTo(item);
    }

    @Override
    public int getItemCount() {
        return (sellers.size()<1 || sellers==null)?0:sellers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView lblNama, lblHarga, lblDist;

        public ViewHolder(View itemView) {
            super(itemView);

            lblNama=(TextView)itemView.findViewById(R.id.lblSellerItemNama);
            lblHarga=(TextView)itemView.findViewById(R.id.lblSellerItemPrice);
            lblDist=(TextView)itemView.findViewById(R.id.lblSellerItemDistance);
        }

        private void bindTo(SellerItemModel iseller){
            lblNama.setText(iseller.getItemname());
            String harga = iseller.getMaxPrice()==0?"Rp"+iseller.getMinPrice():"Rp"+iseller.getMinPrice()+" - Rp"+iseller.getMaxPrice();
            lblHarga.setText(harga);
            int min=0,max=10;
            int randomNumber =min + (int)(Math.random() * (max - min));
            lblDist.setText(""+randomNumber+"KM");
        }
    }
}
