package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/23 13:08
*/

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.starstudio.loser.phrapp.common.view.PHRProgressDialog;


public class PHRView<E extends BaseEventListener> implements BaseView<E> {
    private Activity mActivity;
    private E mListener;
    private PHRProgressDialog mDialog;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new PHRProgressDialog(getActivity());
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

}
