package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/23 13:08
*/

import android.app.Activity;


public class PHRView<E extends BaseEventListener> implements BaseView<E> {
    private Activity mActivity;
    private E mListener;

    public PHRView(Activity activity) {
        this.mActivity = activity;
    }

    protected Activity getActivity() {
        return mActivity;
    }



    public E getListener() {
        return mListener;
    }

    @Override
    public void setEventListener(E eventListener) {
        this.mListener = eventListener;
    }

}
