package com.starstudio.loser.phrapp.item.treatment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.starstudio.loser.phrapp.R;

import java.util.ArrayList;

/**
 * Created by 11024 on 2018/8/2.
 */

public class ChoiceDeptActivity extends AppCompatActivity {
    private ArrayList<String> deptData=new ArrayList<>();
    private ListView listView;
    private Bundle bundle;
    private String hospName;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_dept);

        toolbar =  findViewById(R.id.phr_treatment_dept_choice_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //获取TreatmentActivity传来的ArrayList数据
        bundle=this.getIntent().getExtras();
        deptData=bundle.getStringArrayList("deptList");
        hospName=bundle.getString("hospName");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(ChoiceDeptActivity.this,android.R.layout.simple_list_item_1,deptData);
        listView=findViewById(R.id.dept_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dept=deptData.get(position);
                Intent intent=new Intent(ChoiceDeptActivity.this,DoctorListActivity.class);
                intent.putExtra("dept",dept);
                intent.putExtra("hospName",hospName);
                startActivity(intent);
            }
        });
    }
}
