package com.starstudio.loser.phrapp.item.community.presenter;

/*
    create by:loser
    date:2018/7/30 15:48
*/

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.child.contract.DynamicContract;
import com.starstudio.loser.phrapp.item.community.child.contract.MyArticleContract;
import com.starstudio.loser.phrapp.item.community.child.model.DynamicViewModel;
import com.starstudio.loser.phrapp.item.community.child.model.MyArticleViewModel;
import com.starstudio.loser.phrapp.item.community.child.presenter.DynamicViewPresenter;
import com.starstudio.loser.phrapp.item.community.child.presenter.MyArticleViewPresenter;
import com.starstudio.loser.phrapp.item.community.child.view.DynamicView;
import com.starstudio.loser.phrapp.item.community.child.view.MyArticleView;
import com.starstudio.loser.phrapp.item.community.contract.CommunityContract;
import com.starstudio.loser.phrapp.item.community.fragment.view.WriteFragment;

public class CommunityPresenter extends PHRPresenter<CommunityContract.CommunityView, CommunityContract.CommunityModel> implements CommunityContract.CommunityPresenter{
    private CommunityContract.CommunityView mView;
    private CommunityContract.CommunityModel mModel;

    private DynamicContract.DynamicChildView mDynamicChildView;
    private MyArticleContract.MyArticleChildView mMyArticleChildView;

    private CommunityEventListener mListener = new CommunityEventListener() {

        @Override
        public void startWriteFragment() {
            FragmentTransaction ft  = ((PHRActivity)getActivity()).getSupportFragmentManager().beginTransaction();
            WriteFragment fragment = new WriteFragment();
            ft.add(R.id.community_container, fragment);
            ft.addToBackStack("add fragment");
            ft.commit();
        }

        @Override
        public View getView(int position) {
            View view = null;
            switch (position) {
                case 0:
                    view = mView == null ? null : mDynamicChildView.getView();
                    break;
                case 1:
                    view = mView == null ? null : mMyArticleChildView.getView();
                    break;
                default:
            }
            return view;
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

        init();
    }

    private void init(){
        mDynamicChildView = new DynamicView(getActivity());
        DynamicContract.DynamicChildPresenter presenter = new DynamicViewPresenter(getActivity());
        presenter.setView(mDynamicChildView);
        presenter.setModel(new DynamicViewModel(presenter));
        presenter.attach();

        mMyArticleChildView = new MyArticleView(getActivity());
        MyArticleContract.MyArticleChildPresenter presenter1 = new MyArticleViewPresenter(getActivity());
        presenter1.setView(mMyArticleChildView);
        presenter1.setModel(new MyArticleViewModel(presenter1));
        presenter1.attach();
    } 


    @Override
    protected void onDetach() {
    }

    @Override
    public void childRefresh() {
        mMyArticleChildView.tellToRefresh();
        mDynamicChildView.tellToRefresh();
    }
}
