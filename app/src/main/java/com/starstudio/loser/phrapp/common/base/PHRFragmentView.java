package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/24 14:40
*/

import android.support.v4.app.Fragment;

public class PHRFragmentView<E extends BaseEventListener> implements BaseView<E>{
    private Fragment mFragment;
    private E mListener;

    public PHRFragmentView(Fragment fragment) {
        this.mFragment = fragment;
    }

    protected Fragment getActivity() {
        return mFragment;
    }

    @Override
    public void setEventListener(E e) {
        mListener = e;
    }

    public E getListener() {
        return mListener;
    }
}
