package com.starstudio.loser.phrapp.item.treatment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11024 on 2018/8/3.
 */

public class DoctorPage extends AppCompatActivity{
    private ImageView doctorImage;
    private TextView doctorNameTv;
    private TextView doctorTitleTv;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] titles={"简介","评价","预约"};
    private FragmentAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private String hospName;
    private String deptName;
    private String docName;
    private String title;
    private String profile;
    private String imageUrl;
    private JSONArray workTime;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_doc_page);

        toolbar = findViewById(R.id.phr_treatment_doc_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent=getIntent();
        hospName=intent.getStringExtra("hosp");
        deptName=intent.getStringExtra("dept");
        docName=intent.getStringExtra("docName");
        title=intent.getStringExtra("title");
        profile=intent.getStringExtra("profile");
        imageUrl=intent.getStringExtra("imageUrl");
        initView();
    }

    private void initView() {
        tabLayout=findViewById(R.id.doctor_page_tab);
        viewPager=findViewById(R.id.doctor_page_content);
        doctorImage=findViewById(R.id.doctor_page_image);
        doctorNameTv=findViewById(R.id.doctor_page_name);
        doctorTitleTv=findViewById(R.id.doctor_page_title);

        doctorNameTv.setText(docName);
        doctorTitleTv.setText(title);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.waiting)
                .error(R.mipmap.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).load(imageUrl).apply(options).into(doctorImage);
        titleList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            titleList.add(titles[i]);
        }
        fragmentList = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            fragmentList.add(TabFragment.newInstance(i,hospName,deptName,docName,title,profile));// index  0: 简介; 1：评价; 2：预约
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
    }
}
