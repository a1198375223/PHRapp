package com.starstudio.loser.phrapp.item.management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.R;

public class UpdatePhysicalActivity extends AppCompatActivity {
    private static final String TAG = "UpdatePhysicalActivity";
    private EditText date,hospital,content;
    private Button submit;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_update_physical);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.phr_update_physical_record_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        date = (EditText) findViewById(R.id.phr_physical_date);
        hospital = (EditText) findViewById(R.id.phr_physical_hospital);
        content =(EditText) findViewById(R.id.phr_physical_content);
        submit = (Button) findViewById(R.id.phr_physical_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date.getText().toString().equals("")||hospital.getText().toString().equals("")||content.getText().toString().equals("")){
                    Toast.makeText(UpdatePhysicalActivity.this,"请输入完整体检信息！",Toast.LENGTH_SHORT).show();
                }else{
                    AVObject physicalRecord = new AVObject("Record");// 构建对象
                    physicalRecord.put("date", date.getText());
                    physicalRecord.put("hospitalName", hospital.getText());
                    physicalRecord.put("content",content.getText());
                    physicalRecord.put("owner", AVUser.getCurrentUser().getObjectId());
                    physicalRecord.put("type","physical");
                    physicalRecord.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(UpdatePhysicalActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });// 保存到服务端
                }
            }
        });
    }
}
