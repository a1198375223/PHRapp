package com.starstudio.loser.phrapp.item.management;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.R;

import java.util.ArrayList;
import java.util.List;

public class ManageMainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ManageMainActivity";
    private RelativeLayout list_physical,list_clinical,update_physical,update_clinical;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_manage_main);
        initData();
        initView();
    }

    private  void initData(){
        tabs.add("体检记录管理");
        tabs.add("临床记录管理");
        fragments.add(ManageFragment.newInstance("physical"));
        fragments.add(ManageFragment.newInstance("clinical"));
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tayLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(new ManageAdapter(getSupportFragmentManager()));
        //关联ViewPager和TabLayout
        tabLayout.setupWithViewPager(viewPager);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.phr_manage_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton update_physical = (FloatingActionButton) findViewById(R.id.phr_update_physical_record);
        update_physical.setOnClickListener(this);
        FloatingActionButton update_clinical = (FloatingActionButton) findViewById(R.id.phr_update_clinical_record);
        update_clinical.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.phr_update_clinical_record:
                startActivity(new Intent(ManageMainActivity.this,UpdateClinicalActivity.class));
                break;
            case R.id.phr_update_physical_record:
                startActivity(new Intent(ManageMainActivity.this,UpdatePhysicalActivity.class));
                break;
            default:
                break;
        }
    }


    class ManageAdapter extends FragmentPagerAdapter {
        public ManageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //显示标签上的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }

}
