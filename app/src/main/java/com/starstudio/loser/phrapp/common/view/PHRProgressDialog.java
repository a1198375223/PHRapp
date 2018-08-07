package com.starstudio.loser.phrapp.common.view;

/*
    create by:loser
    date:2018/7/25 14:47
*/

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;

import com.starstudio.loser.phrapp.R;

import java.util.Objects;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

public class PHRProgressDialog extends Dialog {

    public PHRProgressDialog(@NonNull Context context) {
        super(context, R.style.PHR_ProgressDialog);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_progress_dialog);
        Objects.requireNonNull(getWindow()).getAttributes().gravity = Gravity.CENTER;
    }

    public void showProgressDialog(){
        if (!isShowing()) {
            setCanceledOnTouchOutside(false);
            setCancelable(true);
            show();
        }
    }
}
