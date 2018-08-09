package com.starstudio.loser.phrapp.item.treatment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SendCallback;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by 11024 on 2018/8/3.
 */

public class AppointmentActivity extends AppCompatActivity{
    private TextView userNameTv;
    private TextView hospNameTv;
    private TextView deptNameTv;
    private TextView docNameTv;
    private TextView timeTv;
    private Button button;
    private String time;
    private Toolbar toolbar;
    private static final String TAG = "AppointmentActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_appointment);

        toolbar =  findViewById(R.id.phr_treatment_appointment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();

        Intent intent=getIntent();
        final String hospName=intent.getStringExtra("hospName");
        final String deptName=intent.getStringExtra("deptName");
        final String docName=intent.getStringExtra("docName");
        time=intent.getStringExtra("time").substring(0,7);
        //Toast.makeText(AppointmentActivity.this,hospName+","+deptName+","+docName+","+time,Toast.LENGTH_SHORT).show();

        final AVUser avUser = AVUser.getCurrentUser();
        if(avUser!=null){
            userNameTv.setText(avUser.getUsername());
        }else{
            //未登录
            Toast.makeText(this,"抱歉，您暂未登录，无法预约",Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(AppointmentActivity.this, LoginActivity.class);
            startActivity(intent1);
            finish();
        }

        hospNameTv.setText(hospName);
        deptNameTv.setText(deptName);
        docNameTv.setText(docName);
        timeTv.setText(time);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //提交预约单，变化预约数
                changeAppointment(hospName,deptName,docName,avUser);
            }
        });
    }

    private void sendmsg(String doctorID, final String userName) {
        AVQuery<AVObject> avQuery = new AVQuery<>("_User");
        avQuery.getInBackground(doctorID, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                AVQuery pushQuery = AVInstallation.getQuery();
// 假设 THE_INSTALLATION_ID 是保存在用户表里的 installationId，
// 可以在应用启动的时候获取并保存到用户表
                pushQuery.whereEqualTo("installationId", avObject.getString("installationID"));
                AVPush.sendMessageInBackground("您收到了一条来自"+ userName +"的预约！",  pushQuery, new SendCallback() {
                    @Override
                    public void done(AVException e) {

                    }
                });
            }
        });
    }

    private void changeAppointment(final String hospName, final String deptName, final String docName, final AVUser avUser) {
        AVQuery<AVObject> query1 = new AVQuery<>("_User");
        AVQuery<AVObject> query2 = new AVQuery<>("_User");
        AVQuery<AVObject> query3 = new AVQuery<>("_User");
        query1.whereEqualTo("hospName",hospName);
        query2.whereEqualTo("dept",deptName);
        query3.whereEqualTo("username",docName);
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(query1,query2,query3));
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e==null) {
                    try {
                        final String doctorID=avObject.getObjectId();
                        //判断该用户是否已经在同一时段预约这位医生
                        final String userID=avUser.getObjectId();
                        AVQuery<AVObject> query1 = new AVQuery<>("Appointment");
                        AVQuery<AVObject> query2 = new AVQuery<>("Appointment");
                        AVQuery<AVObject> query3 = new AVQuery<>("Appointment");
                        query1.whereEqualTo("doctorID",doctorID);
                        query2.whereEqualTo("userID",userID);
                        query3.whereEqualTo("appointTime",time);
                        AVQuery<AVObject> avQuery = AVQuery.and(Arrays.asList(query1,query2,query3));
                        avQuery.getFirstInBackground(new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                if((e==null)&&(avObject!=null)){//在同一时段，该用户已经预约过这位医生了。提示后退出
                                    Toast.makeText(AppointmentActivity.this,"您已在该时段预约过此医生",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    AVQuery<AVObject> query4 = new AVQuery<>("WorkTime");
                                    query4.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            try {
                                                JSONArray jsonArray = avObject.getJSONArray("workTime");
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                    if (jsonObject.getString("date").equals(time)) {
                                                        int reserved = Integer.valueOf(jsonObject.getString("reserved"));
                                                        int reminder = Integer.valueOf(jsonObject.getString("reminder"));
                                                        //Toast.makeText(AppointmentActivity.this, "reserved:" + reserved + ",reminder:" + reminder, Toast.LENGTH_SHORT).show();
                                                        if (reminder != 0) {
                                                            reserved++;
                                                            reminder--;
                                                            jsonObject.put("reserved", String.valueOf(reserved));
                                                            jsonObject.put("reminder", String.valueOf(reminder));
                                                            //Toast.makeText(AppointmentActivity.this, "reserved:" + jsonObject.getString("reserved"), Toast.LENGTH_SHORT).show();
                                                            avObject.put("workTime", jsonArray);
                                                            avObject.saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(AVException e2) {
                                                                    if (e2 == null) {
                                                                        //Toast.makeText(AppointmentActivity.this, "存储成功", Toast.LENGTH_SHORT).show();
                                                                        //Toast.makeText(AppointmentActivity.this, "预约成功", Toast.LENGTH_SHORT).show();
                                                                        AVObject record=new AVObject("Appointment");
                                                                        record.put("hospName",hospName);
                                                                        record.put("deptName",deptName);
                                                                        record.put("doctorName",docName);
                                                                        record.put("userID",avUser.getObjectId());
                                                                        record.put("doctorID",doctorID);
                                                                        record.put("appointTime",time);
                                                                        record.put("isTreat","false");
                                                                        record.saveInBackground();
                                                                        Intent intent=new Intent(AppointmentActivity.this,AppointSuccessActivity.class);
                                                                        intent.putExtra("hospName",hospName);
                                                                        intent.putExtra("deptName",deptName);
                                                                        intent.putExtra("docName",docName);
                                                                        intent.putExtra("date",time);
                                                                        sendmsg(doctorID,avUser.getUsername());
                                                                        startActivity(intent);
                                                                    } else {
                                                                        Toast.makeText(AppointmentActivity.this, "预约失败，请重试", Toast.LENGTH_SHORT).show();
                                                                        Log.e("test:", e2.getCode() + "  ");
                                                                        Log.e("test", Log.getStackTraceString(e2));
                                                                        finish();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            Toast.makeText(AppointmentActivity.this, "预约失败，该时段已预约满", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                    }
                                                }
                                            }catch (Exception e3){
                                                e3.printStackTrace();
                                            }
                                            //写入数据库 表 Appointment
                                        }
                                    });
                                }
                            }
                        });

                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                    /*Intent intent=new Intent(AppointmentActivity.this,DoctorListActivity.class);
                    intent.putExtra("dept",deptName);
                    intent.putExtra("hospName",hospName);
                    startActivity(intent);*/
                }else{
                    Toast.makeText(AppointmentActivity.this,"查找失败，请检查是否联网",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    private void initView() {
        userNameTv=findViewById(R.id.user_name_tv);
        hospNameTv=findViewById(R.id.hosp_name_tv);
        deptNameTv=findViewById(R.id.dept_name_tv);
        docNameTv=findViewById(R.id.doctor_name_tv);
        timeTv=findViewById(R.id.appointment_time_tv);
        button=findViewById(R.id.concert_appoint_button);
    }
}
