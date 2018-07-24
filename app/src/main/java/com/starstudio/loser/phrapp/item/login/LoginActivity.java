package com.starstudio.loser.phrapp.item.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;

import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.main.PHRMainActivity;



public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText phone,password;
    private Button login_btn;
    private TextView sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_login);
        initLogin();

    }

    private void initLogin(){
        phone=(EditText) findViewById(R.id.phr_login_username);
        password=(EditText) findViewById(R.id.phr_login_password);
        login_btn=(Button) findViewById(R.id.phr_login_login);
        sign_in=(TextView) findViewById(R.id.tv_register);
        AVOSCloud.initialize(this,"NUjpdRi6jqP1S2iAfQCs7YNU-gzGzoHsz","27zlhvjRBd155W8iAWSoNJiO");
        AVOSCloud.setDebugLogEnabled(true);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumber=phone.getText().toString();
                String pwd=password.getText().toString();
                if (phoneNumber.equals("")||pwd.equals("")){
                    Toast toast=Toast.makeText(LoginActivity.this,"账号或密码不能为空！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else {
                    AVUser.loginByMobilePhoneNumberInBackground(phoneNumber, pwd, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            if (e == null) {
                                AVQuery<AVObject> query = new AVQuery<>("_User");
                                query.whereEqualTo("mobilePhoneNumber",phoneNumber);
                                query.getFirstInBackground(new GetCallback<AVObject>() {
                                    @Override
                                    public void done(AVObject avObject, AVException e) {
                                        Bundle b=new Bundle();
                                        b.putString("name",avObject.getString("username"));
                                        b.putString("email",avObject.getString("email"));
                                        Intent intent = new Intent(LoginActivity.this, PHRMainActivity.class);
                                        intent.putExtras(b);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Log.d(TAG, "+++++++++++++++: " + e.getCode());
                                if (e.getCode() == 0) {
                                    Toast toast = Toast.makeText(LoginActivity.this, "网络出差了！", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else if (e.getCode() == 211) {
                                    Toast toast = Toast.makeText(LoginActivity.this, "账号不存在！", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else {
                                    Toast toast = Toast.makeText(LoginActivity.this, "密码错误请重试！", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }


}
