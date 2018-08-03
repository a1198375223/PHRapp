package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/23 13:09
*/

import android.app.Activity;

public abstract class PHRPresenter<V extends BaseView, M extends BaseModel> implements BasePresenter<V, M> {
    private V mView;
    private M mModel;
    private Activity mActivity;

    public PHRPresenter(Activity activity) {
        this.mActivity = activity;
    }

    protected Activity getActivity(){
        return mActivity;
    }

    @Override
    public void setView(V view) {
        this.mView = view;
    }

    @Override
    public void setModel(M model) {
        this.mModel = model;
    }

    @Override
    public void attach() {
        onAttach();
    }

    @Override
    public void detach() {
        if (mView != null) {
            mView = null;
        }

        if (mModel != null) {
            mModel = null;
        }

        onDetach();
    }


    protected V getView() {
        return mView;
    }


    protected M getModel() {
        return mModel;
    }



    protected abstract void onAttach();
    protected abstract void onDetach();
}
