package com.starstudio.loser.phrapp.item.community.comment.view;

/*
    create by:loser
    date:2018/8/5 23:39
*/

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.makeramen.roundedimageview.RoundedImageView;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.common.utils.DateUtils;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;
import com.starstudio.loser.phrapp.common.utils.KeyBoardUtils;
import com.starstudio.loser.phrapp.item.community.comment.adpater.CommentRvAdapter;
import com.starstudio.loser.phrapp.item.community.comment.contract.ArticleContract;
import com.starstudio.loser.phrapp.item.community.comment.presenter.ArticleEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

public class ArticleView extends PHRView<ArticleEventListener> implements ArticleContract.ArticleContractView {
    private TextView mTitle;
    private RoundedImageView mIcon;
    private TextView mName;
    private TextView mDescribe;
    private TextView mDate;
    private TextView mBody;
    private CommentRvAdapter mAdapter;


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArticleView(Activity activity) {
        super(activity);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.phr_article_fragment_toolbar);
        ((PHRActivity) getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((PHRActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mTitle = (TextView) activity.findViewById(R.id.phr_article_fragment_title);
        mIcon = (RoundedImageView) activity.findViewById(R.id.phr_article_fragment_icon);
        mName = (TextView) activity.findViewById(R.id.phr_article_fragment_name);
        mDescribe = (TextView) activity.findViewById(R.id.phr_article_fragment_describe);
        mDate = (TextView) activity.findViewById(R.id.phr_article_fragment_date);
        mBody = (TextView) activity.findViewById(R.id.phr_article_fragment_body_text);
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.phr_article_fragment_comment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommentRvAdapter(getActivity());


        //init Bottom EditView and Button
        final EditText editText = (EditText) activity.findViewById(R.id.phr_article_fragment_edit);
        final Button button = (Button) activity.findViewById(R.id.phr_article_fragment_submit);
        button.setEnabled(false);
        button.setTextColor(getActivity().getResources().getColor(R.color.color_text_hint));
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                KeyBoardUtils.openKeyBoard(editText, getActivity());
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("") && !button.isEnabled()) {
                    button.setEnabled(true);
                    button.setTextColor(getActivity().getResources().getColor(R.color.color_text_sub_title));
                } else if (s.toString().equals("") && button.isEnabled()) {
                    button.setEnabled(false);
                    button.setTextColor(getActivity().getResources().getColor(R.color.color_text_hint));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                getListener().saveComment(editText.getText().toString());
                editText.setText("");
                editText.setFocusable(false);
                KeyBoardUtils.closeKeyBoard(editText, getActivity());
                getListener().loadComment();
                dismissProgressDialog();
            }
        });
        //--------------------------------------------------------------------------------------//

        mAdapter.setListener(new CommentRvAdapter.OnReplyClickListener() {
            @Override
            public void onReplyClickListener(int id, String name) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                KeyBoardUtils.openKeyBoard(editText, getActivity());
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setData(AVObject data, List<AVObject> comment) {
        AVUser avUser = data.getAVUser("article_user");
        mTitle.setText(data.getString("title"));
        mBody.setText(data.getString("text"));
        GlideUtils.loadRoundImage(getActivity(), avUser.getAVFile("head_img").getUrl(), mIcon);
        mName.setText(avUser.getUsername());
        mDescribe.setText(data.getString("describe"));
        mDate.setText(DateUtils.parseDate(data.getDate("time")));
        if (comment != null) {
            mAdapter.setList(comment);
        } else {
            mAdapter.setList(new ArrayList<AVObject>());
        }
    }

    @Override
    public void showError() {
        showErrorToast("请先登入");
    }

    @Override
    public void load(List<AVObject> list) {
        if (list == null) {
            mAdapter.setList(new ArrayList<AVObject>());
        }
        mAdapter.setList(list);
    }
}
