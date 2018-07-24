package com.starstudio.loser.phrapp.item.message.view;

/*
    create by:loser
    date:2018/7/23 22:52
*/

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.item.message.contract.MessageContract;
import com.starstudio.loser.phrapp.item.message.presenter.MessagePresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class MessageViewImpl extends PHRView implements MessageContract.MessageView {
    private View mRootView;
    private List<String> mTitle;
    private MessageContract.MessagePresenter mPresenter;

    public MessageViewImpl(Activity activity) {
        super(activity);
        mRootView = LayoutInflater.from(activity).inflate(R.layout.phr_activity_message, null);
        mPresenter = new MessagePresenterImpl(activity);

        ViewPager viewPager = (ViewPager) activity.findViewById(R.id.phr_message_view_pager);
        TabLayout tabLayout = (TabLayout) activity.findViewById(R.id.phr_message_tab_layout);

        mTitle = new ArrayList<>();
        mTitle.add("健康");
        mTitle.add("心理");
        mTitle.add("头条");
        mTitle.add("体育");
        mTitle.add("社会");
        mTitle.add("国内");
        mTitle.add("娱乐");
        mTitle.add("军事");
        mTitle.add("财经");
        mTitle.add("时尚");
        mTitle.add("科技");

        for (int i=0; i<mTitle.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mTitle.get(i)));
        }

        viewPager.setAdapter(new MyAdapter(((PHRActivity)activity).getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    @Override
    public View getView() {
        return mRootView;
    }


    private class MyAdapter extends FragmentPagerAdapter {

        protected MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mPresenter.getFragment(position);
        }

        @Override
        public int getCount() {
            return mTitle.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }
    }

}
