package com.starstudio.loser.phrapp.item.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.main.PHRMainActivity;
import com.starstudio.loser.phrapp.item.register.RegisterActivity;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText phone,password;
    private Button login_btn;
    private TextView sign_in,find_pwd;

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
                                        b.putString("url",avObject.getAVFile("head_img").getUrl());
                                        b.putString("name",avObject.getString("username"));
                                        b.putString("note",avObject.getString("email"));
                                        Intent intent = new Intent(LoginActivity.this, PHRMainActivity.class);
                                        intent.putExtras(b);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                if (e.getCode() == 0) {
                                    Toast toast = Toast.makeText(LoginActivity.this, "无法连接到服务器！", Toast.LENGTH_SHORT);
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

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPassword();
            }

        });
    }

    private void findPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("请输入注册时的邮箱");    //设置对话框标题
        final EditText edit = new EditText(LoginActivity.this);
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AVUser.requestPasswordResetInBackground(edit.getText().toString(), new RequestPasswordResetCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "已经向您的邮箱发送重置密码链接，注意查收" , Toast.LENGTH_SHORT).show();
                        } else {
                            //e.printStackTrace();
                            Log.d(TAG, "邮箱找回密码： "+e.getCode());
                            if (e.getCode() == 0) {
                                Toast toast = Toast.makeText(LoginActivity.this, "无法连接到服务器！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }else if (e.getCode()==205){
                                Toast toast = Toast.makeText(LoginActivity.this, "该邮箱未被注册过！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "你点了取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }

}
