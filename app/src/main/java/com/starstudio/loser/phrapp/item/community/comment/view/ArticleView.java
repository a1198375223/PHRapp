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
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.makeramen.roundedimageview.RoundedImageView;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.common.utils.DateUtils;
import com.starstudio.loser.phrapp.common.utils.GlideUtils;
import com.starstudio.loser.phrapp.common.utils.KeyBoardUtils;
import com.starstudio.loser.phrapp.common.utils.ShareUtils;
import com.starstudio.loser.phrapp.common.view.PHRShareDialog;
import com.starstudio.loser.phrapp.common.view.PHRSortDialog;
import com.starstudio.loser.phrapp.item.community.comment.ArticleActivity;
import com.starstudio.loser.phrapp.item.community.comment.adpater.CommentRvAdapter;
import com.starstudio.loser.phrapp.item.community.comment.contract.ArticleContract;
import com.starstudio.loser.phrapp.item.community.comment.presenter.ArticleEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

public class ArticleView extends PHRView<ArticleEventListener> implements ArticleContract.ArticleContractView {
    private TextView mTitle;
    private RoundedImageView mIcon;
    private TextView mName;
    private TextView mDescribe;
    private TextView mDate;
    private TextView mBody;
    private CommentRvAdapter mAdapter;
    private int mId = -1;
    private PHRShareDialog mShareDialog;
    private int mSortId = 1;


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
        final TextView text = (TextView) activity.findViewById(R.id.phr_article_fragment_toolbar_type);
        final PHRSortDialog dialog = new PHRSortDialog(getActivity());
        dialog.setListener(new PHRSortDialog.OnItemClickListener() {
            @Override
            public void onItemClickListener(int which) {
                switch (which) {
                    case PHRSortDialog.ITEM_1:
                        mSortId = 1;
                        text.setText("按时间");
                        showProgressDialog();
                        getListener().sortByTime();//降序
                        break;
                    case PHRSortDialog.ITEM_2:
                        mSortId = 2;
                        text.setText("按赞数");
                        showProgressDialog();
                        getListener().sortByLike();
                        break;
                    case PHRSortDialog.ITEM_3:
                        mSortId = 3;
                        text.setText("按新鲜");
                        showProgressDialog();
                        getListener().sortById();
                        break;
                    case PHRSortDialog.ITEM_4:
                        mSortId = 4;
                        text.setText("看楼主");
                        showProgressDialog();
                        getListener().sortByAuthor();
                        break;
                }
            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.showSortDialog();
            }
        });

        mShareDialog = new PHRShareDialog(getActivity());
        mShareDialog.setListener(new PHRShareDialog.OnShareItemClickListener() {
            @Override
            public void onCancelItemClickListener() {
                if (mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
                Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShareItemClickListener(int which, String title, String text) {
                switch (which) {
                    case PHRShareDialog.WEIBO:
                        ShareUtils.shareWeibo(title, "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", text, new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                showSuccessToast("分享成功");
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                showErrorToast("出错啦");
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (mShareDialog.isShowing()) {
                            mShareDialog.dismiss();
                        }
                        break;
                    case PHRShareDialog.QQ:
                        ShareUtils.shareQQ(title, "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", text, new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                showSuccessToast("分享成功");
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                showErrorToast("出错啦");
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (mShareDialog.isShowing()) {
                            mShareDialog.dismiss();
                        }
                        break;
                    case PHRShareDialog.WECHAT:
                        ShareUtils.shareWechat(title, "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", text, new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                showSuccessToast("分享成功");
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                showErrorToast("出错啦");
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (mShareDialog.isShowing()) {
                            mShareDialog.dismiss();
                        }
                        break;
                }
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
                if (mSortId == 4) {
                    getListener().toReplyAuthor(editText.getText().toString(), mId);
                } else {
                    if (((ArticleActivity) getActivity()).isReply) {
                        getListener().saveComment(editText.getText().toString(), mId);
                    } else {
                        getListener().saveComment(editText.getText().toString(), -1);
                    }
                }

                editText.setText("");
                editText.setFocusable(false);
                KeyBoardUtils.closeKeyBoard(editText, getActivity());
                ((ArticleActivity) getActivity()).isReply = false;
            }
        });
        //--------------------------------------------------------------------------------------//

        mAdapter.setListener(new CommentRvAdapter.OnReplyClickListener() {
            @Override
            public void onReplyClickListener(int id, String name) {
                ((ArticleActivity) getActivity()).isReply = true;
                mId = id;
                editText.setHint("引用" + id +"楼 @" + name);
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                KeyBoardUtils.openKeyBoard(editText, getActivity());
            }

            @Override
            public void onShowReplyClickListener(int position) {
                getListener().startShowReplyActivity(position);
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
        dismissProgressDialog();
    }

    @Override
    public void load(List<AVObject> list) {
        mAdapter.setList(list);
        dismissProgressDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showShareDialog(String title, String text) {
        mShareDialog.setTitle(title);
        mShareDialog.setText(text);
        dismissProgressDialog();
        mShareDialog.showShareDialog();
    }
}
