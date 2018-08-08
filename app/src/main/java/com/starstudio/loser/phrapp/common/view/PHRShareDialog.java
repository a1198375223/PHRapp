package com.starstudio.loser.phrapp.common.view;

/*
    create by:loser
    date:2018/8/8 13:00
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starstudio.loser.phrapp.R;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class PHRShareDialog extends Dialog {
    private Context mContext;
    private OnShareItemClickListener mListener;
    public static final int WEIBO = 1;
    public static final int WECHAT = 2;
    public static final int QQ = 3;
    private String title = "default title";
    private String text  = "default text";

    public interface OnShareItemClickListener{
        void onShareItemClickListener(int which, String title, String text);

        void onCancelItemClickListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PHRShareDialog(@NonNull Context context) {
        super(context, R.style.PHR_BottomDialog);
        this.mContext = context;
        setContentView(R.layout.phr_share_bottom_share_dialog);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.PHR_BottomDialog_Animator);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout weibo = (LinearLayout) findViewById(R.id.phr_share_weibo);
        LinearLayout wechat = (LinearLayout) findViewById(R.id.phr_share_wechat);
        LinearLayout qq = (LinearLayout) findViewById(R.id.phr_share_qq);
        TextView cancel = (TextView) findViewById(R.id.phr_share_cancel);

        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onShareItemClickListener(WEIBO, title, text);
                } else {
                    Toasty.error(mContext, "没有设置监听器", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onShareItemClickListener(WECHAT, title, text);
                } else {
                    Toasty.error(mContext, "没有设置监听器", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onShareItemClickListener(QQ, title, text);
                } else {
                    Toasty.error(mContext, "没有设置监听器", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCancelItemClickListener();
                } else {
                    Toasty.error(mContext, "没有设置监听器", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    public void setListener(OnShareItemClickListener listener) {
        this.mListener = listener;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void showShareDialog() {
        if (!isShowing()) {
            setCanceledOnTouchOutside(true);
            setCancelable(true);
            show();
        }
    }
}
