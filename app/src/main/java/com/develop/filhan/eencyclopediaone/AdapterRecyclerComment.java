package com.develop.filhan.eencyclopediaone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 05/05/2018.
 */

public class AdapterRecyclerComment extends RecyclerView.Adapter<AdapterRecyclerComment.ViewHolder>{

    private ArrayList<CommentModel> comments;
    private Context context;

    public AdapterRecyclerComment(ArrayList<CommentModel> comments) {
        this.comments = comments;
    }

    @Override
    public AdapterRecyclerComment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        context=view.getContext();
        return new AdapterRecyclerComment.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerComment.ViewHolder holder, int position) {
        CommentModel item = comments.get(position);
        holder.bindTo(item);
    }

    @Override
    public int getItemCount() {
        return (comments.size()<1 || comments==null)?0:comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView lblDN, lblDate, lblTXT;

        public ViewHolder(View itemView) {
            super(itemView);

            lblDN=(TextView)itemView.findViewById(R.id.lblCommentDName);
            lblDate=(TextView)itemView.findViewById(R.id.lblCommentDate);
            lblTXT=(TextView)itemView.findViewById(R.id.lblCommentTXT);
        }

        private void bindTo(CommentModel item){
            lblDN.setText(item.getEmail());
            lblDate.setText(item.getDate());
            lblTXT.setText(item.getText());
        }
    }
}
