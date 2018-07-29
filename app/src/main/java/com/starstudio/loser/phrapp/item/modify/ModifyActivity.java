package com.starstudio.loser.phrapp.item.modify;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.starstudio.loser.phrapp.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ModifyActivity";
    private ImageView head_back,head_img,home_icon;
    private TextView change_head,name,note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_modify);
        initView();
        Log.d(TAG, "onCreate: 查询邮箱"+AVUser.getCurrentUser().getEmail());
//        AVOSCloud.initialize(this,"NUjpdRi6jqP1S2iAfQCs7YNU-gzGzoHsz","27zlhvjRBd155W8iAWSoNJiO");
//        AVOSCloud.setDebugLogEnabled(true);
//        AVQuery<AVObject> query = new AVQuery<>("_User");
//        query.whereEqualTo("mobilePhoneNumber","18246443492");
//        AVUser.getCurrentUser().setEmail("397655952@qq.com");
//        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                AVUser.getCurrentUser().setEmail("18246443492@163.com");
//                AVUser.getCurrentUser().saveInBackground();
//                Log.d(TAG, "onCreate: 查询邮箱"+AVUser.getCurrentUser().getEmail());
//            }
//        });
    }
    private void initView() {
        Bundle bundle = getIntent().getExtras();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        name = (TextView) findViewById(R.id.phr_modify_name);
        name.setText(bundle.getString("name"));
        note = (TextView) findViewById(R.id.phr_modify_note);
        note.setText(bundle.getString("note"));
        head_back = (ImageView) findViewById(R.id.phr_modify_back);
        head_img = (ImageView) findViewById(R.id.phr_modify_head);
        home_icon = (ImageView) findViewById(R.id.phr_modify_homeicon);
        home_icon.bringToFront();
        home_icon.setOnClickListener(this);
        change_head = (TextView) findViewById(R.id.phr_modify_change_head);
        change_head.bringToFront();
        change_head.setOnClickListener(this);

        Glide.with(this).load(bundle.getString("url"))
                .apply(options.bitmapTransform(new BlurTransformation(25, 3)))
                .into(head_back);
        Glide.with(this)
                .load(bundle.getString("url"))
                .apply(options)
                .into(head_img);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phr_modify_homeicon:
                finish();
                break;
            case R.id.phr_modify_change_head:
                Toast.makeText(ModifyActivity.this,"改变头像",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
