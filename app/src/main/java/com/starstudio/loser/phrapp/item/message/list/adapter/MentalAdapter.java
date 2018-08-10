package com.starstudio.loser.phrapp.item.message.list.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.baidu.mapapi.map.InfoWindow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;
import com.starstudio.loser.phrapp.item.message.list.web.PHRWebActivity;
import com.starstudio.loser.phrapp.item.message.list.web.SimpleWebActivity;

import java.util.List;

public class MentalAdapter extends RecyclerView.Adapter<MentalAdapter.ViewHolder> {
    private static final String TAG = "MentalAdapter";
    private List<UsefulData> mDataList;
    private Context mContext;

    private RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.waiting)
            .error(R.drawable.error)
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        TextView newsTitle;
        ImageView newsImage;
        TextView newsAuthor;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view;
            newsTitle = (TextView) view.findViewById(R.id.phr_rv_one_image_text);
            newsAuthor = (TextView) view.findViewById(R.id.phr_rv_one_image_author);
            newsImage = (ImageView) view.findViewById(R.id.phr_rv_one_image_icon);
        }
    }

    public MentalAdapter(List<UsefulData> dataList){
        mDataList = dataList;
    }

    @NonNull
    @Override
    public MentalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext=parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phr_rv_one_picture_layout,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                UsefulData data = mDataList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("url",data.getUrl());
                Intent intent = new Intent(mContext,SimpleWebActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MentalAdapter.ViewHolder holder, int position) {
        UsefulData data = mDataList.get(position);
        holder.newsTitle.setText(data.getTitle());
        holder.newsAuthor.setText(data.getAuthor());
        Glide.with(mContext)
                .load(data.getImage1())
                .apply(options)
                .into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
