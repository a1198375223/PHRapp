package com.starstudio.loser.phrapp.common.view;

/*
    create by:loser
    date:2018/8/9 10:15
*/

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starstudio.loser.phrapp.R;

import java.util.Objects;

public class PHRSortDialog extends Dialog {
    private Context mContext;
    private int old = 1;
    private OnItemClickListener mListener;
    public static final int ITEM_1 = 1;
    public static final int ITEM_2 = 2;
    public static final int ITEM_3 = 3;
    public static final int ITEM_4 = 4;
    private TextView mItem1, mItem2, mItem3, mItem4;

    public interface OnItemClickListener{
        void onItemClickListener(int which);
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PHRSortDialog(@NonNull Context context) {
        super(context, R.style.PHR_BottomDialog);
        this.mContext = context;
        setContentView(R.layout.phr_sort_dialog_layout);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.PHR_BottomDialog_Animator);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem1 = (TextView) findViewById(R.id.phr_article_sort_item1);
        mItem2 = (TextView) findViewById(R.id.phr_article_sort_item2);
        mItem3 = (TextView) findViewById(R.id.phr_article_sort_item3);
        mItem4 = (TextView) findViewById(R.id.phr_article_sort_item4);
        TextView cancel = (TextView) findViewById(R.id.phr_article_sort_item5);
        mItem1.setTextColor(mContext.getColor(R.color.color_boom_red_normal_color));

        mItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    dismiss();
                    mListener.onItemClickListener(ITEM_1);
                    setColor(ITEM_1);
                }
            }
        });

        mItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    dismiss();
                    mListener.onItemClickListener(ITEM_2);
                    setColor(ITEM_2);
                }
            }
        });
        mItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    dismiss();
                    mListener.onItemClickListener(ITEM_3);
                    setColor(ITEM_3);
                }
            }
        });
        mItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    dismiss();
                    mListener.onItemClickListener(ITEM_4);
                    setColor(ITEM_4);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setColor(int position) {
        restore(old);
        switch (position) {
            case 1:
                mItem1.setTextColor(mContext.getColor(R.color.color_boom_red_normal_color));
                break;
            case 2:
                mItem2.setTextColor(mContext.getColor(R.color.color_boom_red_normal_color));
                break;
            case 3:
                mItem3.setTextColor(mContext.getColor(R.color.color_boom_red_normal_color));
                break;
            case 4:
                mItem4.setTextColor(mContext.getColor(R.color.color_boom_red_normal_color));
                break;
        }
        old = position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void restore(int old) {
        switch (old) {
            case 1:
                mItem1.setTextColor(mContext.getColor(R.color.color_text_sub_title));
                break;
            case 2:
                mItem2.setTextColor(mContext.getColor(R.color.color_text_sub_title));
                break;
            case 3:
                mItem3.setTextColor(mContext.getColor(R.color.color_text_sub_title));
                break;
            case 4:
                mItem4.setTextColor(mContext.getColor(R.color.color_text_sub_title));
                break;
        }
    }

    public void showSortDialog() {
        if (!isShowing()) {
            setCanceledOnTouchOutside(true);
            setCancelable(true);
            show();
        }
    }
}
