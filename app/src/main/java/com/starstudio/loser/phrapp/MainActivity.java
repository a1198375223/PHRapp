package com.starstudio.loser.phrapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.starstudio.loser.phrapp.common.view.PHRProgressDialog;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_main_layout);

        PHRProgressDialog dialog = new PHRProgressDialog(this);
        dialog.showProgressDialog();
    }
}
