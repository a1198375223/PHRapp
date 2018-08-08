package com.starstudio.loser.phrapp.item.treatment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by 11024 on 2018/8/3.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    public List<Fragment> list;
    private List<String> titles;

    public FragmentAdapter(FragmentManager fm,List<Fragment> list,List<String> titles){
        super(fm);
        this.list=list;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    //返回每个Tab的标题
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
