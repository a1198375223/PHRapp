package com.starstudio.loser.phrapp.item.community.view;

/*
    create by:loser
    date:2018/7/30 15:47
*/

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.item.community.contract.CommunityContract;
import com.starstudio.loser.phrapp.item.community.presenter.CommunityEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.avos.avoscloud.AVAnalytics.TAG;


public class CommunityView extends PHRView<CommunityEventListener> implements CommunityContract.CommunityView {
    private List<String> mList;
    private MyAdapter mAdapter;
    private ViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CommunityView(Activity activity) {
        super(activity);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.phr_community_toolbar);
        ((PHRActivity) getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((PHRActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        mViewPager = (ViewPager) activity.findViewById(R.id.phr_community_viewPager);
        TabLayout tabLayout = (TabLayout) activity.findViewById(R.id.phr_community_tab_layout);
        FloatingActionButton actionButton = (FloatingActionButton) activity.findViewById(R.id.phr_community_floating_button);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessToast("show");
                getListener().startWriteFragment();
            }
        });

        mList = new ArrayList<>();
        mList.add("动  态");
        mList.add("自  己");

        for (String title : mList) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        mAdapter = new MyAdapter();
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }



        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = null;
            if (getListener() != null) {
                view = getListener().getView(position);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position);
        }



    }

}
