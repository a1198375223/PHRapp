package com.starstudio.loser.phrapp.item.community.comment;

/*
    create by:loser
    date:2018/8/5 23:37
*/

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.utils.EventBusUtils;
import com.starstudio.loser.phrapp.common.utils.KeyBoardUtils;
import com.starstudio.loser.phrapp.common.view.PHRMoreDialog;
import com.starstudio.loser.phrapp.item.community.callback.MessageEventBus;
import com.starstudio.loser.phrapp.item.community.comment.contract.ArticleContract;
import com.starstudio.loser.phrapp.item.community.comment.model.ArticleModel;
import com.starstudio.loser.phrapp.item.community.comment.presenter.ArticleViewPresenter;
import com.starstudio.loser.phrapp.item.community.comment.view.ArticleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phr_article_activity_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.phr_article_menu_more:
                final PHRMoreDialog dialog = new PHRMoreDialog(this);
                dialog.setListener(new PHRMoreDialog.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        switch (position) {
                            case 0:
                                dialog.dismiss();
                                mPresenter.toCollect();
                                break;
                            case 1:
                                dialog.dismiss();
                                mPresenter.toShare();
                                break;
                            case 2:
                                dialog.dismiss();
                                mPresenter.toComplaints();
                                break;
                            case 3:
                                dialog.dismiss();
                                mPresenter.toLike();
                                break;
                            case 4:
                                dialog.dismiss();
                                mPresenter.toDislike();
                                break;
                        }
                    }
                });
                dialog.showShareDialog();
                break;
        }
        return true;
    }
}
