package com.starstudio.loser.phrapp.item.immune;

import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.starstudio.loser.phrapp.R;

public class ImmuneDetailActivity extends AppCompatActivity {
    private static final String TAG = "ImmuneDetailActivity";
    private TextView name,age,type,times,content;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_immune_detail);
        Bundle bundle = getIntent().getExtras();
        initView(bundle);
    }

    private void initView(Bundle bundle) {
        name = (TextView) findViewById(R.id.phr_detail_immune_name);
        age = (TextView) findViewById(R.id.phr_detail_immune_age);
        type = (TextView) findViewById(R.id.phr_detail_immune_type);
        times = (TextView) findViewById(R.id.phr_detail_immune_times);
        content = (TextView) findViewById(R.id.phr_detail_immune_content);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        toolbar = (Toolbar) findViewById(R.id.phr_detail_immune_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String id = bundle.getString("id");
        AVQuery<AVObject> avQuery = new AVQuery<>("Immune");
        avQuery.getInBackground(id, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null){
                    name.setText(avObject.getString("name"));
                    age.setText(avObject.getString("age"));
                    type.setText(avObject.getString("type"));
                    times.setText(avObject.getNumber("times").toString());
                    content.setText(avObject.getString("content"));
                }else{
                    Toast.makeText(ImmuneDetailActivity.this,"获取数据出错",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "done: " + e.getCode());
                }
            }
        });
    }
}
