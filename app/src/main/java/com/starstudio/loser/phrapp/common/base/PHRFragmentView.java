package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/24 14:40
*/

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.starstudio.loser.phrapp.common.view.PHRProgressDialog;

import java.util.Objects;

public abstract class PHRFragmentView<E extends BaseEventListener> implements BaseView<E>{
    private Fragment mFragment;
    private E mListener;
    private PHRProgressDialog mDialog;

    public PHRFragmentView(Fragment fragment) {
        this.mFragment = fragment;
    }

    protected Fragment getFragment() {
        return mFragment;
    }

    @Override
    public void setEventListener(E e) {
        mListener = e;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new PHRProgressDialog(Objects.requireNonNull(getFragment().getContext()));
            mDialog.showProgressDialog();
        }
        mDialog.showProgressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public E getListener() {
        return mListener;
    }
}
