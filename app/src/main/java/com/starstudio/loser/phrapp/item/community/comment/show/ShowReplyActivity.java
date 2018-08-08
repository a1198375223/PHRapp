package com.starstudio.loser.phrapp.item.community.comment.show;

/*
    create by:loser
    date:2018/8/7 17:04
*/

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.utils.EventBusUtils;
import com.starstudio.loser.phrapp.common.utils.KeyBoardUtils;
import com.starstudio.loser.phrapp.item.community.callback.MessageEventBus;
import com.starstudio.loser.phrapp.item.community.comment.ArticleActivity;
import com.starstudio.loser.phrapp.item.community.comment.show.contract.ShowReplyContract;
import com.starstudio.loser.phrapp.item.community.comment.show.model.ShowReplyModel;
import com.starstudio.loser.phrapp.item.community.comment.show.presenter.ShowReplyPresenter;
import com.starstudio.loser.phrapp.item.community.comment.show.view.ShowReplyView;

public class ShowReplyActivity extends PHRActivity {
    private ShowReplyContract.ShowReplyContractPresenter mPresenter;
    private RelativeLayout mContainer;
    private static final String TAG = "ShowReplyActivity";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_show_reply);

        mContainer = (RelativeLayout) findViewById(R.id.show_reply_container);

        mPresenter = new ShowReplyPresenter(this);
        mPresenter.setView(new ShowReplyView(this));
        mPresenter.setModel(new ShowReplyModel(mPresenter));
        mPresenter.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardUtils.shouldHideKeyBoard(v, ev)) {
                v.setFocusable(false);
                ((EditText)v).setHint("写回复");
                KeyBoardUtils.closeKeyBoard((EditText)v, this);
                mContainer.setVisibility(View.INVISIBLE);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBusUtils.post(new MessageEventBus(true));
        finish();
    }
}
