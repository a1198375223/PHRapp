package com.starstudio.loser.phrapp.item.message.list.presenter;

/*
    create by:loser
    date:2018/7/27 10:01
*/

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.common.base.PHRFragmentPresenter;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;
import com.starstudio.loser.phrapp.item.message.list.web.PHRWebActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HealthPresenter extends PHRFragmentPresenter<CommonContract.View, CommonContract.SimpleModel> implements CommonContract.SimplePresenter{
    private CommonContract.SimpleModel mModel;
    private CommonContract.View mView;

    private FragmentEventListener mListener = new FragmentEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void startWebActivity(String url) {
            Intent intent = new Intent(getFragment().getActivity(), PHRWebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            intent.putExtras(bundle);
            Objects.requireNonNull(getFragment().getActivity()).startActivity(intent);
        }

        @Override
        public List<UsefulData> convertToUsefulData(BaseBean baseBean) {
            List<UsefulData> usefulData = new ArrayList<>();
            List<BaseBean.ResultBean.DataBean> dataBea  = baseBean.getResult().getData();
            for (int i=0; i<dataBea.size(); i++) {
                UsefulData data = new UsefulData();
                data.setTitle(dataBea.get(i).getTitle());
                data.setAuthor(dataBea.get(i).getAuthor_name());
                data.setImage1(dataBea.get(i).getThumbnail_pic_s());
                data.setImage2(dataBea.get(i).getThumbnail_pic_s02());
                data.setImage3(dataBea.get(i).getThumbnail_pic_s03());
                data.setUrl(dataBea.get(i).getUrl());
                usefulData.add(data);
            }
            return usefulData;
        }
    };

    public HealthPresenter(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected void onAttach() {
        mModel = getModel();
        mView = getView();
        mView.setEventListener(mListener);

        mModel.fetchModel((PHRFragment) getFragment())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.getResult() != null) {
                            mModel.saveToLeanCloud(baseBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showErrorToast("加载失败");
                    }

                    @Override
                    public void onComplete() {
                        mModel.getLeanCloudData();
                        mView.showProgressDialog();
                    }
                });
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public void loadView(List<UsefulData> list) {
        mView.loadRecyclerView(list);
        mView.dismissProgressDialog();
        //mView.showSuccessToast("加载成功");
    }
}
