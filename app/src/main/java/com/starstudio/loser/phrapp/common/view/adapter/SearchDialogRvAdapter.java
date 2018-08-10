package com.starstudio.loser.phrapp.common.view.adapter;

/*
    create by:loser
    date:2018/8/9 20:02
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.R;

import java.util.ArrayList;
import java.util.List;

public class SearchDialogRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<AVObject> mList;
    private OnItemClickListener mListener;

    public SearchDialogRvAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }

    public void setList(List<AVObject> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_search_dialog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof MyHolder) {
            ((MyHolder) holder).position = position;
            ((MyHolder) holder).mResult.setText(mList.get(position).getString("title"));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        int position;
        TextView mResult;
        MyHolder(View itemView) {
            super(itemView);
            mResult = (TextView) itemView.findViewById(R.id.phr_rv_search_dialog_result);
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
}
