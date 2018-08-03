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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
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
    Menu menu;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_main_layout);

        NavigationView navigationView =  (NavigationView) findViewById(R.id.phr_main_navigation_view);
        if (getIntent().getExtras()==null){
            initView(navigationView);//以前没登陆则侧滑栏显示登录按钮
            Log.d(TAG, "onCreate: initView()启动方式");
        }else {
            set_head_by_login(navigationView);//从登录页面跳转转来
            Log.d(TAG, "onCreate: set_head()启动方式");
        }

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

    private void set_head(AVUser avUser,NavigationView navigationView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        View headerView = navigationView.getHeaderView(0);
        menu = navigationView.getMenu();
        menu.findItem(R.id.phr_main_navigation_view_menu_item5).setVisible(true);
        menu.findItem(R.id.phr_main_navigation_view_menu_item4).setVisible(true);
        menu.findItem(R.id.phr_main_navigation_view_menu_item3).setVisible(true);//登录后让“退出登录”和“个人信息管理可见”可见
        name = (TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_name_text);
        note = (TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_note_text);
        head_img = (ImageView) headerView.findViewById(R.id.phr_main_navigation_view_header_icon);
        head_img.setVisibility(View.VISIBLE);
        login = headerView.findViewById(R.id.phr_phr_main_navigation_view_login);
        login.setVisibility(View.GONE);

        AVFile avFile = avUser.getAVFile("head_img");
        name.setText(avUser.getUsername());
        note.setText(avUser.getString("note"));

        Glide.with(PHRMainActivity.this)
                .load(avFile.getUrl())
                .apply(options)
                .into(head_img);

    }

    private void set_head_by_login(NavigationView navigationView){
        set_head(AVUser.getCurrentUser(),navigationView);
        save_user(true);
    }

    private void save_user(boolean b){
        SharedPreferences.Editor editor = getSharedPreferences("user_data",MODE_PRIVATE).edit();
        editor.putBoolean("is_login",b);
        editor.apply();
    }

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
        if (is_login()) {//若用户已经登录了，则主活动每次回到栈顶就刷新，因为其他活动可能更新头像、用户名
            AVQuery<AVUser> userQuery = new AVQuery<>("_User");
            userQuery.whereEqualTo("objectId",AVUser.getCurrentUser().getObjectId());
            userQuery.getFirstInBackground(new GetCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    set_head(avUser,(NavigationView) findViewById(R.id.phr_main_navigation_view));
                }
            }); //保存成功就更新当前页面的信息

            Log.d(TAG, "onResume: set_head()启动方式");
        }
        super.onResume();
    }
}
