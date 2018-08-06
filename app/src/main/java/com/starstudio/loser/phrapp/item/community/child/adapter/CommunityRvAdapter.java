package com.starstudio.loser.phrapp.item.community.child.adapter;

/*
    create by:loser
    date:2018/7/31 20:15
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVSaveOption;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.utils.DateUtils;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;
import com.starstudio.loser.phrapp.common.utils.ToastyUtils;

import java.util.ArrayList;
import java.util.List;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

public class CommunityRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AVObject> mList;
    private Context mContext;
    private OnItemClickListener mListener;

    public CommunityRvAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public void setList(List<AVObject> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(List<AVObject> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
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
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_community_message_layout, parent, false));
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
            ((MyHolder) holder).mLikeCount.setText(mList.get(position).getInt("like")+"");
            ((MyHolder) holder).mDislikeCount.setText(mList.get(position).getInt("dislike")+"");
            ((MyHolder) holder).mMessageCount.setText(mList.get(position).getInt("reply")+"");
            initBoomButton((MyHolder)holder);
            ((MyHolder) holder).mLike.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    if (checked) {
                        AVObject avObject = AVObject.createWithoutData("Article", mList.get(position).getObjectId());
                        String key = "like";
                        final int like = avObject.getInt("like");
                        avObject.fetchInBackground(key, new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                if (e == null && avObject != null) {
                                    AVSaveOption option = new AVSaveOption();
                                    option.query(new AVQuery("Article").whereEqualTo("like", like));
                                    option.setFetchWhenSave(true);
                                    avObject.increment("like", 1);
                                    avObject.saveInBackground(option, new SaveCallback() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                ((MyHolder) holder).mLikeCount.setText(like + 1 + "");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        AVObject avObject = AVObject.createWithoutData("Article", mList.get(position).getObjectId());
                        String key = "like";
                        final int like = avObject.getInt("like");
                        avObject.fetchInBackground(key, new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                if (e == null && avObject != null) {
                                    AVSaveOption option = new AVSaveOption();
                                    option.query(new AVQuery("Article").whereEqualTo("like", like));
                                    option.setFetchWhenSave(true);
                                    avObject.increment("like", -1);
                                    avObject.saveInBackground(option, new SaveCallback() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                ((MyHolder) holder).mLikeCount.setText(like - 1 + "");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
            ((MyHolder) holder).mDislike.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    if (checked) {
                        AVObject avObject = AVObject.createWithoutData("Article", mList.get(position).getObjectId());
                        String key = "dislike";
                        final int dislike = avObject.getInt("dislike");
                        avObject.fetchInBackground(key, new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                if (e == null && avObject != null) {
                                    AVSaveOption option = new AVSaveOption();
                                    option.query(new AVQuery("Article").whereEqualTo("dislike", dislike));
                                    option.setFetchWhenSave(true);
                                    avObject.increment("dislike", 1);
                                    avObject.saveInBackground(option, new SaveCallback() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                ((MyHolder) holder).mDislikeCount.setText(dislike + 1 + "");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        AVObject avObject = AVObject.createWithoutData("Article", mList.get(position).getObjectId());
                        String key = "dislike";
                        final int dislike = avObject.getInt("dislike");
                        avObject.fetchInBackground(key, new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                if (e == null && avObject != null) {
                                    AVSaveOption option = new AVSaveOption();
                                    option.query(new AVQuery("Article").whereEqualTo("dislike", dislike));
                                    option.setFetchWhenSave(true);
                                    avObject.increment("dislike", -1);
                                    avObject.saveInBackground(option, new SaveCallback() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                ((MyHolder) holder).mDislikeCount.setText(dislike - 1 + "");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void initBoomButton(MyHolder holder) {
        holder.mShare.setButtonEnum(ButtonEnum.TextOutsideCircle);
        holder.mShare.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_1);
        holder.mShare.setButtonPlaceEnum(ButtonPlaceEnum.SC_4_1);
        holder.mShare.clearBuilders();
        TextOutsideCircleButton.Builder blue = new TextOutsideCircleButton.Builder()
                //图片
                .normalImageRes(R.mipmap.phr_boom_delete_normal)
                .highlightedImageRes(R.mipmap.phr_boom_delete_highlight)
                //文字
                .normalTextRes(R.string.phr_message_menu_delete)
                .normalTextColorRes(R.color.avoscloud_feedback_white)
                .highlightedTextColorRes(R.color.color_text_title)
                //按钮
                .normalColorRes(R.color.color_boom_blue_normal_color)
                .highlightedColorRes(R.color.color_boom_blue_highlight_color)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        ToastyUtils.showSuccess("click: " + index);
                    }
                });

        TextOutsideCircleButton.Builder purple = new TextOutsideCircleButton.Builder()
                //图片
                .normalImageRes(R.mipmap.phr_boom_share_normal)
                .highlightedImageRes(R.mipmap.phr_boom_share_highlight)
                //文字
                .normalTextRes(R.string.phr_boom_share_text)
                .normalTextColorRes(R.color.avoscloud_feedback_white)
                .highlightedTextColorRes(R.color.color_text_title)
                //按钮
                .normalColorRes(R.color.color_boom_purple_normal_color)
                .highlightedColorRes(R.color.color_boom_purple_highlight_color)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        ToastyUtils.showSuccess("click: " + index);
                    }
                });
        TextOutsideCircleButton.Builder yellow = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.mipmap.phr_boom_warn_normal)
                .highlightedImageRes(R.mipmap.phr_boom_warn_highlight)
                //文字
                .normalTextRes(R.string.phr_boom_complain_text)
                .normalTextColorRes(R.color.avoscloud_feedback_white)
                .highlightedTextColorRes(R.color.color_text_title)
                //按钮
                .normalColorRes(R.color.color_boom_yellow_normal_color)
                .highlightedColorRes(R.color.color_boom_yellow_highlight_color)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        ToastyUtils.showSuccess("click: " + index);
                    }
                });
        TextOutsideCircleButton.Builder red = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.mipmap.phr_boom_collect_normal)
                .highlightedImageRes(R.mipmap.phr_boom_collect_highlight)
                //文字
                .normalTextRes(R.string.phr_boom_get_text)
                .normalTextColorRes(R.color.avoscloud_feedback_white)
                .highlightedTextColorRes(R.color.color_text_title)
                //按钮
                .normalColorRes(R.color.color_boom_red_normal_color)
                .highlightedColorRes(R.color.color_boom_red_highlight_color)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        ToastyUtils.showSuccess("click: " + index);
                    }
                });
        holder.mShare.addBuilder(purple);
        holder.mShare.addBuilder(blue);
        holder.mShare.addBuilder(yellow);
        holder.mShare.addBuilder(red);
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
        BoomMenuButton mShare;
        TextView mDate;
        TextView mTitle;
        TextView mText;
        ShineButton mLike;
        ShineButton mDislike;
        TextView mLikeCount;
        TextView mDislikeCount;
        ImageView mMessage;
        TextView mMessageCount;
        MyHolder(View itemView) {
            super(itemView);
            mHeadIcon = (RoundedImageView) itemView.findViewById(R.id.phr_rv_community_message_icon);
            mUsername = (TextView) itemView.findViewById(R.id.phr_rv_community_message_name);
            mDescription = (TextView) itemView.findViewById(R.id.phr_rv_community_message_description);
            mShare = (BoomMenuButton) itemView.findViewById(R.id.phr_rv_community_message_share);
            mDate = (TextView) itemView.findViewById(R.id.phr_rv_community_message_time);
            mTitle = (TextView) itemView.findViewById(R.id.phr_rv_community_message_title);
            mText = (TextView) itemView.findViewById(R.id.phr_rv_community_message_body_text);
            mLike = (ShineButton) itemView.findViewById(R.id.phr_rv_community_message_like);
            mDislike = (ShineButton) itemView.findViewById(R.id.phr_rv_community_message_dislike);
            mLikeCount = (TextView) itemView.findViewById(R.id.phr_rv_community_message_like_count);
            mDislikeCount = (TextView) itemView.findViewById(R.id.phr_rv_community_message_dislike_count);
            mMessage = (ImageView) itemView.findViewById(R.id.phr_rv_community_message_message);
            mMessageCount = (TextView) itemView.findViewById(R.id.phr_rv_community_message_comment_count);
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
