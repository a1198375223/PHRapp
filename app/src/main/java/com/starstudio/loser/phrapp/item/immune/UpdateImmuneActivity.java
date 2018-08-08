package com.starstudio.loser.phrapp.item.immune;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVDeleteOption;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;

public class UpdateImmuneActivity extends AppCompatActivity {
    private static final String TAG = "UpdateImmuneActivity";
    private EditText name,age,times,type,content;
    private Button submit;
    private Toolbar toolbar;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_update_immune);
        initFirst();
        bundle = getIntent().getExtras();
        if (bundle == null) {
            initView();
        }else{
            initView(bundle);
        }
    }

    private void initFirst(){
        name = (EditText) findViewById(R.id.phr_update_immune_name);
        age = (EditText) findViewById(R.id.phr_update_immune_age);
        times = (EditText) findViewById(R.id.phr_update_immune_times);
        type = (EditText) findViewById(R.id.phr_update_immune_type);
        content = (EditText) findViewById(R.id.phr_update_immune_content);

        toolbar = (Toolbar) findViewById(R.id.phr_update_immune_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit = (Button) findViewById(R.id.phr_update_immune_submit);
    }

    private void initView() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")||age.getText().toString().equals("")||times.getText().toString().equals("")||type.getText().equals("")){
                    Toast.makeText(UpdateImmuneActivity.this, "请输入完整信息！", Toast.LENGTH_SHORT).show();
                }else{
                    AVObject immune = new AVObject("Immune");
                    immune.put("name",name.getText());
                    immune.put("age",age.getText());
                    immune.put("times",Integer.valueOf(times.getText().toString()));
                    immune.put("type",type.getText());
                    immune.put("content",content.getText());
                    immune.put("doctorID", AVUser.getCurrentUser().getObjectId());
                    immune.put("doctor",AVUser.getCurrentUser());
                    immune.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null){
                                Toast.makeText(UpdateImmuneActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(UpdateImmuneActivity.this, "保存失败！错误代码" + e.getCode(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initView(final Bundle bundle) {
        toolbar.setTitle("更新");
        AVQuery<AVObject> avQuery = new AVQuery<>("Immune");
        avQuery.getInBackground(bundle.getString("id"), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                name.setText(avObject.getString("name"));
                age.setText(avObject.getString("age"));
                times.setText(String.valueOf(avObject.getNumber("times")));
                type.setText(avObject.getString("type"));
                content.setText(avObject.getString("content"));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVObject immune = AVObject.createWithoutData("Immune", bundle.getString("id"));
                immune.put("name", name.getText());
                immune.put("age", age.getText());
                immune.put("times", Integer.valueOf(times.getText().toString()));
                immune.put("type", type.getText());
                immune.put("content", content.getText());
                immune.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        Toast.makeText(UpdateImmuneActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (bundle != null) {
            getMenuInflater().inflate(R.menu.phr_immune_delete_view, menu);
            return true;
        }else{
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (bundle != null){
            switch (item.getItemId()){
                case R.id.phr_immune_delete:
                    AVObject avObject = AVObject.createWithoutData("Immune",getIntent().getExtras().getString("id"));
                    avObject.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(UpdateImmuneActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                default:
                    break;
            }
        }
        return true;
    }
}
