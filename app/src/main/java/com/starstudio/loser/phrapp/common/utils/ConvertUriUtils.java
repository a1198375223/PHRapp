package com.starstudio.loser.phrapp.common.utils;

/*
    create by:loser
    date:2018/8/8 10:55
*/

import android.content.Context;
import android.net.Uri;

public class ConvertUriUtils {
    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
