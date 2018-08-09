package com.starstudio.loser.phrapp.item.treatment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TreatmentActiity  extends AppCompatActivity {
    private GridView gridView;
    private List<HospitalItem> imageUrls;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_treatment);

        toolbar =  findViewById(R.id.phr_treatment_hosp_choice_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gridView=findViewById(R.id.hosp_grid_view);
        initData();
    }

    private void initData() {
        AVOSCloud.initialize(this,"NUjpdRi6jqP1S2iAfQCs7YNU-gzGzoHsz","27zlhvjRBd155W8iAWSoNJiO");
        AVOSCloud.setDebugLogEnabled(true);

        imageUrls=new ArrayList<>();
        AVQuery query = AVQuery.getQuery("Hospital");
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {//查找成功
                    //Toast.makeText(TreatmentActiity.this, "查找成功", Toast.LENGTH_SHORT).show();

                    for (AVObject avObject : list){
                        //测试：输出医院名
                        //Toast.makeText(TreatmentActiity.this, avObject.getString("hospName"), Toast.LENGTH_SHORT).show();

                        String hospName = avObject.getString("hospName");
                        AVFile hospPic = avObject.getAVFile("hospPic");
                        HospitalItem data=new HospitalItem(hospName,""+hospPic.getUrl());

                        //测试：输出图片URL
                        //Toast.makeText(TreatmentActiity.this,hospPic.getUrl(),Toast.LENGTH_SHORT).show();

                        imageUrls.add(data);

                    }

                    //测试：输出List大小
                    //Toast.makeText(TreatmentActiity.this, "size :"+imageUrls.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TreatmentActiity.this, "查找失败", Toast.LENGTH_SHORT).show();
                }
                gridView.setAdapter(new ImageListAdapter(TreatmentActiity.this,imageUrls));
                //设置点击事件
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final String name=imageUrls.get(position).getHospName();
                        AVQuery query1=AVQuery.getQuery("Hospital");
                        query1.whereEqualTo("hospName",name);
                        query1.findInBackground(new FindCallback<AVObject>(){
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                if(e==null){
                                    Bundle bundle = new Bundle();
                                    ArrayList<String> deptList = new ArrayList<>();
                                    //Toast.makeText(TreatmentActiity.this,"点击："+name,Toast.LENGTH_SHORT).show();
                                    try {
                                        for(AVObject avObject1 : list) {
                                            JSONArray jsonArray = avObject1.getJSONArray("hospDept");

                                            //Toast.makeText(TreatmentActiity.this, "jsonArray length: " + jsonArray.length(), Toast.LENGTH_SHORT).show();

                                            for (int i = 0; i < jsonArray.length(); i++) {

                                                //JSONObject jsonObject = new JSONObject();
                                                //jsonObject = jsonArray.getJSONObject(i);
                                                deptList.add(jsonArray.getString(i));
                                                //Toast.makeText(TreatmentActiity.this, i + ": " + jsonArray.getString(i), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }catch (Exception e1){
                                        e1.printStackTrace();
                                    }
                                    bundle.putStringArrayList("deptList", deptList);
                                    bundle.putString("hospName",name);
                                    Intent intent = new Intent(TreatmentActiity.this, ChoiceDeptActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
