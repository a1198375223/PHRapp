package com.starstudio.loser.phrapp.common.view;

/*
    create by:loser
    date:2018/8/9 8:29
*/

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.view.adapter.PHRMoreRvAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PHRMoreDialog extends Dialog {
    private Context mContext;
    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PHRMoreDialog(@NonNull Context context) {
        super(context, R.style.PHR_BottomDialog);
        this.mContext = context;
        setContentView(R.layout.phr_artilce_more_dialog_layout);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.PHR_BottomDialog_Animator);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.phr_article_more_recycler_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        List<String> text = new ArrayList<>();
        text.add(mContext.getResources().getString(R.string.phr_article_menu_collect));
        text.add(mContext.getResources().getString(R.string.phr_article_menu_share));
        text.add(mContext.getResources().getString(R.string.phr_article_menu_complaints));
        text.add(mContext.getResources().getString(R.string.phr_article_menu_like));
        text.add(mContext.getResources().getString(R.string.phr_article_menu_dislike));

        int[] res = new int[]{R.mipmap.phr_article_menu_collect,
                R.mipmap.phr_article_menu_share,
                R.mipmap.phr_article_menu_warn,
                R.mipmap.phr_article_menu_like,
                R.mipmap.phr_article_menu_dislike};
        PHRMoreRvAdapter adapter = new PHRMoreRvAdapter(mContext);
        adapter.setListener(new PHRMoreRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (mListener != null) {
                    mListener.onItemClickListener(position);
                }
            }
        });
        adapter.setData(text, res);
        recyclerView.setAdapter(adapter);
        //-----------------------------------------

        TextView cancel = (TextView) findViewById(R.id.phr_article_more_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    public void showShareDialog() {
        if (!isShowing()) {
            setCanceledOnTouchOutside(true);
            setCancelable(true);
            show();
        }
    }
}
