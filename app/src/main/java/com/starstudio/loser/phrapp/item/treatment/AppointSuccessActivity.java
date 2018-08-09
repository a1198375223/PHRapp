package com.starstudio.loser.phrapp.item.treatment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.main.PHRMainActivity;

public class AppointSuccessActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String hospName;
    private String deptName;
    private String docName;
    private String date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_appoint_result);

        final Intent intent=getIntent();
        hospName=intent.getStringExtra("hospName");
        deptName=intent.getStringExtra("deptName");
        docName=intent.getStringExtra("docName");
        date=intent.getStringExtra("date");

        TextView hospTv=findViewById(R.id.phr_result_hosp_name_tv);
        TextView deptTv=findViewById(R.id.phr_result_dept_name_tv);
        TextView docTv=findViewById(R.id.phr_result_appoint_doc_name_tv);
        TextView timeTv=findViewById(R.id.phr_result_appoint_time_tv);
        Button button=findViewById(R.id.phr_treatment_back_to_main_page);
        toolbar=findViewById(R.id.phr_treatemnt_appoint_result_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        hospTv.setText(hospName);
        deptTv.setText(deptName);
        docTv.setText(docName);
        timeTv.setText(date);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(AppointSuccessActivity.this,PHRMainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
