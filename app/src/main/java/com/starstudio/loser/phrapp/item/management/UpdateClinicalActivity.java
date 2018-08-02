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

import java.util.regex.Pattern;

public class UpdateClinicalActivity extends AppCompatActivity {
    private static final String TAG = "UpdateClinicalActivity";
    private EditText date,hospital,content,money;
    private Button submit;
    private Toolbar toolbar;
    private String pattern = "^\\d\\d\\d\\d/\\d{1,2}/\\d{1,2}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_update_clinical);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.phr_update_clinical_record_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        date = (EditText) findViewById(R.id.phr_clinical_date);
        hospital = (EditText) findViewById(R.id.phr_clinical_hospital);
        content =(EditText) findViewById(R.id.phr_clinical_content);
        money = (EditText) findViewById(R.id.phr_clinical_money);
        submit = (Button) findViewById(R.id.phr_clinical_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (money.getText().toString().equals("")||date.getText().toString().equals("")||hospital.getText().toString().equals("")||content.getText().toString().equals("")){
                    Toast.makeText(UpdateClinicalActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                }else if (!Pattern.matches(pattern,date.getText().toString())){
                    Toast.makeText(UpdateClinicalActivity.this,"请输入正确的时间格式！",Toast.LENGTH_SHORT).show();
                } else{
                    AVObject clinicalRecord = new AVObject("Record");// 构建对象
                    clinicalRecord.put("date", date.getText());
                    clinicalRecord.put("hospitalName", hospital.getText());
                    clinicalRecord.put("content",content.getText());
                    clinicalRecord.put("money",money.getText());
                    clinicalRecord.put("owner", AVUser.getCurrentUser().getObjectId());
                    clinicalRecord.put("type","clinical");
                    clinicalRecord.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(UpdateClinicalActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });// 保存到服务端
                }
            }
        });
    }

}
