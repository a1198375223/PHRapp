package com.starstudio.loser.phrapp.item.treatment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 11024 on 2018/8/2.
 */

public class DoctorListActivity extends AppCompatActivity {
    private String dept;
    private String hospName;
    private List<DoctorItem> doctorList=new ArrayList<>();
    private ListView listView;
    private DoctorListAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_doc_choice);

        toolbar =  findViewById(R.id.phr_treatment_doctor_choice_toolbar);
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
        listView=findViewById(R.id.doctor_list_view);

        dept=intent.getStringExtra("dept");
        hospName=intent.getStringExtra("hospName");
        //Toast.makeText(DoctorListActivity.this,"dept:"+dept+" hosp:"+hospName,Toast.LENGTH_SHORT).show();

        //根据hospName和hospDept在_User表查找所有医生，跳转
        final AVQuery<AVObject> queryDept = new AVQuery<>("_User");
        final AVQuery<AVObject> queryHosp = new AVQuery<>("_User");
        queryDept.whereEqualTo("dept",dept);
        queryHosp.whereEqualTo("hospName",hospName);
        AVQuery<AVObject> query=AVQuery.and(Arrays.asList(queryHosp,queryDept));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    for(AVObject avObject : list){
                        final String docName=avObject.getString("username");
                        final String imageId=avObject.getAVFile("head_img").getUrl();
                        final String title=avObject.getString("title");
                        final String profile=avObject.getString("doctorProfile");
                        try {
                            AVQuery<AVObject> query = new AVQuery<>("WorkTime");
                            query.whereEqualTo("doctorID",avObject.getObjectId());
                            query.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    if (avObject == null) {
                                        //Log.e("TAG", "done: true");
                                        initDoctors(docName, imageId, title, profile, null, dept, hospName);
                                    } else{
                                        JSONArray workTime = avObject.getJSONArray("workTime");
                                        //String workTime = avObject.get("workTime").toString();
                                        //Log.e("TAG", "done: " + avObject.getObjectId());
                                        //.makeText(DoctorListActivity.this, avObject.getObjectId(), Toast.LENGTH_SHORT).show();
                                        initDoctors(docName, imageId, title, profile, workTime, dept, hospName);
                                    }
                                    adapter=new DoctorListAdapter(DoctorListActivity.this,doctorList);
                                    listView.setAdapter(adapter);
                                    setListViewHeight(listView);
                                }
                            });
//                            AVObject object=avObject.getAVObject("workTime");
//                            if(object!=null){
//                                Toast.makeText(DoctorListActivity.this,object.getObjectId(),Toast.LENGTH_SHORT).show();
//                                JSONArray workTime = object.getJSONArray("workTime");
//                                boolean b =workTime==null;
//                                Log.e("TAG", "done: "+ b);
//                                Toast.makeText(DoctorListActivity.this,object.get("workTime").toString(),Toast.LENGTH_SHORT).show();
//                                initDoctors(docName, imageId, title, profile, workTime, dept, hospName);
//                            }else{
//                                initDoctors(docName, imageId, title, profile, null, dept, hospName);
//                            }
                        }catch (Exception e1){
                            e1.printStackTrace();
                            Log.e("test:",Log.getStackTraceString(e1));
                        }
                    }
                }else{
                    Toast.makeText(DoctorListActivity.this,"查找失败",Toast.LENGTH_SHORT).show();
                }
                //adapter=new DoctorListAdapter(DoctorListActivity.this,doctorList);
                //listView.setAdapter(adapter);
            }
        });
    }

    private DoctorItem initDoctors(String name,String imageId,String title,String profile, JSONArray workTime,String dept,String hosp){
        DoctorItem doctor=new DoctorItem(name,imageId,title,profile,workTime,dept,hosp);
        doctorList.add(doctor);
        return doctor;
    }

    //设置ListView的高度
    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}