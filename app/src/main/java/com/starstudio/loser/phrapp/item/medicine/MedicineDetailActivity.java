package com.starstudio.loser.phrapp.item.medicine;

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
import com.starstudio.loser.phrapp.item.immune.ImmuneDetailActivity;

public class MedicineDetailActivity extends AppCompatActivity {
    private static final String TAG = "MedicineDetailActivity";
    private TextView name,money,factory,content;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_medicine_detail);
        initView(getIntent().getExtras());
    }

    private void initView(Bundle bundle) {
        name = (TextView) findViewById(R.id.phr_detail_medicine_name);
        money = (TextView) findViewById(R.id.phr_detail_medicine_money);
        factory = (TextView) findViewById(R.id.phr_detail_medicine_factory);
        content = (TextView) findViewById(R.id.phr_detail_medicine_content);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        toolbar = (Toolbar) findViewById(R.id.phr_detail_medicine_toolbar);
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
        AVQuery<AVObject> avQuery = new AVQuery<>("Medicine");
        avQuery.getInBackground(id, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null){
                    name.setText(avObject.getString("name"));
                    money.setText(avObject.getString("money"));
                    factory.setText(avObject.getString("factory"));
                    content.setText(avObject.getString("content"));
                }else{
                    Toast.makeText(MedicineDetailActivity.this,"获取数据出错",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "done: " + e.getCode());
                }
            }
        });
    }
}
