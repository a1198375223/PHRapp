package com.starstudio.loser.phrapp.item.treatment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 11024 on 2018/8/3.
 */

public class AppointmentActivity extends Activity{
    private TextView userNameTv;
    private TextView hospNameTv;
    private TextView deptNameTv;
    private TextView docNameTv;
    private TextView timeTv;
    private Button button;
    private String userName;
    private String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_appointment);
        initView();

        Intent intent=getIntent();
        final String hospName=intent.getStringExtra("hospName");
        final String deptName=intent.getStringExtra("deptName");
        final String docName=intent.getStringExtra("docName");
        time=intent.getStringExtra("time").substring(0,7);
        //Toast.makeText(AppointmentActivity.this,hospName+","+deptName+","+docName+","+time,Toast.LENGTH_SHORT).show();

        userNameTv.setText("");
        hospNameTv.setText(hospName);
        deptNameTv.setText(deptName);
        docNameTv.setText(docName);
        timeTv.setText(time);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //提交预约单，变化预约数
                changeAppointment(hospName,deptName,docName,userName);
            }
        });
    }

    private void changeAppointment(final String hospName, final String deptName, String docName, String userName) {
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
                    // object 就是符合条件的第一个 AVObject
                    //Toast.makeText(AppointmentActivity.this,"查找成功",Toast.LENGTH_SHORT).show();
                    try {
                        AVQuery<AVObject> query = new AVQuery<>("WorkTime");
                        query.whereEqualTo("doctorID",avObject.getObjectId());
                        query.getFirstInBackground(new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                try {
                                    JSONArray jsonArray = avObject.getJSONArray("workTime");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        if (jsonObject.getString("date").equals(time)) {
                                            int reserved = Integer.valueOf(jsonObject.getString("reserved"));
                                            int reminder = Integer.valueOf(jsonObject.getString("reminder"));
                                            Toast.makeText(AppointmentActivity.this, "reserved:" + reserved + ",reminder:" + reminder, Toast.LENGTH_SHORT).show();
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
                                                            Toast.makeText(AppointmentActivity.this, "预约成功", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(AppointmentActivity.this, "存储失败", Toast.LENGTH_SHORT).show();
                                                            Log.e("test:", e2.getCode() + "  ");
                                                            Log.e("test", Log.getStackTraceString(e2));
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(AppointmentActivity.this, "预约失败，该时段已预约满", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }catch (Exception e3){
                                    e3.printStackTrace();
                                }
                            }
                        });
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                    Intent intent=new Intent(AppointmentActivity.this,DoctorListActivity.class);
                    intent.putExtra("dept",deptName);
                    intent.putExtra("hospName",hospName);
                    startActivity(intent);
                }else{
                    Toast.makeText(AppointmentActivity.this,"查找失败",Toast.LENGTH_SHORT).show();
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
