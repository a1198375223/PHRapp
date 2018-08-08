package com.starstudio.loser.phrapp.item.main.collect.adapter;

/*
    create by:loser
    date:2018/8/8 16:36
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
import com.avos.avoscloud.AVUser;
import com.makeramen.roundedimageview.RoundedImageView;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.utils.DateUtils;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;
import java.util.ArrayList;
import java.util.List;

public class CollectRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AVObject> mList;
    private Context mContext;
    private OnItemClickListener mListener;

    public CollectRvAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public void setList(List<AVObject> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_collect_layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof MyHolder) {
            ((MyHolder) holder).position = position;
            //头像
            AVUser avUser = mList.get(position).getAVUser("article_user");
            GlideUtils.loadRoundImage(mContext, avUser.getAVFile("head_img").getUrl(), ((MyHolder) holder).mHeadIcon);
            ((MyHolder) holder).mDescription.setText(mList.get(position).getString("describe"));
            ((MyHolder) holder).mUsername.setText(avUser.getUsername());
            ((MyHolder) holder).mTitle.setText(mList.get(position).getString("title"));
            ((MyHolder) holder).mText.setText(mList.get(position).getString("text"));
            ((MyHolder) holder).mDate.setText(DateUtils.parseDate(mList.get(position).getDate("time")));
        }
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        int position;
        RoundedImageView mHeadIcon;
        TextView mUsername;
        TextView mDescription;
        TextView mDate;
        TextView mTitle;
        TextView mText;
        MyHolder(View itemView) {
            super(itemView);
            mHeadIcon = (RoundedImageView) itemView.findViewById(R.id.phr_rv_collect_icon);
            mUsername = (TextView) itemView.findViewById(R.id.phr_rv_collect_name);
            mDescription = (TextView) itemView.findViewById(R.id.phr_rv_collect_description);
            mDate = (TextView) itemView.findViewById(R.id.phr_rv_collect_time);
            mTitle = (TextView) itemView.findViewById(R.id.phr_rv_collect_title);
            mText = (TextView) itemView.findViewById(R.id.phr_rv_collect_body_text);
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
