package com.starstudio.loser.phrapp.item.treatment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.starstudio.loser.phrapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private boolean condition;
    private List<String> recordList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_record);
        initView();

        final AVUser avUser=AVUser.getCurrentUser();
        AVQuery<AVObject> query=new AVQuery<>("_User");
        query.whereEqualTo("objectId",avUser.getObjectId());
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e==null){
                    if(avObject.getBoolean("isDoctor")){//用户为医生
                        searchDoctorRecord(avUser.getObjectId());
                    }else{
                        searchUserRecord(avUser.getObjectId());
                    }
                }
            }
        });
    }

    private void searchUserRecord(String objectId) {
        AVQuery<AVObject> query=new AVQuery<>("Appointment");
        query.whereEqualTo("userID",objectId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for(int i=0;i<list.size();i++){
                    AVObject avObject=list.get(i);
                    String hosp=avObject.getString("hospName");
                    String dept=avObject.getString("deptName");
                    String doctor=avObject.getString("doctorName");
                    String date=avObject.getString("appointTime");
                    recordList.add(hosp+"    "+dept+"\n"+"医生："+doctor+"    "+date);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(RecordActivity.this,android.R.layout.simple_list_item_1,recordList);

                listView.setAdapter(adapter);
            }
        });
    }

    private void searchDoctorRecord(String objectId) {
        AVQuery<AVObject> query=new AVQuery<>("Appointment");
        query.whereEqualTo("doctorID",objectId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> list, AVException e) {
                for(int i=0;i<list.size();i++){
                    final int num = i;
                    AVObject avObject=list.get(i);
                    final String hosp=avObject.getString("hospName");
                    final String dept=avObject.getString("deptName");
                    String userID=avObject.getString("userID");
                    final String userName=userID.substring(userID.length()-5);
                    final String date=avObject.getString("appointTime");
                    AVQuery<AVUser> avUserAVQuery = new AVQuery<>("_User");
                    avUserAVQuery.getInBackground(userID, new GetCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            Log.d("############", "done: ++++++++++++++" + avUser.getUsername());
                            recordList.add(hosp+"    "+dept+"\n"+"用户："+avUser.getUsername()+"    "+date);
                            if (num==list.size()-1){
                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(RecordActivity.this,android.R.layout.simple_list_item_1,recordList);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        toolbar=findViewById(R.id.phr_treatment_record_toolbar);
        listView=findViewById(R.id.record_list_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
