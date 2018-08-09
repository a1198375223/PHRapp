package com.starstudio.loser.phrapp.item.immune;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SendCallback;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.login.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DoctorImmuneActivity extends AppCompatActivity{
    private static final String TAG = "DoctorImmuneActivity";
    private FloatingActionButton add;
    private Toolbar toolbar;
    private List<Immune> immuneList = new ArrayList<>();
    private ImmuneAdapter adapter;
    private List<String> doctorIDs = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_doctor_immune);
        initView();
    }


    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.immune_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.phr_immune_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("免疫知识");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.phr_white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.phr_white));

        Log.d(TAG, "initView: " + AVUser.getCurrentUser().getBoolean("isDoctor"));
        if (AVUser.getCurrentUser().getBoolean("isDoctor")) {
            add = (FloatingActionButton) findViewById(R.id.phr_add_immune);
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(DoctorImmuneActivity.this, UpdateImmuneActivity.class));
                }
            });
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.phr_immune_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ImmuneAdapter(immuneList,AVUser.getCurrentUser().getBoolean("isDoctor"));
        recyclerView.setAdapter(adapter);
        swipeRefresh=(SwipeRefreshLayout) findViewById(R.id.immune_swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        });
    }

    private void refreshNews() {
        initImmune();
        try{
            Thread.sleep(1500);
            swipeRefresh.setRefreshing(false);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void initImmune(){
        //String doctorID = null;
        //final String[] doctorIDs;
        Log.d(TAG, "initImmune: "+ AVUser.getCurrentUser().getBoolean("isDoctor"));
        if (AVUser.getCurrentUser().getBoolean("isDoctor")){
            doctorIDs.add(AVUser.getCurrentUser().getObjectId());
            addImmune(doctorIDs);
        }else {
            AVQuery<AVObject> avQuery = new AVQuery<>("Appointment");
            avQuery.whereEqualTo("userID", AVUser.getCurrentUser().getObjectId());
            avQuery.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        for (AVObject i : list) {
                            Log.d(TAG, "done: +++++++++++++++++++++++++++++++");
                            doctorIDs.add(i.getString("doctorID"));
                        }
                        addImmune(doctorIDs);
                    }else{
                        Log.d(TAG, "done: " + e.getCode());
                    }
                }
            });
        }
    }

    private void addImmune(List<String> doctorIDs){
        for (String doctorID : doctorIDs) {
            AVQuery<AVObject> query = new AVQuery<>("Immune");
            query.whereEqualTo("doctorID", doctorID);
            query.include("doctor");
            query.addDescendingOrder("updatedAt");
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        immuneList.clear();
                        for (AVObject i : list) {
                            //Log.d(TAG, "done: " + dateString);
                            immuneList.add(new Immune(i));
                        }
                        if (immuneList.size()==0){
                            //Toast.makeText(DoctorImmuneActivity.this, "还未有相关信息发布！", Toast.LENGTH_SHORT).show();
                            Toasty.normal(DoctorImmuneActivity.this, "还未有相关信息发布！", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        //Toast.makeText(DoctorImmuneActivity.this, "拉取数据失败", Toast.LENGTH_SHORT).show();
                        Toasty.error(DoctorImmuneActivity.this, "拉取数据失败", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "done: " + e.getCode());
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        initImmune();
        super.onResume();
    }
}
