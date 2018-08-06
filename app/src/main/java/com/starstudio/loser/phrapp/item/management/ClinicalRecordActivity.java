package com.starstudio.loser.phrapp.item.management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.GetCallback;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.starstudio.loser.phrapp.R;

public class ClinicalRecordActivity extends AppCompatActivity {
    private static final String TAG = "ClinicalRecordActivity";
    private TextView name,hospital,date,content,money;
    private Toolbar toolbar;
    private FloatingActionButton delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_clinical_record);
        Log.d(TAG, "onCreate: "+getIntent().getExtras().getString("recordId"));
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.phr_clinical_record_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        name = (TextView) findViewById(R.id.clinical_record_name);
        hospital = (TextView) findViewById(R.id.clinical_record_hospital);
        date = (TextView) findViewById(R.id.clinical_record_date);
        money = (TextView) findViewById(R.id.clinical_record_money);
        content = (TextView) findViewById(R.id.clinical_record_content);

        AVQuery<AVObject> avQuery = new AVQuery<>("Record");
        avQuery.getInBackground(getIntent().getExtras().getString("recordId"), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                name.setText(AVUser.getCurrentUser().getUsername());
                hospital.setText(avObject.getString("hospitalName"));
                date.setText(avObject.getString("date"));
                money.setText(avObject.getString("money"));
                content.setText(avObject.getString("content"));
            }
        });

        delete = (FloatingActionButton) findViewById(R.id.clinical_record_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVObject record = AVObject.createWithoutData("Record", getIntent().getExtras().getString("recordId"));
                record.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(AVException e) {
                        Toast.makeText(ClinicalRecordActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}