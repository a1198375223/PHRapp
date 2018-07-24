[33mcommit 4af58ddd4f722751e2ead83fb37a126dc199848c[m[33m ([m[1;31morigin/master[m[33m)[m
Author: yafeng-Soong <397655952@qq.com>
Date:   Tue Jul 24 15:38:34 2018 +0800

    写好了登录,注册文件、布局和gradle文件都有改

[1mdiff --git a/.idea/caches/build_file_checksums.ser b/.idea/caches/build_file_checksums.ser[m
[1mindex 2f38339..b2bf1cd 100644[m
Binary files a/.idea/caches/build_file_checksums.ser and b/.idea/caches/build_file_checksums.ser differ
[1mdiff --git a/app/build.gradle b/app/build.gradle[m
[1mindex 3ca16f0..3ff76ca 100644[m
[1m--- a/app/build.gradle[m
[1m+++ b/app/build.gradle[m
[36m@@ -16,6 +16,13 @@[m [mandroid {[m
             proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'[m
         }[m
     }[m
[32m+[m[32m    packagingOptions{[m
[32m+[m[32m        exclude 'META-INF/LICENSE.txt'[m
[32m+[m[32m        exclude 'META-INF/NOTICE.txt'[m
[32m+[m[32m    }[m
[32m+[m[32m    lintOptions {[m
[32m+[m[32m        abortOnError false[m
[32m+[m[32m    }[m
 }[m
 [m
 dependencies {[m
[36m@@ -31,4 +38,17 @@[m [mdependencies {[m
     implementation 'com.trello.rxlifecycle2:rxlifecycle:2.2.1'[m
     implementation 'com.trello.rxlifecycle2:rxlifecycle-components:2.2.1'[m
     implementation 'com.makeramen:roundedimageview:2.3.0'[m
[32m+[m[32m    implementation 'de.hdodenhof:circleimageview:2.2.0'[m
[32m+[m
[32m+[m
[32m+[m[32m    // LeanCloud 基础包[m
[32m+[m[32m    implementation 'cn.leancloud.android:avoscloud-sdk:v4.6.4'[m
[32m+[m[32m    // LeanCloud 统计包[m
[32m+[m[32m    implementation 'cn.leancloud.android:avoscloud-statistics:v4.6.4'[m
[32m+[m[32m    // LeanCloud 用户反馈包[m
[32m+[m[32m    implementation 'cn.leancloud.android:avoscloud-feedback:v4.6.4@aar'[m
[32m+[m
[32m+[m[32m    //Glide依赖包[m
[32m+[m[32m    implementation 'com.github.bumptech.glide:glide:4.6.1'[m
[32m+[m[32m    annotationProcessor 'com.github.bumptech.glide:compiler:4.6.1'[m
 }[m
[1mdiff --git a/app/src/main/AndroidManifest.xml b/app/src/main/AndroidManifest.xml[m
[1mindex e7b3701..5db6acc 100644[m
[1m--- a/app/src/main/AndroidManifest.xml[m
[1m+++ b/app/src/main/AndroidManifest.xml[m
[36m@@ -2,6 +2,12 @@[m
 <manifest xmlns:android="http://schemas.android.com/apk/res/android"[m
     package="com.starstudio.loser.phrapp">[m
 [m
[32m+[m[32m    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />[m
[32m+[m[32m    <uses-permission android:name="android.permission.INTERNET"/>[m
[32m+[m[32m    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />[m
[32m+[m[32m    <uses-permission android:name="android.permission.READ_PHONE_STATE" />[m
[32m+[m[32m    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />[m
[32m+[m
     <application[m
         android:name=".common.PHRApplication"[m
         android:allowBackup="true"[m
[36m@@ -11,15 +17,21 @@[m
         android:supportsRtl="true"[m
         android:theme="@style/PHR.AppTheme">[m
         <activity android:name=".item.main.PHRMainActivity">[m
[32m+[m[32m            <!--<intent-filter>-->[m
[32m+[m[32m                <!--<action android:name="android.intent.action.MAIN" />-->[m
[32m+[m
[32m+[m[32m                <!--<category android:name="android.intent.category.LAUNCHER" />-->[m
[32m+[m[32m            <!--</intent-filter>-->[m
[32m+[m[32m        </activity>[m
[32m+[m[32m        <activity android:name=".common.PHRActivity" />[m
[32m+[m[32m        <activity android:name=".item.message.PHRMessageActivity" />[m
[32m+[m[32m        <activity android:name=".item.login.LoginActivity">[m
             <intent-filter>[m
                 <action android:name="android.intent.action.MAIN" />[m
 [m
                 <category android:name="android.intent.category.LAUNCHER" />[m
             </intent-filter>[m
         </activity>[m
[31m-[m
[31m-        <activity android:name=".common.PHRActivity"/>[m
[31m-        <activity android:name=".item.message.PHRMessageActivity"/>[m
     </application>[m
 [m
 </manifest>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/com/starstudio/loser/phrapp/item/login/LoginActivity.java b/app/src/main/java/com/starstudio/loser/phrapp/item/login/LoginActivity.java[m
[1mnew file mode 100644[m
[1mindex 0000000..6510eab[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/com/starstudio/loser/phrapp/item/login/LoginActivity.java[m
[36m@@ -0,0 +1,100 @@[m
[32m+[m[32mpackage com.starstudio.loser.phrapp.item.login;[m[41m[m
[32m+[m[41m[m
[32m+[m[32mimport android.content.Intent;[m[41m[m
[32m+[m[32mimport android.support.v7.app.AppCompatActivity;[m[41m[m
[32m+[m[32mimport android.os.Bundle;[m[41m[m
[32m+[m[32mimport android.util.Log;[m[41m[m
[32m+[m[32mimport android.view.Gravity;[m[41m[m
[32m+[m[32mimport android.view.View;[m[41m[m
[32m+[m[32mimport android.widget.Button;[m[41m[m
[32m+[m[32mimport android.widget.EditText;[m[41m[m
[32m+[m[32mimport android.widget.TextView;[m[41m[m
[32m+[m[32mimport android.widget.Toast;[m[41m[m
[32m+[m[41m[m
[32m+[m[32mimport com.avos.avoscloud.AVException;[m[41m[m
[32m+[m[32mimport com.avos.avoscloud.AVOSCloud;[m[41m[m
[32m+[m[32mimport com.avos.avoscloud.AVObject;[m[41m[m
[32m+[m[32mimport com.avos.avoscloud.AVQuery;[m[41m[m
[32m+[m[32mimport com.avos.avoscloud.AVUser;[m[41m[m
[32m+[m[41m[m
[32m+[m[32mimport com.avos.avoscloud.GetCallback;[m[41m[m
[32m+[m[32mimport com.avos.avoscloud.LogInCallback;[m[41m[m
[32m+[m[32mimport com.starstudio.loser.phrapp.R;[m[41m[m
[32m+[m[32mimport com.starstudio.loser.phrapp.item.main.PHRMainActivity;[m[41m[m
[32m+[m[41m[m
[32m+[m[41m[m
[32m+[m[41m[m
[32m+[m[32mpublic class LoginActivity extends AppCompatActivity {[m[41m[m
[32m+[m[41m[m
[32m+[m[32m    private static final String TAG = "LoginActivity";[m[41m[m
[32m+[m[32m    private EditText phone,password;[m[41m[m
[32m+[m[32m    private Button login_btn;[m[41m[m
[32m+[m[32m    private TextView sign_in;[m[41m[m
[32m+[m[41m[m
[32m+[m[32m    @Override[m[41m[m
[32m+[m[32m    protected void onCreate(Bundle savedInstanceState) {[m[41m[m
[32m+[m[32m        super.onCreate(savedInstanceState);[m[41m[m
[32m+[m[32m        setContentView(R.layout.phr_activity_login);[m[41m[m
[32m+[m[32m        initLogin();[m[41m[m
[32m+[m[41m[m
[32m+[m[32m    }[m[41m[m
[32m+[m[41m[m
[32m+[m[32m    private void initLogin(){[m[41m[m
[32m+[m[32m        phone=(EditText) findViewById(R.id.phr_login_username);[m[41m[m
[32m+[m[32m        password=(EditText) findViewById(R.id.phr_login_password);[m[41m[m
[32m+[m[32m        login_btn=(Button) findViewById(R.id.phr_login_login);[m[41m[m
[32m+[m[32m        sign_in=(TextView) findViewById(R.id.tv_register);[m[41m[m
[32m+[m[32m        AVOSCloud.initialize(this,"NUjpdRi6jqP1S2iAfQCs7YNU-gzGzoHsz","27zlhvjRBd155W8iAWSoNJiO");[m[41m[m
[32m+[m[32m        AVOSCloud.setDebugLogEnabled(true);[m[41m[m
[32m+[m[32m        login_btn.setOnClickListener(new View.OnClickListener() {[m[41m[m
[32m+[m[32m            @Override[m[41m[m
[32m+[m[32m            public void onClick(View view) {[m[41m[m
[32m+[m[32m                final String phoneNumber=phone.getText().toString();[m[41m[m
[32m+[m[32m                String pwd=password.getText().toString();[m[41m[m
[32m+[m[32m                if (phoneNumber.equals("")||pwd.equals("")){[m[41m[m
[32m+[m[32m                    Toast toast=Toast.makeText(LoginActivity.this,"账号或密码不能为空！",Toast.LENGTH_SHORT);[m[41m[m
[32m+[m[32m                    toast.setGravity(Gravity.CENTER,0,0);[m[41m[m
[32m+[m[32m                    toast.show();[m[41m[m
[32m+[m[32m                }else {[m[41m[m
[32m+[m[32m                    AVUser.loginByMobilePhoneNumberInBackground(phoneNumber, pwd, new LogInCallback<AVUser>() {[m[41m[m
[32m+[m[32m                        @Override[m[41m[m
[32m+[m[32m                        public void done(AVUser avUser, AVException e) {[m[41m[m
[32m+[m[32m                            if (e == null) {[m[41m[m
[32m+[m[32m                                AVQuery<AVObject> query = new AVQuery<>("_User");[m[41m[m
[32m+[m[32m                                query.whereEqualTo("mobilePhoneNumber",phoneNumber);[m[41m[m
[32m+[m[32m                                query.getFirstInBackground(new GetCallback<AVObject>() {[m[41m[m
[32m+[m[32m                                    @Override[m[41m[m
[32m+[m[32m                                    public void done(AVObject avObject, AVException e) {[m[41m[m
[32m+[m[32m                                        Bundle b=new Bundle();[m[41m[m
[32m+[m[32m                                        b.putString("name",avObject.getString("username"));[m[41m[m
[32m+[m[32m                                        b.putString("email",avObject.getString("email"));[m[41m[m
[32m+[m[32m                                        Intent intent = new Intent(LoginActivity.this, PHRMainActivity.class);[m[41m[m
[32m+[m[32m                                        intent.putExtras(b);[m[41m[m
[32m+[m[32m                                        startActivity(intent);[m[41m[m
[32m+[m[32m                                    }[m[41m[m
[32m+[m[32m                                });[m[41m[m
[32m+[m[32m                            } else {[m[41m[m
[32m+[m[32m                                Log.d(TAG, "+++++++++++++++: " + e.getCode());[m[41m[m
[32m+[m[32m                                if (e.getCode() == 0) {[m[41m[m
[32m+[m[32m                                    Toast toast = Toast.makeText(LoginActivity.this, "网络出差了！", Toast.LENGTH_SHORT);[m[41m[m
[32m+[m[32m                                    toast.setGravity(Gravity.CENTER, 0, 0);[m[41m[m
[32m+[m[32m                                    toast.show();[m[41m[m
[32m+[m[32m                                } else if (e.getCode() == 211) {[m[41m[m
[32m+[m[32m                                    Toast toast = Toast.makeText(LoginActivity.this, "账号不存在！", Toast.LENGTH_SHORT);[m[41m[m
[32m+[m[32m                                    toast.setGravity(Gravity.CENTER, 0, 0);[m[41m[m
[32m+[m[32m                                    toast.show();[m[41m[m
[32m+[m[32m                                } else {[m[41m[m
[32m+[m[32m                                    Toast toast = Toast.makeText(LoginActivity.this, "密码错误请重试！", Toast.LENGTH_SHORT);[m[41m[m
[32m+[m[32m                                    toast.setGravity(Gravity.CENTER, 0, 0);[m[41m[m
[32m+[m[32m                                    toast.show();[m[41m[m
[32m+[m[32m                                }[m[41m[m
[32m+[m[32m                            }[m[41m[m
[32m+[m[32m                        }[m[41m[m
[32m+[m[32m                    });[m[41m[m
[32m+[m[32m                }[m[41m[m
[32m+[m[32m            }[m[41m[m
[32m+[m[32m        });[m[41m[m
[32m+[m[32m    }[m[41m[m
[32m+[m[41m[m
[32m+[m[41m[m
[32m+[m[32m}[m[41m[m
[1mdiff --git a/app/src/main/java/com/starstudio/loser/phrapp/item/main/PHRMainActivity.java b/app/src/main/java/com/starstudio/loser/phrapp/item/main/PHRMainActivity.java[m
[1mindex 22e1d18..220af0a 100644[m
[1m--- a/app/src/main/java/com/starstudio/loser/phrapp/item/main/PHRMainActivity.java[m
[1m+++ b/app/src/main/java/com/starstudio/loser/phrapp/item/main/PHRMainActivity.java[m
[36m@@ -6,11 +6,22 @@[m [mpackage com.starstudio.loser.phrapp.item.main;[m
 */[m
 [m
 import android.annotation.SuppressLint;[m
[32m+[m[32mimport android.content.Intent;[m
[32m+[m[32mimport android.graphics.Bitmap;[m
 import android.os.Build;[m
 import android.os.Bundle;[m
 import android.support.annotation.Nullable;[m
 import android.support.annotation.RequiresApi;[m
[32m+[m[32mimport android.support.design.widget.NavigationView;[m
[32m+[m[32mimport android.util.Log;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.widget.ImageView;[m
[32m+[m[32mimport android.widget.TextView;[m
 [m
[32m+[m[32mimport com.bumptech.glide.Glide;[m
[32m+[m[32mimport com.bumptech.glide.load.engine.DiskCacheStrategy;[m
[32m+[m[32mimport com.bumptech.glide.request.RequestOptions;[m
[32m+[m[32mimport com.makeramen.roundedimageview.RoundedImageView;[m
 import com.starstudio.loser.phrapp.R;[m
 import com.starstudio.loser.phrapp.common.PHRActivity;[m
 import com.starstudio.loser.phrapp.item.main.model.MainModelmpl;[m
[36m@@ -19,12 +30,18 @@[m [mimport com.starstudio.loser.phrapp.item.main.view.MainViewImpl;[m
 [m
 @SuppressLint("Registered")[m
 public class PHRMainActivity extends PHRActivity{[m
[32m+[m[32m    private static final String TAG = "PHRMainActivity";[m
     MainPresenterImpl mPresenter;[m
[32m+[m[32m    TextView name,email;[m
[32m+[m[32m    ImageView head_img;[m
[32m+[m
     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)[m
     @Override[m
     protected void onCreate(@Nullable Bundle savedInstanceState) {[m
         super.onCreate(savedInstanceState);[m
         setContentView(R.layout.phr_main_layout);[m
[32m+[m[32m        Log.d(TAG, "+++++++++++++++++++:----------------");[m
[32m+[m[32m        set_head(getIntent(),(NavigationView) findViewById(R.id.phr_main_navigation_view));//设置navigationView的名字图片[m
 [m
         mPresenter = new MainPresenterImpl(this);[m
         mPresenter.setModel(new MainModelmpl());[m
[36m@@ -37,4 +54,24 @@[m [mpublic class PHRMainActivity extends PHRActivity{[m
         super.onDestroy();[m
         mPresenter.detach();[m
     }[m
[32m+[m
[32m+[m[32m    private void set_head(Intent intent, NavigationView navigationView){[m
[32m+[m[32m        RequestOptions options = new RequestOptions()[m
[32m+[m[32m                .placeholder(R.drawable.waiting)[m
[32m+[m[32m                .error(R.drawable.default_head)[m
[32m+[m[32m                .diskCacheStrategy(DiskCacheStrategy.NONE);[m
[32m+[m
[32m+[m[32m        Bundle b=intent.getExtras();[m
[32m+[m[32m        View headerView = navigationView.getHeaderView(0);[m
[32m+[m[32m        name=(TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_name_text);[m
[32m+[m[32m        email=(TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_note_text);[m
[32m+[m[32m        head_img=(ImageView) headerView.findViewById(R.id.phr_main_navigation_view_header_icon);[m
[32m+[m[32m        name.setText(b.getString("name"));[m
[32m+[m[32m        email.setText(b.getString("email"));[m
[32m+[m[32m        Glide.with(PHRMainActivity.this)[m
[32m+[m[32m                .load("http://lc-nujpdri6.cn-n1.lcfile.com/dcde1581cbcb099c1fb6.png")[m
[32m+[m[32m                .apply(options)[m
[32m+[m[32m                .into(head_img);[m
[32m+[m[32m        //head_img.setImageBitmap((Bitmap) b.getParcelable("head"));[m
[32m+[m[32m    }[m
 }[m
[1mdiff --git a/app/src/main/res/drawable/default_head.png b/app/src/main/res/drawable/default_head.png[m
[1mnew file mode 100644[m
[1mindex 0000000..daa4596[m
Binary files /dev/null and b/app/src/main/res/drawable/default_head.png differ
[1mdiff --git a/app/src/main/res/drawable/waiting.png b/app/src/main/res/drawable/waiting.png[m
[1mnew file mode 100644[m
[1mindex 0000000..aacfe75[m
Binary files /dev/null and b/app/src/main/res/drawable/waiting.png differ
[1mdiff --git a/app/src/main/res/layout/phr_activity_login.xml b/app/src/main/res/layout/phr_activity_login.xml[m
[1mindex caa7039..b756c1c 100644[m
[1m--- a/app/src/main/res/layout/phr_activity_login.xml[m
[1m+++ b/app/src/main/res/layout/phr_activity_login.xml[m
[36m@@ -40,7 +40,7 @@[m
             android:layout_height="wrap_content"[m
             android:id="@+id/phr_login_username"[m
             android:textColor="@color/color_login_text1"[m
[31m-            android:hint="@string/phr_login_username"/>[m
[32m+[m[32m            android:hint="手机号"/>[m
     </android.support.design.widget.TextInputLayout>[m
 [m
     <android.support.design.widget.TextInputLayout[m
[1mdiff --git a/app/src/main/res/layout/phr_main_navigation_view_header.xml b/app/src/main/res/layout/phr_main_navigation_view_header.xml[m
[1mindex 3aede0a..ce5671e 100644[m
[1m--- a/app/src/main/res/layout/phr_main_navigation_view_header.xml[m
[1m+++ b/app/src/main/res/layout/phr_main_navigation_view_header.xml[m
[36m@@ -6,43 +6,78 @@[m
     xmlns:tools="http://schemas.android.com/tools"[m
     android:background="@color/color_main_image_bg">[m
 [m
[31m-    <com.makeramen.roundedimageview.RoundedImageView[m
[32m+[m[32m    <!--<com.makeramen.roundedimageview.RoundedImageView-->[m
[32m+[m[32m        <!--android:layout_width="70dp"-->[m
[32m+[m[32m        <!--android:layout_height="70dp"-->[m
[32m+[m[32m        <!--android:layout_centerVertical="true"-->[m
[32m+[m[32m        <!--android:src="@mipmap/ic_launcher"-->[m
[32m+[m[32m        <!--app:riv_oval="true"-->[m
[32m+[m[32m        <!--android:layout_margin="20dp"-->[m
[32m+[m[32m        <!--android:id="@+id/phr_main_navigation_view_header_icon"/>-->[m
[32m+[m
[32m+[m[32m    <!--<de.hdodenhof.circleimageview.CircleImageView-->[m
[32m+[m[32m        <!--android:layout_width="70dp"-->[m
[32m+[m[32m        <!--android:layout_height="70dp"-->[m
[32m+[m[32m        <!--android:layout_centerVertical="true"-->[m
[32m+[m[32m        <!--android:src="@mipmap/ic_launcher"-->[m
[32m+[m[32m        <!--app:riv_oval="true"-->[m
[32m+[m[32m        <!--android:layout_margin="20dp"-->[m
[32m+[m[32m        <!--android:id="@+id/phr_main_navigation_view_header_icon"/>-->[m
[32m+[m
[32m+[m
[32m+[m[32m    <!--<LinearLayout-->[m
[32m+[m[32m        <!--android:layout_width="wrap_content"-->[m
[32m+[m[32m        <!--android:layout_height="wrap_content"-->[m
[32m+[m[32m        <!--android:orientation="vertical"-->[m
[32m+[m[32m        <!--android:id="@+id/linear"-->[m
[32m+[m[32m        <!--android:layout_toRightOf="@+id/phr_main_navigation_view_header_icon"-->[m
[32m+[m[32m        <!--android:layout_toEndOf="@+id/phr_main_navigation_view_header_icon"-->[m
[32m+[m[32m        <!--android:layout_centerVertical="true">-->[m
[32m+[m[32m        <!--<TextView-->[m
[32m+[m[32m            <!--android:layout_width="wrap_content"-->[m
[32m+[m[32m            <!--android:layout_height="wrap_content"-->[m
[32m+[m[32m            <!--tools:text="@string/phr_main_navigation_view_header_name"-->[m
[32m+[m[32m            <!--android:id="@+id/phr_main_navigation_view_header_name_text"-->[m
[32m+[m[32m            <!--android:textSize="@dimen/phr_main_navigation_view_header_name_size"-->[m
[32m+[m[32m            <!--android:padding="@dimen/phr_main_text_padding"-->[m
[32m+[m[32m            <!--android:layout_margin="@dimen/phr_main_navigation_view_header_text_margin_distance"/>-->[m
[32m+[m
[32m+[m[32m        <!--<TextView-->[m
[32m+[m[32m            <!--android:layout_width="wrap_content"-->[m
[32m+[m[32m            <!--android:layout_height="wrap_content"-->[m
[32m+[m[32m            <!--tools:text="@string/phr_main_navigation_view_header_note"-->[m
[32m+[m[32m            <!--android:padding="@dimen/phr_main_text_padding"-->[m
[32m+[m[32m            <!--android:id="@+id/phr_main_navigation_view_header_note_text"-->[m
[32m+[m[32m            <!--android:textSize="@dimen/phr_main_navigation_view_header_note_size"-->[m
[32m+[m[32m            <!--android:layout_margin="@dimen/phr_main_navigation_view_header_text_margin_distance"/>-->[m
[32m+[m
[32m+[m[32m    <!--</LinearLayout>-->[m
[32m+[m
[32m+[m[32m    <de.hdodenhof.circleimageview.CircleImageView[m
[32m+[m[32m        android:id="@+id/phr_main_navigation_view_header_icon"[m
[32m+[m[32m        android:layout_width="100dp"[m
[32m+[m[32m        android:layout_height="100dp"[m
[32m+[m[32m        android:layout_centerInParent="true"/>[m
[32m+[m
[32m+[m[32m    <TextView[m
[32m+[m[32m        android:id="@+id/phr_main_navigation_view_header_note_text"[m
[32m+[m[32m        tools:text="@string/phr_main_navigation_view_header_note"[m
         android:layout_width="wrap_content"[m
         android:layout_height="wrap_content"[m
[31m-        android:layout_centerVertical="true"[m
[31m-        android:src="@mipmap/ic_launcher"[m
[31m-        app:riv_oval="true"[m
[31m-        android:layout_margin="20dp"[m
[31m-        android:id="@+id/phr_main_navigation_view_header_icon"/>[m
[32m+[m[32m        android:layout_alignParentBottom="true"[m
[32m+[m[32m        android:textColor="#FFF"[m
[32m+[m[32m        android:textSize="14sp"[m
[32m+[m[32m        android:layout_centerInParent="true"/>[m
 [m
[31m-    <LinearLayout[m
[32m+[m[32m    <TextView[m
[32m+[m[32m        android:id="@+id/phr_main_navigation_view_header_name_text"[m
[32m+[m[32m        android:layout_above="@id/phr_main_navigation_view_header_note_text"[m
[32m+[m[32m        tools:text="@string/phr_main_navigation_view_header_name"[m
         android:layout_width="wrap_content"[m
         android:layout_height="wrap_content"[m
[31m-        android:orientation="vertical"[m
[31m-        android:layout_toRightOf="@+id/phr_main_navigation_view_header_icon"[m
[31m-        android:layout_toEndOf="@+id/phr_main_navigation_view_header_icon"[m
[31m-        android:layout_centerVertical="true">[m
[31m-        <TextView[m
[31m-            android:layout_width="wrap_content"[m
[31m-            android:layout_height="wrap_content"[m
[31m-            tools:text="@string/phr_main_navigation_view_header_name"[m
[31m-            android:id="@+id/phr_main_navigation_view_header_name_text"[m
[31m-            android:textSize="@dimen/phr_main_navigation_view_header_name_size"[m
[31m-            android:padding="@dimen/phr_main_text_padding"[m
[31m-            android:layout_margin="@dimen/phr_main_navigation_view_header_text_margin_distance"/>[m
[31m-[m
[31m-        <TextView[m
[31m-            android:layout_width="wrap_content"[m
[31m-            android:layout_height="wrap_content"[m
[31m-            tools:text="@string/phr_main_navigation_view_header_note"[m
[31m-            android:padding="@dimen/phr_main_text_padding"[m
[31m-            android:id="@+id/phr_main_navigation_view_header_note_text"[m
[31m-            android:textSize="@dimen/phr_main_navigation_view_header_note_size"[m
[31m-            android:layout_margin="@dimen/phr_main_navigation_view_header_text_margin_distance"/>[m
[31m-[m
[31m-    </LinearLayout>[m
[31m-[m
[31m-[m
[32m+[m[32m        android:textColor="#FFF"[m
[32m+[m[32m        android:textSize="14sp"[m
[32m+[m[32m        android:layout_centerInParent="true"/>[m
 [m
 [m
 </RelativeLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/values/styles.xml b/app/src/main/res/values/styles.xml[m
[1mindex 5ecc523..56640e3 100644[m
[1m--- a/app/src/main/res/values/styles.xml[m
[1m+++ b/app/src/main/res/values/styles.xml[m
[36m@@ -25,4 +25,5 @@[m
     </style>[m
 [m
 [m
[32m+[m
 </resources>[m
[1mdiff --git a/build.gradle b/build.gradle[m
[1mindex 43c0708..2e6fb2b 100644[m
[1m--- a/build.gradle[m
[1m+++ b/build.gradle[m
[36m@@ -5,6 +5,9 @@[m [mbuildscript {[m
     repositories {[m
         google()[m
         jcenter()[m
[32m+[m[32m        maven {[m
[32m+[m[32m            url "http://mvn.leancloud.cn/nexus/content/repositories/public"[m
[32m+[m[32m        }[m
     }[m
     dependencies {[m
         classpath 'com.android.tools.build:gradle:3.1.3'[m
[36m@@ -19,6 +22,9 @@[m [mallprojects {[m
     repositories {[m
         google()[m
         jcenter()[m
[32m+[m[32m        maven {[m
[32m+[m[32m            url "http://mvn.leancloud.cn/nexus/content/repositories/public"[m
[32m+[m[32m        }[m
     }[m
 }[m
 [m
