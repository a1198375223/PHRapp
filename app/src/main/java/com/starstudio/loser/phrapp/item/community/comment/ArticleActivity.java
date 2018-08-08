package com.starstudio.loser.phrapp.item.community.comment;

/*
    create by:loser
    date:2018/8/5 23:37
*/

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.utils.EventBusUtils;
import com.starstudio.loser.phrapp.common.utils.KeyBoardUtils;
import com.starstudio.loser.phrapp.item.community.callback.MessageEventBus;
import com.starstudio.loser.phrapp.item.community.comment.contract.ArticleContract;
import com.starstudio.loser.phrapp.item.community.comment.model.ArticleModel;
import com.starstudio.loser.phrapp.item.community.comment.presenter.ArticleViewPresenter;
import com.starstudio.loser.phrapp.item.community.comment.view.ArticleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ArticleActivity extends PHRActivity {
    private ArticleContract.ArticleContractPresenter mPresenter;
    public boolean isReply = false;
    private static final String TAG = "ArticleActivity";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_article_fragment_layout);
        EventBusUtils.register(this);
        mPresenter = new ArticleViewPresenter(getActivity());
        mPresenter.setView(new ArticleView(getActivity()));
        mPresenter.setModel(new ArticleModel(mPresenter));
        mPresenter.attach();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(MessageEventBus eventBus) {
        if (eventBus.isTodo()) {
            mPresenter.toRefresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
        EventBusUtils.unregister(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardUtils.shouldHideKeyBoard(v, ev)) {
                v.setFocusable(false);
                ((EditText)v).setHint("写回复");
                KeyBoardUtils.closeKeyBoard((EditText)v, this);
                isReply = false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
