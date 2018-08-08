package com.starstudio.loser.phrapp.common;

/*
    create by:loser
    date:2018/7/23 13:03
*/

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.mob.MobSDK;

public class PHRApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"NUjpdRi6jqP1S2iAfQCs7YNU-gzGzoHsz","27zlhvjRBd155W8iAWSoNJiO");
        AppContext.init(this);
        MobSDK.init(this);

    }
}
