package com.starstudio.loser.phrapp.item.main;

/*
    create by:loser
    date:2018/7/23 13:22
*/

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.item.login.LoginActivity;
import com.starstudio.loser.phrapp.item.main.model.MainModelmpl;
import com.starstudio.loser.phrapp.item.main.presenter.MainPresenterImpl;
import com.starstudio.loser.phrapp.item.main.view.MainViewImpl;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("Registered")
public class PHRMainActivity extends PHRActivity{
    private static final String TAG = "PHRMainActivity";
    MainPresenterImpl mPresenter;
    TextView name,note;
    ImageView head_img;
    Button login;
    Menu logout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_main_layout);

        //通过检测Bundle来判断是否由Login启动，若不是再判断以前是否登录
//        NavigationView navigationView =  (NavigationView) findViewById(R.id.phr_main_navigation_view);
//        Bundle b = getIntent().getExtras();
//        if (b==null){
//            Map<String,String> map = get_user();
//            if (map==null||map.get("name").equals("")){
//                initView(navigationView);//以前没登陆则侧滑栏显示登录按钮
//                Log.d(TAG, "onCreate: initView()启动方式");
//            }else {
//                set_head(map, navigationView);//以前登录过则用保存的字段设置navigationView的名字图片
//                Log.d(TAG, "onCreate: set_head()启动方式");
//            }
//        }else{
//            set_head_by_login(b,navigationView);//从登录页面跳转转来
//            Log.d(TAG, "onCreate: set_head_by_login()启动方式");
//        }
//
//        Log.d(TAG, "+++++++++++++++++++:----------------测试成功");


        mPresenter = new MainPresenterImpl(this);
        mPresenter.setModel(new MainModelmpl());
        mPresenter.setView(new MainViewImpl(this));
        mPresenter.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public void initView(NavigationView navigationView){
        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_name_text);
        note = (TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_note_text);
        name.setText("");
        note.setText("");
        head_img = (ImageView) headerView.findViewById(R.id.phr_main_navigation_view_header_icon);
        head_img.setVisibility(View.GONE);
        login = headerView.findViewById(R.id.phr_phr_main_navigation_view_login);
        login.setVisibility(View.VISIBLE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PHRMainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


//    private void set_head(Map<String,String> map, NavigationView navigationView) {
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.waiting)
//                .error(R.drawable.default_head)
//                .diskCacheStrategy(DiskCacheStrategy.NONE);
//
//        View headerView = navigationView.getHeaderView(0);
//        logout = navigationView.getMenu();
//        logout.findItem(R.id.phr_main_navigation_view_menu_item5).setVisible(true);
//        logout.findItem(R.id.phr_main_navigation_view_menu_item4).setVisible(true);//登录后让“退出登录”和“个人信息管理可见”可见
//        name = (TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_name_text);
//        note = (TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_note_text);
//        head_img = (ImageView) headerView.findViewById(R.id.phr_main_navigation_view_header_icon);
//        head_img.setVisibility(View.VISIBLE);
//        login = headerView.findViewById(R.id.phr_phr_main_navigation_view_login);
//        login.setVisibility(View.GONE);
//
//        name.setText(map.get("name").toString());
//        note.setText(map.get("note").toString());
//        Glide.with(PHRMainActivity.this)
//                .load(map.get("url").toString())
//                .apply(options)
//                .into(head_img);
//    }

    private void set_head(NavigationView navigationView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        View headerView = navigationView.getHeaderView(0);
        logout = navigationView.getMenu();
        logout.findItem(R.id.phr_main_navigation_view_menu_item5).setVisible(true);
        logout.findItem(R.id.phr_main_navigation_view_menu_item4).setVisible(true);//登录后让“退出登录”和“个人信息管理可见”可见
        name = (TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_name_text);
        note = (TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_note_text);
        head_img = (ImageView) headerView.findViewById(R.id.phr_main_navigation_view_header_icon);
        head_img.setVisibility(View.VISIBLE);
        login = headerView.findViewById(R.id.phr_phr_main_navigation_view_login);
        login.setVisibility(View.GONE);

        AVUser avUser = AVUser.getCurrentUser();
        AVFile avFile = avUser.getAVFile("head");
        name.setText(avUser.getUsername());
        note.setText(avUser.getString("note"));

        Glide.with(PHRMainActivity.this)
                .load(avFile.getUrl())
                .apply(options)
                .into(head_img);

    }

    private void set_head_by_login(Bundle b,NavigationView navigationView){
        set_head(navigationView);
        save_user(true);
    }

//    public  void set_head_after_modify(){
//        Map<String,String> map = new HashMap();
//        map.put("name", AVUser.getCurrentUser().getUsername());
//        map.put("note",AVUser.getCurrentUser().getString("note"));
//        AVFile file = AVUser.getCurrentUser().getAVFile("head_img");
//        map.put("url", file.getUrl());
//        set_head(map,(NavigationView) findViewById(R.id.phr_main_navigation_view));
//        save_user(map);
//    }

    private void save_user(boolean b){
        SharedPreferences.Editor editor = getSharedPreferences("user_data",MODE_PRIVATE).edit();
        editor.putBoolean("is_login",b);
        editor.apply();
    }

//    private Map<String,String> get_user(){
//        try {
//            SharedPreferences pref = getSharedPreferences("user_data",MODE_PRIVATE );
//            Map map = new HashMap();
//            map.put("name",pref.getString("name",""));
//            map.put("note",pref.getString("note",""));
//            map.put("url",pref.getString("url",""));
//            return map;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    private boolean is_login(){
        try {
            SharedPreferences pref = getSharedPreferences("user_data",MODE_PRIVATE );
            return pref.getBoolean("is_login",false);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onResume() {
        NavigationView navigationView =  (NavigationView) findViewById(R.id.phr_main_navigation_view);
        Bundle b = getIntent().getExtras();
        if (b==null){
            if (!is_login()){
                initView(navigationView);//以前没登陆则侧滑栏显示登录按钮
                Log.d(TAG, "onCreate: initView()启动方式");
            }else {
                set_head(navigationView);//以前登录过则用保存的字段设置navigationView的名字图片
                Log.d(TAG, "onCreate: set_head()启动方式");
            }
        }else{
            set_head_by_login(b,navigationView);//从登录页面跳转转来
            Log.d(TAG, "onCreate: set_head_by_login()启动方式");
        }

        Log.d(TAG, "+++++++++++++++++++:----------------测试成功");
        super.onResume();
        Log.d(TAG, "onResume: 从个人信息页面启动！");
    }
}
