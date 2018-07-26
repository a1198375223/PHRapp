package com.starstudio.loser.phrapp.item.message.list.presenter;

/*
    create by:loser
    date:2018/7/25 14:08
*/

import android.support.v4.app.Fragment;

import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.common.base.PHRFragmentPresenter;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CommonPresenter extends PHRFragmentPresenter<CommonContract.View, CommonContract.Model> implements CommonContract.Presenter{
    private CommonContract.Model mModel;
    private CommonContract.View mView;

    public CommonPresenter(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected void onAttach() {
        mModel = getModel();
        mView = getView();

        mModel.fetchModel((PHRFragment) getFragment())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean != null)
                            mView.load(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDetach() {

    }
}
