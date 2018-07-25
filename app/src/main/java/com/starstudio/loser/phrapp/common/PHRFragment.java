package com.starstudio.loser.phrapp.common;

/*
    create by:loser
    date:2018/7/24 14:39
*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.base.BaseEventListener;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.trello.rxlifecycle2.components.support.RxFragment;


public abstract class PHRFragment<E extends BaseEventListener> extends RxFragment implements BaseView<E>{
    private E mListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_fragment_layout, container, false);
        initFragment(view);
        return view;
    }

    public abstract void initFragment(View view);

    @Override
    public void setEventListener(E eventListener) {
        this.mListener = eventListener;
    }

    public E getListener() {
        return mListener;
    }
}
