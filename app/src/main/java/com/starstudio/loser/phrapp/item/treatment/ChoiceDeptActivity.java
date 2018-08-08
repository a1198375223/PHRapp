package com.starstudio.loser.phrapp.item.treatment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 11024 on 2018/8/2.
 */

public class ChoiceDeptActivity extends Activity{
    private ArrayList<String> deptData=new ArrayList<>();
    private ListView listView;
    private Bundle bundle;
    private String hospName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_treatment_dept);

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
