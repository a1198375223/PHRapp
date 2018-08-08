package com.starstudio.loser.phrapp.item.message.presenter;

/*
    create by:loser
    date:2018/7/23 22:54
*/

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.message.contract.MessageContract;
import com.starstudio.loser.phrapp.item.message.list.view.EconomicFragment;
import com.starstudio.loser.phrapp.item.message.list.view.EntertainmentFragment;
import com.starstudio.loser.phrapp.item.message.list.view.FashionFragment;
import com.starstudio.loser.phrapp.item.message.list.view.HealthFragment;
import com.starstudio.loser.phrapp.item.message.list.view.IslandFragment;
import com.starstudio.loser.phrapp.item.message.list.view.MentalFragment;
import com.starstudio.loser.phrapp.item.message.list.view.MilitaryFragment;
import com.starstudio.loser.phrapp.item.message.list.view.ScienceFragment;
import com.starstudio.loser.phrapp.item.message.list.view.SocialFragment;
import com.starstudio.loser.phrapp.item.message.list.view.SportsFragment;
import com.starstudio.loser.phrapp.item.message.list.view.TopFragment;

import static com.avos.avoscloud.AVAnalytics.TAG;

public class MessagePresenterImpl extends PHRPresenter<MessageContract.MessageView, MessageContract.MessageModel> implements MessageContract.MessagePresenter {
    private MessageContract.MessageView mMessageView;
    private MessageEventListener mListener = new MessageEventListener() {
        @Override
        public Fragment getFragment(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = mMessageView == null ? null : new HealthFragment();
                    break;
                case 1:
                    fragment = mMessageView == null ? null : new SportsFragment();

                    break;
                case 2:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new MentalFragment();
                    break;
                case 3:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new TopFragment();
                    break;
                case 4:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new SocialFragment();
                    break;
                case 5:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new IslandFragment();
                    break;
                case 6:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new EntertainmentFragment();
                    break;
                case 7:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new MilitaryFragment();
                    break;
                case 8:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new EconomicFragment();
                    break;
                case 9:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new FashionFragment();
                    break;
                case 10:
//                    fragment = mMessageView == null ? null : new Fragment();
                    fragment = mMessageView == null ? null : new ScienceFragment();
                    break;
                default:
            }
            return fragment;
        }
    };

    public MessagePresenterImpl(Activity activity) {
        super(activity);

    }

    @Override
    protected void onAttach() {
         mMessageView = getView();

         mMessageView.setEventListener(mListener);
    }

    @Override
    protected void onDetach() {

    }


}
