package com.starstudio.loser.phrapp.item.management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.*;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.R;

public class UpdatePhysicalActivity extends AppCompatActivity {
    private static final String TAG = "UpdatePhysicalActivity";
    private EditText date,hospital,content;
    private Button submit;
    private Toolbar toolbar;
    private String pattern = "^\\d\\d\\d\\d/\\d{1,2}/\\d{1,2}";
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_update_physical);
        initFirst();
        bundle = getIntent().getExtras();
        if (bundle==null) {
            initView();
        }else{
            initView(bundle);
        }
    }

    private void initFirst() {
        toolbar = (Toolbar) findViewById(R.id.phr_update_physical_record_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        date = (EditText) findViewById(R.id.phr_physical_date);
        hospital = (EditText) findViewById(R.id.phr_physical_hospital);
        content =(EditText) findViewById(R.id.phr_physical_content);
        submit = (Button) findViewById(R.id.phr_physical_submit);
    }

    private void initView() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date.getText().toString().equals("")||hospital.getText().toString().equals("")||content.getText().toString().equals("")){
                    Toast.makeText(UpdatePhysicalActivity.this,"请输入完整体检信息！",Toast.LENGTH_SHORT).show();
                }else if (!Pattern.matches(pattern,date.getText().toString())){
                    Toast.makeText(UpdatePhysicalActivity.this,"请输入正确的时间格式！",Toast.LENGTH_SHORT).show();
                }else{
                    AVObject physicalRecord = new AVObject("Record");// 构建对象
                    physicalRecord.put("date", date.getText());
                    physicalRecord.put("hospitalName", hospital.getText());
                    physicalRecord.put("content",content.getText());
                    physicalRecord.put("owner", AVUser.getCurrentUser().getObjectId());
                    physicalRecord.put("type","physical");
                    physicalRecord.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(UpdatePhysicalActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });// 保存到服务端
                }
            }
        });
    }

    private void initView(final Bundle bundle) {
        toolbar.setTitle("体检记录");
        AVQuery<AVObject> avQuery = new AVQuery<>("Record");
        avQuery.getInBackground(bundle.getString("recordId"), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                date.setText(avObject.getString("date"));
                hospital.setText(avObject.getString("hospitalName"));
                content.setText(avObject.getString("content"));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVObject clinicalRecord = AVObject.createWithoutData("Record",bundle.getString("recordId"));
                clinicalRecord.put("date", date.getText());
                clinicalRecord.put("hospitalName", hospital.getText());
                clinicalRecord.put("content",content.getText());
                clinicalRecord.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(UpdatePhysicalActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(UpdatePhysicalActivity.this, "修改失败！错误代码" + e.getCode(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });// 保存到服务端
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (bundle == null ) {
            return super.onCreateOptionsMenu(menu);
        }else{
            getMenuInflater().inflate(R.menu.phr_immune_delete_view, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (bundle != null){
            switch (item.getItemId()){
                case R.id.phr_immune_delete:
                    AVObject avObject = AVObject.createWithoutData("Record",bundle.getString("recordId"));
                    avObject.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(UpdatePhysicalActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
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
