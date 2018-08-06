package com.starstudio.loser.phrapp.item.community.comment.adpater;

/*
    create by:loser
    date:2018/8/3 16:46
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.utils.DateUtils;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;
import com.starstudio.loser.phrapp.common.view.PHRBottomDialog;

import java.util.ArrayList;
import java.util.List;

public class CommentRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<AVObject> mList;
    private OnReplyClickListener mListener;

    public CommentRvAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public interface OnReplyClickListener{
        void onReplyClickListener(int id, String name);
    }

    public void setListener(OnReplyClickListener listener) {
        this.mListener = listener;
    }

    enum Type{
        NO_COMMENT,
        HAS_COMMENT
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 0) {
            return Type.NO_COMMENT.ordinal();
        }
        return Type.HAS_COMMENT.ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Type.NO_COMMENT.ordinal()) {
            return new NoneReply(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_my_article_no_comment, parent, false));
        }
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_my_article_comment, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof MyHolder) {
            if (!mList.get(position).getBoolean("is_author")) {
                ((MyHolder) holder).mAuthor.setVisibility(View.INVISIBLE);
            }

            if (mList.get(position).getAVObject("reply_to") == null) {
                ((MyHolder) holder).mCanSee.setVisibility(View.GONE);
            } else {
                ((MyHolder) holder).mReference.setText("引用#"+mList.get(position).getAVObject("reply_to").getInt("id")+"楼 @"+mList.get(position).getAVObject("reply_to").getAVUser("comment_user").getUsername()+"发表的");
                ((MyHolder) holder).mReplyTo.setText(mList.get(position).getAVObject("reply_to").getString("comment"));
            }

            ((MyHolder) holder).mMore.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    PHRBottomDialog bottomDialog = new PHRBottomDialog(mContext);
                    bottomDialog.showBottomDialog();
                }
            });

            ((MyHolder) holder).mLike.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {

                }
            });

            ((MyHolder) holder).mLikeCount.setText("喜欢(" + mList.get(position).getInt("like") + ")");
            GlideUtils.loadRoundImage(mContext, mList.get(position).getAVUser("comment_user").getAVFile("head_img").getUrl(), ((MyHolder) holder).mIcon);
            ((MyHolder) holder).mName.setText(mList.get(position).getAVUser("comment_user").getUsername());
            ((MyHolder) holder).mDate.setText(DateUtils.parseDate(mList.get(position).getDate("date")));
            ((MyHolder) holder).mComment.setText(mList.get(position).getString("comment"));
            ((MyHolder) holder).mId.setText("#" + mList.get(position).getInt("id"));

            ((MyHolder) holder).mReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onReplyClickListener(mList.get(position).getInt("id"),mList.get(position).getAVUser("comment_user").getUsername()) ;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() == 0) {
            return 1;
        }
        return mList.size();
    }

    public void setList(List<AVObject> list) {
        mList.clear();
        this.mList = list;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        RoundedImageView mIcon;
        TextView mName;
        TextView mDate;
        TextView mAuthor;
        TextView mId;
        TextView mComment;
        TextView mReply;
        TextView mReplyTo;

        ShineButton mLike;
        TextView mLikeCount;
        ImageView mMore;
        LinearLayout mCanSee;
        TextView mReference;
        MyHolder(View itemView) {
            super(itemView);
            mIcon = (RoundedImageView) itemView.findViewById(R.id.phr_rv_my_article_comment_icon);
            mName = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_name);
            mDate = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_date);
            mAuthor = (TextView) itemView.findViewById(R.id.phr_rv_my_article_author);
            mId = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_id);

            mComment = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_comment);

            mLike = (ShineButton) itemView.findViewById(R.id.phr_rv_my_article_comment_like);
            mLikeCount = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_like_count);
            mMore = (ImageView) itemView.findViewById(R.id.phr_rv_my_article_comment_more);
            mReply = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_reply);



            mCanSee = (LinearLayout) itemView.findViewById(R.id.phr_rv_my_article_is_reply_see);
            mReplyTo = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_reply_other);
            mReference = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_reference);
        }
    }

    class NoneReply extends RecyclerView.ViewHolder {
        NoneReply(View itemView) {
            super(itemView);
        }
    }

}
