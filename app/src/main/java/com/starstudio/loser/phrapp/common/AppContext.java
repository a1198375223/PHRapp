package com.starstudio.loser.phrapp.common;

/*
    create by:loser
    date:2018/7/31 21:36
*/

import android.content.Context;

public class AppContext {
    private static Context sAppContext = null;

    public static void init(Context context) {
        sAppContext = context.getApplicationContext();
    }

    public static Context get(){
        return sAppContext;
    }
}
