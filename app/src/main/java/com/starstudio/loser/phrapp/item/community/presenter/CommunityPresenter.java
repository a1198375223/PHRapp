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
import com.starstudio.loser.phrapp.item.community.contract.CommunityContract;
import com.starstudio.loser.phrapp.item.community.fragment.view.WriteFragment;

public class CommunityPresenter extends PHRPresenter<CommunityContract.CommunityView, CommunityContract.CommunityModel> implements CommunityContract.CommunityPresenter{
    private CommunityContract.CommunityView mView;
    private CommunityContract.CommunityModel mModel;
    private CommunityEventListener mListener = new CommunityEventListener() {
        @Override
        public View getView(int position) {
            View view = null;
            switch (position) {
                case 0:
                    view = mView == null ? null : new View(getActivity());
                    break;
                case 1:
                    view = mView == null ? null : new View(getActivity());
                    break;
                default:
            }
            return view;
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
    }

    @Override
    protected void onDetach() {

    }
}
