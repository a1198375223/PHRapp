package com.starstudio.loser.phrapp.item.medicine;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.R;



import java.util.ArrayList;
import java.util.List;

public class MedicineMainActivity extends AppCompatActivity {
    private static final String TAG = "DoctorImmuneActivity";
    private FloatingActionButton add;
    private Toolbar toolbar;
    private List<String> doctorIDs = new ArrayList<>();
    private List<Medicine> medicineList = new ArrayList<>();
    private MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_medicine_main);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.medicine_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.phr_medicine_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("药品信息");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.phr_black));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.phr_black));

        Log.d(TAG, "initView: " + AVUser.getCurrentUser().getBoolean("isDoctor"));
        if (AVUser.getCurrentUser().getBoolean("isDoctor")) {
            add = (FloatingActionButton) findViewById(R.id.phr_add_medicine);
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MedicineMainActivity.this, UpdateMedicineActivity.class));
                }
            });
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.phr_medicine_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MedicineAdapter(medicineList,AVUser.getCurrentUser().getBoolean("isDoctor"));
        recyclerView.setAdapter(adapter);
    }

    private void initImmune(){
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
            AVQuery<AVObject> query = new AVQuery<>("Medicine");
            query.whereEqualTo("doctorID", doctorID);
            query.include("doctor");
            query.addDescendingOrder("updatedAt");
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        medicineList.clear();
                        for (AVObject i : list) {
                            //Log.d(TAG, "done: " + dateString);
                            medicineList.add(new Medicine(i));
                        }
                        if (medicineList.size() == 0){
                            Toast.makeText(MedicineMainActivity.this, "还未有相关信息发布！", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MedicineMainActivity.this, "拉取数据失败", Toast.LENGTH_SHORT).show();
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
