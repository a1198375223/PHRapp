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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.utils.DateUtils;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;
import com.starstudio.loser.phrapp.common.view.PHRBottomDialog;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.avos.avoscloud.AVAnalytics.TAG;

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

        void onShowReplyClickListener(int position);
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
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
                    final PHRBottomDialog bottomDialog = new PHRBottomDialog(mContext);
                    bottomDialog.setOnItemClickListener(new PHRBottomDialog.OnItemClickListener() {
                        @Override
                        public void onItemClickListener(int which) {
                            switch (which) {
                                case PHRBottomDialog.DISLIKE:
                                    if (AVUser.getCurrentUser() == null) {
                                        showError("请先登入");
                                    } else {
                                        AVQuery<AVObject> dislike = new AVQuery<>("CommentDislike");
                                        dislike.include("comment_dislike_user").include("comment").whereEqualTo("comment", mList.get(position));
                                        dislike.findInBackground(new FindCallback<AVObject>() {
                                            @Override
                                            public void done(final List<AVObject> list, AVException e) {
                                                if (e == null) {
                                                    boolean isContain = false;
                                                    if (list.size() == 0) {
                                                        isContain = false;
                                                    } else {
                                                        for (AVObject av : list) {
                                                            if (av.getAVUser("comment_dislike_user").equals(AVUser.getCurrentUser())) {
                                                                isContain = true;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (isContain) {
                                                        showWarning("亲,您已经点灭过了!");
                                                    } else {
                                                        AVObject dislike = new AVObject("CommentDislike");
                                                        dislike.put("comment_dislike_user", AVUser.getCurrentUser());
                                                        dislike.put("comment", mList.get(position));
                                                        mList.get(position).increment("dislike");
                                                        mList.get(position).setFetchWhenSave(true);
                                                        dislike.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(AVException e) {
                                                                if (e == null) {
                                                                    showSuccess("点灭成功");
                                                                } else {
                                                                    showError("出错啦");
                                                                }
                                                            }
                                                        });
                                                    }
                                                } else {
                                                    showError("出错啦");
                                                }
                                            }
                                        });
                                        if (bottomDialog.isShowing()) {
                                            bottomDialog.dismiss();
                                        }
                                    }
                                    break;
                                case PHRBottomDialog.WARN:
                                    if (AVUser.getCurrentUser() == null) {
                                        showError("请先登入");
                                    } else {
                                        AVQuery<AVObject> complaints = new AVQuery<>("CommentComplaints");
                                        complaints.include("comment_complaints_user").include("comment").whereEqualTo("comment", mList.get(position));
                                        complaints.findInBackground(new FindCallback<AVObject>() {
                                            @Override
                                            public void done(final List<AVObject> list, AVException e) {
                                                if (e == null) {
                                                    boolean isContain = false;
                                                    if (list.size() == 0) {
                                                        isContain = false;
                                                    } else {
                                                        for (AVObject av : list) {
                                                            if (av.getAVUser("comment_complaints_user").equals(AVUser.getCurrentUser())) {
                                                                isContain = true;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (isContain) {
                                                        showWarning("亲,您已经举报过了!");
                                                    } else {
                                                        AVObject complaints = new AVObject("CommentComplaints");
                                                        complaints.put("comment_complaints_user", AVUser.getCurrentUser());
                                                        complaints.put("comment", mList.get(position));
                                                        mList.get(position).increment("complaints");
                                                        mList.get(position).setFetchWhenSave(true);
                                                        complaints.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(AVException e) {
                                                                if (e == null) {
                                                                    showSuccess("举报成功");
                                                                } else {
                                                                    showError("出错啦");
                                                                }
                                                            }
                                                        });
                                                    }
                                                } else {
                                                    showError("出错啦");
                                                }
                                            }
                                        });
                                        if (bottomDialog.isShowing()) {
                                            bottomDialog.dismiss();
                                        }
                                    }
                                    break;
                                case PHRBottomDialog.EXIT:
                                    if (bottomDialog.isShowing()) {
                                        bottomDialog.dismiss();
                                    }
                                    break;
                            }
                        }
                    });
                    bottomDialog.showBottomDialog();
                }
            });

            ((MyHolder) holder).mLike.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    if (checked) {
                        if (AVUser.getCurrentUser() == null) {
                            showError("请先登入");
                        } else {
                            AVQuery<AVObject> like = new AVQuery<>("CommentLike");
                            like.include("comment_like_user").include("comment").whereEqualTo("comment", mList.get(position));
                            like.findInBackground(new FindCallback<AVObject>() {
                                @Override
                                public void done(final List<AVObject> list, AVException e) {
                                    if (e == null) {
                                        boolean isContain = false;
                                        if (list.size() == 0) {
                                            isContain = false;
                                        } else {
                                            for (AVObject av : list) {
                                                if (av.getAVUser("comment_like_user").equals(AVUser.getCurrentUser())) {
                                                    isContain = true;
                                                    break;
                                                }
                                            }
                                        }
                                        if (isContain) {
                                            showWarning("亲,您已经点亮过了!");
                                        } else {
                                            AVObject like = new AVObject("CommentLike");
                                            like.put("comment_like_user", AVUser.getCurrentUser());
                                            like.put("comment", mList.get(position));
                                            mList.get(position).increment("like");
                                            mList.get(position).setFetchWhenSave(true);
                                            like.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(AVException e) {
                                                    if (e == null) {
                                                        ((MyHolder) holder).mLikeCount.setText("喜欢(" + mList.get(position).getInt("like") + ")");
                                                    } else {
                                                        showError("出错啦");
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        showError("出错啦");
                                    }
                                }
                            });
                        }
                    }
                }
            });

            ((MyHolder) holder).mLikeCount.setText("喜欢(" + mList.get(position).getInt("like") + ")");
            GlideUtils.loadRoundImage(mContext, mList.get(position).getAVUser("comment_user").getAVFile("head_img").getUrl(), ((MyHolder) holder).mIcon);
            ((MyHolder) holder).mName.setText(mList.get(position).getAVUser("comment_user").getUsername());
            ((MyHolder) holder).mDate.setText(DateUtils.parseDate(mList.get(position).getDate("date")));
            ((MyHolder) holder).mComment.setText(mList.get(position).getString("comment"));
            ((MyHolder) holder).mId.setText("#" + mList.get(position).getInt("id"));
            Log.d(TAG, "onBindViewHolder:  position:" + position + "  reply:" + mList.get(position).getInt("reply"));
            if (mList.get(position).getInt("reply") != 0) {
                ((MyHolder) holder).mShowReply.setVisibility(View.VISIBLE);
                ((MyHolder) holder).mShowReply.setText("查看回复(" + mList.get(position).getInt("reply") + ")");
                ((MyHolder) holder).mShowReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onShowReplyClickListener(position);
                        }
                    }
                });
            } else {
                ((MyHolder) holder).mShowReply.setVisibility(View.INVISIBLE);
            }

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

        TextView mShowReply;

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

            mShowReply = (TextView) itemView.findViewById(R.id.phr_rv_my_article_comment_show_reply);
        }
    }

    class NoneReply extends RecyclerView.ViewHolder {
        NoneReply(View itemView) {
            super(itemView);
        }
    }

    private void showError(String error) {
        Toasty.error(mContext, error, Toast.LENGTH_SHORT, true).show();
    }

    private void showSuccess(String success) {
        Toasty.success(mContext,success, Toast.LENGTH_SHORT, true).show();
    }

    private void showWarning(String warning) {
        Toasty.warning(mContext, warning, Toast.LENGTH_SHORT, true).show();
    }


}
