package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/23 13:08
*/

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.starstudio.loser.phrapp.common.view.PHRProgressDialog;

import es.dmoral.toasty.Toasty;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;


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


    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new PHRProgressDialog(getActivity());
            mDialog.showProgressDialog();
        }else {
            mDialog.showProgressDialog();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void showErrorToast(String message) {
        Toasty.error(getActivity(), message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void showWarningToast(String message) {
        Toasty.warning(getActivity(), message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void showSuccessToast(String message) {
        Toasty.success(getActivity(), message, Toast.LENGTH_SHORT, true).show();
    }

}
