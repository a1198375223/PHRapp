package com.starstudio.loser.phrapp.common;

/*
    create by:loser
    date:2018/7/24 14:39
*/

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.base.BaseEventListener;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.common.view.PHRProgressDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static android.support.constraint.Constraints.TAG;


public abstract class PHRFragment<E extends BaseEventListener> extends RxFragment implements BaseView<E>{
    private E mListener;
    private PHRProgressDialog mDialog;
    private boolean isVisible;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_fragment_layout, container, false);
//        if (isVisible) {
//            initFragment(view);
//        }
        initFragment(view);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;

    }

    public Fragment getFragment(){
        return this;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//    }

    public abstract void initFragment(View view);

    @Override
    public void setEventListener(E eventListener) {
        this.mListener = eventListener;
    }

    public E getListener() {
        return mListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new PHRProgressDialog(Objects.requireNonNull(getFragment().getActivity()));
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showErrorToast(String message) {
        Toasty.error(Objects.requireNonNull(getActivity()), message, Toast.LENGTH_SHORT, true).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showWarningToast(String message) {
        Toasty.warning(Objects.requireNonNull(getActivity()), message, Toast.LENGTH_SHORT, true).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showSuccessToast(String message) {
        Toasty.success(Objects.requireNonNull(getActivity()), message, Toast.LENGTH_SHORT, true).show();
    }
}
