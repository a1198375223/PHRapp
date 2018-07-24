package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/24 12:52
*/

import android.support.v4.app.Fragment;

public abstract class PHRFragmentPresenter<V extends BaseView, M extends BaseModel> implements BasePresenter<V, M> {
    private V mView;
    private M mModel;
    private Fragment mFragment;

    public PHRFragmentPresenter(Fragment fragment) {
        this.mFragment = fragment;
    }

    protected Fragment getFragment(){
        return mFragment;
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
