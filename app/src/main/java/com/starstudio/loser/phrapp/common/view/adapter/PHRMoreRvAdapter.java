package com.starstudio.loser.phrapp.common.view.adapter;

/*
    create by:loser
    date:2018/8/8 23:57
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;

import java.util.List;

public class PHRMoreRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private OnItemClickListener mListener;
    private List<String> mText;
    private int[] mRes;

    public PHRMoreRvAdapter(Context context) {
        this.mContext = context;
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setData(List<String> text, int[] icon) {
        this.mText = text;
        this.mRes = icon;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_artcle_more_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof MyHolder) {
            ((MyHolder) holder).position = position;
            ((MyHolder) holder).mText.setText(mText.get(position));
            GlideUtils.loadLocal(mContext, mRes[position], ((MyHolder) holder).mIcon);
        }
    }

    @Override
    public int getItemCount() {
        return mText.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mText;
        int position;

        MyHolder(final View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.phr_rv_article_more_image);
            mText = (TextView) itemView.findViewById(R.id.phr_rv_article_more_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClickListener(position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

}
