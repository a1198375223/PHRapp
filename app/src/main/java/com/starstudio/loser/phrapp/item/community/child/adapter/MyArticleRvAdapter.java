package com.starstudio.loser.phrapp.item.community.child.adapter;

/*
    create by:loser
    date:2018/8/2 16:16
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
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.utils.DateUtils;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;
import com.starstudio.loser.phrapp.common.utils.ToastyUtils;

import java.util.ArrayList;
import java.util.List;

public class MyArticleRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AVObject> mList;
    private Context mContext;
    private OnItemClickListener mListener;

    public MyArticleRvAdapter(Context context) {
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

        void onMenuItemClickListener(int position, int index);
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_my_article_layout, parent, false));
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
            initBoomButtonMenu((MyHolder)holder, position);
        }
    }

    private void initBoomButtonMenu(MyHolder holder, final int position) {
        holder.mShare.setButtonEnum(ButtonEnum.Ham);
        holder.mShare.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        holder.mShare.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        holder.mShare.clearBuilders();
        HamButton.Builder blue = new HamButton.Builder()
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
                        if (mListener != null) {
                            mListener.onMenuItemClickListener(position, index);
                        }
                    }
                });

        HamButton.Builder purple = new HamButton.Builder()
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
                        if (mListener != null) {
                            mListener.onMenuItemClickListener(position, index);
                        }
                    }
                });
        HamButton.Builder yellow = new HamButton.Builder()
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
                        if (mListener != null) {
                            mListener.onMenuItemClickListener(position, index);
                        }
                    }
                });
        HamButton.Builder red = new HamButton.Builder()
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
                        if (mListener != null) {
                            mListener.onMenuItemClickListener(position, index);
                        }
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
        MyHolder(View itemView) {
            super(itemView);
            mHeadIcon = (RoundedImageView) itemView.findViewById(R.id.phr_rv_my_article_icon);
            mUsername = (TextView) itemView.findViewById(R.id.phr_rv_my_article_name);
            mDescription = (TextView) itemView.findViewById(R.id.phr_rv_my_article_description);
            mShare = (BoomMenuButton) itemView.findViewById(R.id.phr_rv_my_article_share);
            mDate = (TextView) itemView.findViewById(R.id.phr_rv_my_article_time);
            mTitle = (TextView) itemView.findViewById(R.id.phr_rv_my_article_title);
            mText = (TextView) itemView.findViewById(R.id.phr_rv_my_article_body_text);
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
