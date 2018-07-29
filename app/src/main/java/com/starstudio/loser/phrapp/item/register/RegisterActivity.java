package com.starstudio.loser.phrapp.item.register;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.login.LoginActivity;
import com.starstudio.loser.phrapp.item.main.PHRMainActivity;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText username,phone,pwd,rpt_pwd,email;
    private CheckBox see_pwd;
    private Button sign_up;
    private RadioGroup sexGroup;
    private RadioButton boy,girl;
    private static String sex;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_register);
        initView();
        signUp();

    }

    private void initView(){
        username=(EditText) findViewById(R.id.phr_register_username);
        phone=(EditText) findViewById(R.id.phr_register_phone);
        pwd=(EditText) findViewById(R.id.phr_register_password);
        rpt_pwd=(EditText) findViewById(R.id.phr_register_repeat_password);
        email=(EditText)findViewById(R.id.phr_register_email);
        see_pwd=(CheckBox) findViewById(R.id.phr_register_see_password);
        sign_up=(Button) findViewById(R.id.phr_register_submit);
        sexGroup=(RadioGroup) findViewById(R.id.phr_register_sex);
        boy=(RadioButton) findViewById(R.id.phr_register_man);
        girl=(RadioButton) findViewById(R.id.phr_register_girl);
        toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.phr_register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        see_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //选择状态 显示明文--设置为可见的密码
                    pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    rpt_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    //默认状态显示密码--设置文本 要一起写才能起作用  InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    rpt_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                // 通过id实例化选中的这个RadioButton
                RadioButton choise = (RadioButton) findViewById(id);
                sex=choise.getText().toString();
                Toast.makeText(RegisterActivity.this,"性别"+sex,Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void signUp(){
        AVOSCloud.initialize(this,"NUjpdRi6jqP1S2iAfQCs7YNU-gzGzoHsz","27zlhvjRBd155W8iAWSoNJiO");
        AVOSCloud.setDebugLogEnabled(true);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = pwd.getText().toString();
                String rpt_password = rpt_pwd.getText().toString();
                String phoneNumber = phone.getText().toString();
                String name = username.getText().toString();
                String mail = email.getText().toString();

                Log.d(TAG, "onClick: "+password+"   "+phoneNumber+" "+name+"    "+mail+"    "+sex);
                if (password.equals("")||phoneNumber.equals("")||name.equals("")||mail.equals("")){
                    Toast toast = Toast.makeText(RegisterActivity.this, "请输入所有字段！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else {
                    if (!password.equals(rpt_password)) {
                        Toast toast = Toast.makeText(RegisterActivity.this, "请确认两次密码一致！", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {

                        AVUser user = new AVUser();// 新建 AVUser 对象实例
                        user.setUsername(name);// 设置用户名
                        user.setPassword(password);// 设置密码
                        user.setEmail(mail);
                        user.put("mobilePhoneNumber", phoneNumber);
                        user.put("sex", sex);
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Bundle b = new Bundle();
                                    b.putString("url", "");
                                    b.putString("name", username.getText().toString());
                                    b.putString("note", email.getText().toString());
                                    Intent intent = new Intent(RegisterActivity.this, PHRMainActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                } else {
                                    Log.d(TAG, "done: " + e.getCode());
                                    if (e.getCode()==0){
                                        Toast toast = Toast.makeText(RegisterActivity.this, "无法连接到服务器！", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }else if (e.getCode()==202||e.getCode()==214){
                                        Toast toast = Toast.makeText(RegisterActivity.this, "该账号已注册！", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }else if (e.getCode()==125){
                                        Toast toast = Toast.makeText(RegisterActivity.this, "请输入正确的邮箱格式！", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }else if (e.getCode()==127){
                                        Toast toast = Toast.makeText(RegisterActivity.this, "手机号无效！", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                            }
                        });
                    }
                }
            }

        });

    }
}
