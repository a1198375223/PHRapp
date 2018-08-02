package com.starstudio.loser.phrapp.item.community.fragment.view;

/*
    create by:loser
    date:2018/7/31 14:31
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.base.BaseFragment;
import com.starstudio.loser.phrapp.item.community.CommunityActivity;
import com.starstudio.loser.phrapp.item.community.callback.AFCallback;
import com.starstudio.loser.phrapp.item.community.callback.MessageEventBus;
import com.starstudio.loser.phrapp.item.community.fragment.contract.WriteFragmentContract;
import com.starstudio.loser.phrapp.item.community.fragment.model.WriteFragmentModel;
import com.starstudio.loser.phrapp.item.community.fragment.presenter.WriteFragmentEventListener;
import com.starstudio.loser.phrapp.item.community.fragment.presenter.WriteFragmentPresenter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

public class WriteFragment extends BaseFragment<WriteFragmentEventListener> implements WriteFragmentContract.WriteView{
    private WriteFragmentContract.WritePresenter mPresenter;
    private EditText mTitle;
    private EditText mText;
    private MaterialDialog mPostDialog;
    private AFCallback mCallback;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_community_post_message_layout, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.phr_community_post_message_toolbar);
        ((RxAppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((RxAppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        setHasOptionsMenu(true);
        mTitle = (EditText) view.findViewById(R.id.phr_community_post_message_title);
        mText = (EditText) view.findViewById(R.id.phr_community_post_message_text);
        mPresenter = new WriteFragmentPresenter(this);
        mPresenter.setModel(new WriteFragmentModel(mPresenter));
        mPresenter.setView(this);
        mPresenter.attach();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (AFCallback) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.phr_community_post_message_menu, menu);
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.phr_community_post_message_goon:
                if (!mTitle.getText().toString().equals("") && !mText.getText().toString().equals("")){
                    mPostDialog = new MaterialDialog.Builder(Objects.requireNonNull(getActivity()))
                            .title(mTitle.getText().toString())
                            .content(mText.getText().toString())
                            .titleColor(getResources().getColor(R.color.color_text_title, null))
                            .contentColor(getResources().getColor(R.color.color_text_sub_title, null))
                            .positiveText(R.string.phr_write_positive)
                            .negativeText(R.string.phr_write_negative)
                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    switch (which) {
                                        case NEGATIVE:
                                            dialog.dismiss();
                                            showErrorToast("取消发布");
                                            break;
                                        case POSITIVE:
                                            showProgressDialog();
                                            getListener().post(mTitle.getText().toString(), mText.getText().toString());
                                            break;
                                        default:
                                    }
                                }
                            })
                            .canceledOnTouchOutside(true)
                            .show();
                } else if (mTitle.getText().toString().equals("")){
                    showErrorToast("标题不能为空");
                } else if (mText.getText().toString().equals("")) {
                    showErrorToast("内容不能为空");
                }

                break;
            default:
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void destroySelf() {
        dismissProgressDialog();
        showSuccessToast("发布成功");
        if (mCallback != null) {
            mCallback.tellToRefresh();
        }
        //销毁自己
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showErrorDialog() {
        dismissProgressDialog();

        if (mPostDialog != null && mPostDialog.isShowing()) {
            mPostDialog.dismiss();
        }

        new MaterialDialog.Builder(Objects.requireNonNull(getActivity()))
                .canceledOnTouchOutside(false)
                .titleColor(Color.RED)
                .contentColor(Color.GRAY)
                .title(R.string.phr_write_error_title)
                .content(R.string.phr_write_error_content)
                .positiveText(R.string.phr_write_error_ok)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        switch (which) {
                            case POSITIVE:
                                dialog.dismiss();
                                break;
                            default:
                        }
                    }
                })
                .show();
    }
}
