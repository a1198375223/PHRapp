package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/23 13:08
*/

import android.app.Activity;

public class PHRView implements BaseView {
    private Activity mActivity;

    public PHRView(Activity activity) {
        this.mActivity = activity;
    }

    protected Activity getActivity() {
        return mActivity;
    }
}
