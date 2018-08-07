package com.starstudio.loser.phrapp.item.treatment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.starstudio.loser.phrapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11024 on 2018/8/3.
 */

public class DoctorPage extends AppCompatActivity{
    private ImageView doctorImage;
    private TextView doctorName;
    private TextView doctorTitle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] titles={"简介","评价","预约"};
    private FragmentAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> titleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_doc_page);
        initView();
    }

    private void initView() {
        tabLayout=findViewById(R.id.doctor_page_tab);
        viewPager=findViewById(R.id.doctor_page_content);
        titleList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            titleList.add(titles[i]);
        }
        fragmentList = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            fragmentList.add(TabFragment.newInstance(i));
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phr_community_post_message_menu,menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tab_add:
                if (mTitles.size() == titles.length) {
                    return true;
                }
                mTitles.add(titles[mTitles.size()]);
                mFragments.add(TabFragment.newInstance(mTitles.size() - 1));
                adapter.notifyDataSetChanged();
                mTabLayout.setupWithViewPager(mViewPager);
                return true;
            case R.id.tab_change:
                //设置TabLayout的模式，系统默认模式:MODE_FIXED
                if (mTabLayout.getTabMode() == TabLayout.MODE_FIXED) {
                    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                } else {
                    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
