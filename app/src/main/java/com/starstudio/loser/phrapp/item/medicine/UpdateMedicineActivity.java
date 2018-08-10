package com.starstudio.loser.phrapp.item.medicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.immune.UpdateImmuneActivity;

public class UpdateMedicineActivity extends AppCompatActivity {

    private EditText name,money,factory,content;
    private Button submit;
    private Toolbar toolbar;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_update_medicine);
        initFirst();
        bundle = getIntent().getExtras();
        if (bundle == null) {
            initView();
        }else{
            initView(bundle);
        }
    }

    private void initFirst() {
        name = (EditText) findViewById(R.id.phr_update_medicine_name);
        money = (EditText) findViewById(R.id.phr_update_medicine_money);
        content = (EditText) findViewById(R.id.phr_update_medicine_content);
        factory = (EditText) findViewById(R.id.phr_update_medicine_factory);

        toolbar = (Toolbar) findViewById(R.id.phr_update_medicine_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit = (Button) findViewById(R.id.phr_update_medicine_submit);
    }

    private void initView() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")||money.getText().toString().equals("")||factory.getText().toString().equals("")||content.getText().toString().equals("")){
                    Toast.makeText(UpdateMedicineActivity.this, "请输入完整信息！", Toast.LENGTH_SHORT).show();
                }else{
                    AVObject medicine = new AVObject("Medicine");
                    medicine.put("name",name.getText());
                    medicine.put("money",money.getText());
                    medicine.put("factory",factory.getText());
                    medicine.put("content",content.getText());
                    medicine.put("doctorID", AVUser.getCurrentUser().getObjectId());
                    medicine.put("doctor",AVUser.getCurrentUser());
                    medicine.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null){
                                Toast.makeText(UpdateMedicineActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(UpdateMedicineActivity.this, "保存失败！错误代码" + e.getCode(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void initView(final Bundle bundle) {
        toolbar.setTitle("更新");
        AVQuery<AVObject> avQuery = new AVQuery<>("Medicine");
        avQuery.getInBackground(bundle.getString("id"), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                name.setText(avObject.getString("name"));
                money.setText(avObject.getString("money"));
                factory.setText(avObject.getString("factory"));
                content.setText(avObject.getString("content"));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVObject medicine = AVObject.createWithoutData("Medicine",bundle.getString("id"));
                medicine.put("name",name.getText());
                medicine.put("money",money.getText());
                medicine.put("factory",factory.getText());
                medicine.put("content",content.getText());
                medicine.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null){
                            Toast.makeText(UpdateMedicineActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(UpdateMedicineActivity.this, "修改失败！错误代码" + e.getCode(), Toast.LENGTH_SHORT).show();
                        }
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
                    AVObject avObject = AVObject.createWithoutData("Medicine",getIntent().getExtras().getString("id"));
                    avObject.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(UpdateMedicineActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
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
