package com.starstudio.loser.phrapp.item.treatment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 11024 on 2018/8/2.
 */

public class DoctorListActivity extends Activity{
    private String dept;
    private String hospName;
    private List<DoctorItem> doctorList=new ArrayList<>();
    private ListView listView;
    private DoctorListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_doc_choice);

        Intent intent=getIntent();
        listView=findViewById(R.id.doctor_list_view);

        dept=intent.getStringExtra("dept");
        hospName=intent.getStringExtra("hospName");
        //Toast.makeText(DoctorListActivity.this,"dept:"+dept+" hosp:"+hospName,Toast.LENGTH_SHORT).show();

        //根据hospName和hospDept在_User表查找所有医生，跳转
        AVOSCloud.initialize(DoctorListActivity.this,"NUjpdRi6jqP1S2iAfQCs7YNU-gzGzoHsz","27zlhvjRBd155W8iAWSoNJiO");
        AVOSCloud.setDebugLogEnabled(true);
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
                        String docName=avObject.getString("username");
                        String imageId=avObject.getAVFile("head_img").getUrl();
                        String title=avObject.getString("title");
                        String profile=avObject.getString("doctorProfile");
                        JSONArray workTime=avObject.getJSONArray("workTime");
                        initDoctors(docName,imageId,title,profile,workTime,dept,hospName);
                    }
                }else{
                    Toast.makeText(DoctorListActivity.this,"查找失败",Toast.LENGTH_SHORT).show();
                }
                adapter=new DoctorListAdapter(DoctorListActivity.this,doctorList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }
        });
            }

    private DoctorItem initDoctors(String name,String imageId,String title,String profile, JSONArray workTime,String dept,String hosp){
        DoctorItem doctor=new DoctorItem(name,imageId,title,profile,workTime,dept,hosp);
        doctorList.add(doctor);
        return doctor;
    }
}