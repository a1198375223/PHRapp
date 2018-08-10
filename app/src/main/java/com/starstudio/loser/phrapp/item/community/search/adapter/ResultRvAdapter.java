package com.starstudio.loser.phrapp.item.community.search.adapter;

/*
    create by:loser
    date:2018/8/9 21:08
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<AVObject> mList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public ResultRvAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void setList(List<AVObject> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_search_result, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof MyHolder) {
            ((MyHolder) holder).position = position;
            ((MyHolder) holder).mTitle.setText(mList.get(position).getString("title"));
            ((MyHolder) holder).mTime.setText(DateUtils.parseDate(mList.get(position).getDate("time")));
            ((MyHolder) holder).mAuthor.setText(mList.get(position).getAVUser("article_user").getUsername());
            if (mList.get(position).getInt("like") == 0) {
                ((MyHolder) holder).mLikeContainer.setVisibility(View.INVISIBLE);
            } else {
                ((MyHolder) holder).mLike.setText("" + mList.get(position).getInt("like"));
            }
            if (mList.get(position).getInt("reply") == 0) {
                ((MyHolder) holder).mCommentContainer.setVisibility(View.INVISIBLE);
            } else{
                ((MyHolder) holder).mComment.setText("" + mList.get(position).getInt("reply"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        int position;
        TextView mTitle, mTime, mAuthor, mLike, mComment;
        LinearLayout mCommentContainer, mLikeContainer;
        MyHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.phr_rv_search_result_title);
            mTime = (TextView) itemView.findViewById(R.id.phr_search_result_time);
            mAuthor = (TextView) itemView.findViewById(R.id.phr_search_result_author);
            mLike = (TextView) itemView.findViewById(R.id.phr_search_result_like);
            mComment = (TextView) itemView.findViewById(R.id.phr_rv_search_result_comment);

            mCommentContainer = (LinearLayout) itemView.findViewById(R.id.phr_rv_search_result_comment_container);
            mLikeContainer = (LinearLayout) itemView.findViewById(R.id.phr_rv_search_result_like_container);

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
