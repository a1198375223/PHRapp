package com.starstudio.loser.phrapp.item.community.presenter;

/*
    create by:loser
    date:2018/7/30 15:48
*/

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.child.contract.DynamicContract;
import com.starstudio.loser.phrapp.item.community.child.contract.MyArticleContract;
import com.starstudio.loser.phrapp.item.community.child.model.DynamicModel;
import com.starstudio.loser.phrapp.item.community.child.model.MyArticleModel;
import com.starstudio.loser.phrapp.item.community.child.presenter.DynamicPresenter;
import com.starstudio.loser.phrapp.item.community.child.presenter.MyArticlePresenter;
import com.starstudio.loser.phrapp.item.community.child.view.DynamicView;
import com.starstudio.loser.phrapp.item.community.child.view.MyArticleView;
import com.starstudio.loser.phrapp.item.community.contract.CommunityContract;
import com.starstudio.loser.phrapp.item.community.fragment.view.WriteFragment;

public class CommunityPresenter extends PHRPresenter<CommunityContract.CommunityView, CommunityContract.CommunityModel> implements CommunityContract.CommunityPresenter{
    private CommunityContract.CommunityView mView;
    private CommunityContract.CommunityModel mModel;
    private DynamicView mDynamicView;
    private MyArticleView mMyArticleView;

    private CommunityEventListener mListener = new CommunityEventListener() {

        @Override
        public Fragment getFragment(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = mView == null ? null : mDynamicView;
                    break;
                case 1:
                    fragment = mView == null ? null : mMyArticleView;
                    break;
                default:
            }
            return fragment;
        }

        @Override
        public void startWriteFragment() {
            FragmentTransaction ft  = ((PHRActivity)getActivity()).getSupportFragmentManager().beginTransaction();
            WriteFragment fragment = new WriteFragment();
            ft.add(R.id.community_container, fragment);
            ft.addToBackStack("add fragment");
            ft.commit();
        }
    };

    public CommunityPresenter(Activity activity) {
        super(activity);
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();

        mView.setEventListener(mListener);

        mDynamicView = new DynamicView();

        mMyArticleView = new MyArticleView();
    }


    @Override
    protected void onDetach() {
    }

    @Override
    public void childRefresh() {
        mDynamicView.tellToRefresh();
        mMyArticleView.tellToRefresh();
    }
}
