package com.starstudio.loser.phrapp.common.view;

/*
    create by:loser
    date:2018/8/9 21:24
*/

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.view.adapter.SearchDialogRvAdapter;

import java.util.List;
import java.util.Objects;

public class PHRSearchDialog extends Dialog {
    private Context mContext;
    private SearchDialogRvAdapter mAdapter;
    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setList(List<AVObject> list) {
        mAdapter.setList(list);
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PHRSearchDialog(@NonNull Context context) {
        super(context, R.style.PHR_SearchDialog);
        this.mContext = context;
        setContentView(R.layout.phr_recycler_view);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.TOP);
        getWindow().setWindowAnimations(R.style.PHR_BottomDialog_Animator);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mAdapter = new SearchDialogRvAdapter(mContext);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.phr_comment_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setListener(new SearchDialogRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (mListener != null) {
                    mListener.onItemClickListener(position);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    public void showSearchDialog() {
        if (!isShowing()) {
            setCanceledOnTouchOutside(false);
            setCancelable(true);
            show();
        }
    }
}
