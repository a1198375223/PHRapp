package com.starstudio.loser.phrapp.item.main;

/*
    create by:loser
    date:2018/7/23 13:22
*/

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.item.main.model.MainModelmpl;
import com.starstudio.loser.phrapp.item.main.presenter.MainPresenterImpl;
import com.starstudio.loser.phrapp.item.main.view.MainViewImpl;

@SuppressLint("Registered")
public class PHRMainActivity extends PHRActivity{
    private static final String TAG = "PHRMainActivity";
    MainPresenterImpl mPresenter;
    TextView name,email;
    ImageView head_img;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_main_layout);
        Log.d(TAG, "+++++++++++++++++++:----------------");
        set_head(getIntent(),(NavigationView) findViewById(R.id.phr_main_navigation_view));//设置navigationView的名字图片

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

    private void set_head(Intent intent, NavigationView navigationView){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Bundle b=intent.getExtras();
        View headerView = navigationView.getHeaderView(0);
        name=(TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_name_text);
        email=(TextView) headerView.findViewById(R.id.phr_main_navigation_view_header_note_text);
        head_img=(ImageView) headerView.findViewById(R.id.phr_main_navigation_view_header_icon);
    /*    name.setText(b.getString("name"));
        email.setText(b.getString("email"));*/
        Glide.with(PHRMainActivity.this)
                .load("http://lc-nujpdri6.cn-n1.lcfile.com/dcde1581cbcb099c1fb6.png")
                .apply(options)
                .into(head_img);
        //head_img.setImageBitmap((Bitmap) b.getParcelable("head"));
    }
}
