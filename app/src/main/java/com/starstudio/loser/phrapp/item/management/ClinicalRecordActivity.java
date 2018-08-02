package com.starstudio.loser.phrapp.item.management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.starstudio.loser.phrapp.R;

public class ClinicalRecordActivity extends AppCompatActivity {

    private TextView name,hospital,date,content,money;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_clinical_record);
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
                money.setText(avObject.getNumber("money").toString());
                content.setText(avObject.getString("content"));
            }
        });
    }
}
