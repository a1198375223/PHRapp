package com.starstudio.loser.phrapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.bumptech.glide.Glide;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.utils.ConvertUriUtils;
import com.starstudio.loser.phrapp.common.utils.ShareUtils;
import com.starstudio.loser.phrapp.common.view.PHRSearchDialog;
import com.starstudio.loser.phrapp.common.view.PHRShareDialog;
import com.starstudio.loser.phrapp.item.community.search.fragment.view.SearchFrameFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import es.dmoral.toasty.Toasty;

public class MainActivity extends PHRActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.button);

        final List<AVObject> data = new ArrayList<>();
        AVObject article1 = AVObject.createWithoutData("Article", "5b6a462bee920a003b032ff1");
        for (int i=0; i<4; i++) {
            data.add(article1);
        }
        final ArrayList<String> strings = new ArrayList<>();
        for (AVObject av : data) {
            strings.add(av.toString());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            @Override
            public void onClick(View v) {
                container.setVisibility(View.INVISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SearchFrameFragment fragment = new SearchFrameFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("search", strings);
                fragment.setArguments(bundle);
                ft.add(R.id.container, fragment);
                ft.commit();
            }
        });

    }
}