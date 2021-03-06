package com.starstudio.loser.phrapp.item.community.fragment.presenter;

/*
    create by:loser
    date:2018/7/31 15:19
*/

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.avos.avoscloud.AVUser;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRFragmentPresenter;
import com.starstudio.loser.phrapp.item.community.fragment.contract.WriteFragmentContract;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class WriteFragmentPresenter extends PHRFragmentPresenter<WriteFragmentContract.WriteView, WriteFragmentContract.WriteModel> implements WriteFragmentContract.WritePresenter {
    private WriteFragmentContract.WriteView mView;
    private WriteFragmentContract.WriteModel mModel;
    private WriteFragmentEventListener mListener = new WriteFragmentEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void post(String title, String text) {
            AVUser avUser = AVUser.getCurrentUser();
            if (avUser == null) {
                mView.showErrorDialog();
            } else {
                mModel.saveToDataBase(title, text, avUser);
            }
        }
    };

    public WriteFragmentPresenter(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();

        mView.setEventListener(mListener);
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public void tellViewToDestroy() {
        mView.destroySelf();
    }
}