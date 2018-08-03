package com.starstudio.loser.phrapp.item.message.list.adapter;

/*
    create by:loser
    date:2018/7/24 10:01
*/

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;

import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<UsefulData> mData;
    private OnItemClickListener mListener;

    public RvAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
    }

    //点击事件
    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setDataList(List<UsefulData> usefulData) {
        this.mData = usefulData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE.THREE_PICTURE.ordinal()) {
            return new ThreePictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_three_picture_layout, parent, false));
        } else {
            return new OnePictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.phr_rv_one_picture_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClickListener(position);
                }
            });
        }
        if (holder instanceof ThreePictureViewHolder) {
            GlideUtils.loadImage(mContext, mData.get(position).getImage1(), ((ThreePictureViewHolder) holder).mIcon1);
            GlideUtils.loadImage(mContext, mData.get(position).getImage2(), ((ThreePictureViewHolder) holder).mIcon2);
            GlideUtils.loadImage(mContext, mData.get(position).getImage3(), ((ThreePictureViewHolder) holder).mIcon3);
            ((ThreePictureViewHolder) holder).mAuthor.setText(mData.get(position).getAuthor());
            ((ThreePictureViewHolder) holder).mContent.setText(mData.get(position).getTitle());
        } else if (holder instanceof OnePictureViewHolder) {
            GlideUtils.loadImage(mContext, mData.get(position).getImage1(), ((OnePictureViewHolder) holder).mIcon);
            ((OnePictureViewHolder) holder).mContent.setText(mData.get(position).getTitle());
            ((OnePictureViewHolder) holder).mAuthor.setText(mData.get(position).getAuthor());
        }
    }

    enum TYPE{
        THREE_PICTURE,
        ONE_PICTURE
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getImage3() != null) {
            return TYPE.THREE_PICTURE.ordinal();
        }
        return TYPE.ONE_PICTURE.ordinal();
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    class OnePictureViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthor;
        TextView mContent;
        ImageView mIcon;
        public OnePictureViewHolder(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.phr_rv_one_image_author);
            mContent = (TextView) itemView.findViewById(R.id.phr_rv_one_image_text);
            mIcon = (ImageView) itemView.findViewById(R.id.phr_rv_one_image_icon);
        }
    }

    class ThreePictureViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthor;
        TextView mContent;
        ImageView mIcon1;
        ImageView mIcon2;
        ImageView mIcon3;
        public ThreePictureViewHolder(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.phr_rv_three_image_author);
            mContent = (TextView) itemView.findViewById(R.id.phr_rv_three_image_text);
            mIcon1 = (ImageView) itemView.findViewById(R.id.phr_rv_three_image_icon1);
            mIcon2 = (ImageView) itemView.findViewById(R.id.phr_rv_three_image_icon2);
            mIcon3 = (ImageView) itemView.findViewById(R.id.phr_rv_three_image_icon3);
        }
    }


}
