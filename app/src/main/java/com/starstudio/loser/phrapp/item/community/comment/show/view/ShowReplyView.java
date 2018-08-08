package com.starstudio.loser.phrapp.item.community.comment.show.view;

/*
    create by:loser
    date:2018/8/7 17:05
*/

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.common.utils.EventBusUtils;
import com.starstudio.loser.phrapp.common.utils.KeyBoardUtils;
import com.starstudio.loser.phrapp.item.community.callback.MessageEventBus;
import com.starstudio.loser.phrapp.item.community.comment.ArticleActivity;
import com.starstudio.loser.phrapp.item.community.comment.adpater.CommentRvAdapter;
import com.starstudio.loser.phrapp.item.community.comment.show.ShowReplyActivity;
import com.starstudio.loser.phrapp.item.community.comment.show.adapter.ShowReplyRvAdapter;
import com.starstudio.loser.phrapp.item.community.comment.show.contract.ShowReplyContract;
import com.starstudio.loser.phrapp.item.community.comment.show.presenter.ShowReplyEventListener;

import java.util.List;
import java.util.Objects;

public class ShowReplyView extends PHRView<ShowReplyEventListener> implements ShowReplyContract.ShowReplyContractView {
    private ShowReplyRvAdapter mAdapter;
    private int mId;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ShowReplyView(Activity activity) {
        super(activity);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.phr_activity_show_reply_toolbar);
        ((PHRActivity)getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((PHRActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.post(new MessageEventBus(true));
                getActivity().finish();
            }
        });


        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.phr_activity_show_reply_recycler_view);
        final EditText editText = (EditText) activity.findViewById(R.id.phr_activity_show_reply_edit);
        final Button button = (Button) activity.findViewById(R.id.phr_activity_show_reply_submit);
        //容器
        final RelativeLayout container = (RelativeLayout) activity.findViewById(R.id.show_reply_container);
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
                getListener().saveComment(editText.getText().toString(), mId);
                editText.setText("");
                editText.setFocusable(false);
                KeyBoardUtils.closeKeyBoard(editText, getActivity());
                container.setVisibility(View.INVISIBLE);
            }
        });
        //--------------------------------------------------------------------------------------//

        mAdapter = new ShowReplyRvAdapter(activity);
        mAdapter.setListener(new ShowReplyRvAdapter.OnReplyClickListener() {
            @Override
            public void onReplyClickListener(int id, String name, int position) {
                container.setVisibility(View.VISIBLE);
                mId = position;
                editText.setHint("引用" + id +"楼 @" + name);
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                KeyBoardUtils.openKeyBoard(editText, getActivity());
            }
        });
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setData(List<AVObject> list) {
        mAdapter.setList(list);
        dismissProgressDialog();
    }
}
