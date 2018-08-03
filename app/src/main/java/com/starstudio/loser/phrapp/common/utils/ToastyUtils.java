package com.starstudio.loser.phrapp.common.utils;

/*
    create by:loser
    date:2018/7/31 21:35
*/

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.starstudio.loser.phrapp.common.AppContext;

import es.dmoral.toasty.Toasty;

public class ToastyUtils {
    @SuppressLint("CheckResult")
    public static void showSuccess(String message) {
        Toasty.success(AppContext.get(), message, Toast.LENGTH_SHORT, true);
    }

    @SuppressLint("CheckResult")
    public static void showError(String message) {
        Toasty.error(AppContext.get(), message, Toast.LENGTH_SHORT, true);
    }

    @SuppressLint("CheckResult")
    public static void showWarning(String message) {
        Toasty.warning(AppContext.get(), message, Toast.LENGTH_SHORT, true);
    }
}
