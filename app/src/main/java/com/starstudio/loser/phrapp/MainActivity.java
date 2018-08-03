package com.starstudio.loser.phrapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.starstudio.loser.phrapp.common.view.PHRProgressDialog;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.phr_community_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("动  态"));
        tabLayout.addTab(tabLayout.newTab().setText("推  荐"));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }
}
