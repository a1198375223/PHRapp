package com.starstudio.loser.phrapp.common.view;

/*
    create by:loser
    date:2018/8/2 21:13
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
import android.widget.Toast;

import com.starstudio.loser.phrapp.R;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class PHRBottomDialog extends Dialog{
    private Context mContext;
    public static final int EXIT = 1;
    public static final int COLLECT = 2;
    public static final int WARN = 3;
    private OnItemClickListener mListener;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PHRBottomDialog(@NonNull Context context) {
        super(context, R.style.PHR_BottomDialog);
        this.mContext = context;
        setContentView(R.layout.phr_bottom_dialog_layout);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.PHR_BottomDialog_Animator);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView exit = (TextView) findViewById(R.id.phr_bottom_dialog_exit);
        TextView collect = (TextView) findViewById(R.id.phr_bottom_dialog_collect);
        TextView warn = (TextView) findViewById(R.id.phr_bottom_dialog_warn);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClickListener(EXIT);
                    if (isShowing()) {
                        dismiss();
                    }
                } else {
                    Toasty.warning(mContext, "没有设置监听器", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClickListener(COLLECT);
                } else {
                    Toasty.warning(mContext, "没有设置监听器", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        warn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClickListener(WARN);
                } else {
                    Toasty.warning(mContext, "没有设置监听器", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

    }

    public void showBottomDialog() {
        if (!isShowing()) {
            setCanceledOnTouchOutside(true);
            setCancelable(true);
            show();
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(int which);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

}
