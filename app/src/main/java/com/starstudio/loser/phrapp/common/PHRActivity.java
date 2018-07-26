package com.starstudio.loser.phrapp.common;

/*
    create by:loser
    date:2018/7/23 13:22
*/

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.starstudio.loser.phrapp.common.base.BaseEventListener;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.common.view.PHRProgressDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class PHRActivity<E extends BaseEventListener> extends RxAppCompatActivity implements BaseView<E>{
    private E mListener;
    private PHRProgressDialog mDialog;

    @Override
    public void setEventListener(E eventListener) {
        this.mListener = eventListener;
    }

    public Activity getActivity(){
        return this;
    }

    public E getListener() {
        return mListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showProgressDialog() {
        if (mDialog == null && !this.isFinishing()) {
            mDialog = new PHRProgressDialog(getActivity());
            mDialog.showProgressDialog();
        } else if(isFinishing()){
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
