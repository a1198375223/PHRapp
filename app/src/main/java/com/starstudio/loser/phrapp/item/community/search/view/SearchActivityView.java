package com.starstudio.loser.phrapp.item.community.search.view;

/*
    create by:loser
    date:2018/8/9 13:33
*/

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.item.community.search.adapter.HistoryRvAdapter;
import com.starstudio.loser.phrapp.item.community.search.adapter.ResultRvAdapter;
import com.starstudio.loser.phrapp.item.community.search.contract.SearchContract;
import com.starstudio.loser.phrapp.item.community.search.fragment.view.SearchFrameFragment;
import com.starstudio.loser.phrapp.item.community.search.presenter.SearchEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivityView extends PHRView<SearchEventListener> implements SearchContract.SearchContractView {
    private HistoryRvAdapter mHistoryAdapter;
    private ResultRvAdapter mResultAdapter;
    private TextView mNone;
    private RelativeLayout mHistoryContainer, mResultContainer, mSearchFrameContainer;
    private SearchFrameFragment mFragment;
    private TextView mResultCount;
    private int size;
    private List<String> mHistory = new ArrayList<>();
    private List<AVObject> mSearch = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public SearchActivityView(Activity activity) {
        super(activity);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.phr_activity_search_toolbar);
        ((PHRActivity) getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((PHRActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ImageView imageView = (ImageView) activity.findViewById(R.id.phr_activity_search_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        final SearchView searchView = (SearchView) activity.findViewById(R.id.phr_activity_search_search_view);
        //开始处于展开状态
        searchView.setIconified(false);
        //设置无法收起searchView, 就是当没有输入的时候关闭 X 图标
        searchView.onActionViewExpanded();
        //监听器


        //case 1:历史记录
        mHistoryContainer = (RelativeLayout) activity.findViewById(R.id.phr_activity_search_history_container);
        LinearLayout clear = (LinearLayout) activity.findViewById(R.id.phr_activity_search_clear);
        RecyclerView history = (RecyclerView) activity.findViewById(R.id.phr_activity_search_history_recycler_view);
        history.setHasFixedSize(true);
        history.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHistoryAdapter = new HistoryRvAdapter(getActivity());
        mHistoryAdapter.setListener(new HistoryRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                //隐藏历史记录
                if (mHistoryContainer.getVisibility() == View.VISIBLE) {
                    mHistoryContainer.setVisibility(View.INVISIBLE);
                }
                //改变search的text,显示结果
                if (mHistory.get(position) != null) {
                    searchView.setQuery(mHistory.get(position), true);
                }
            }
        });
        history.setAdapter(mHistoryAdapter);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().clearHistory();
            }
        });

        //case 2:搜索过程
        mSearchFrameContainer = (RelativeLayout) activity.findViewById(R.id.phr_activity_search_frame_container);
        FragmentTransaction ft = ((PHRActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        mFragment = new SearchFrameFragment();
        mFragment.setListener(new SearchFrameFragment.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                //搜索框点击事件
                //显示结果,改变search的text
                if (mSearch.get(position) != null) {
                    searchView.setQuery(mSearch.get(position).getString("title"), true);
                    //getListener().toFindAndSave(mSearch.get(position).getString("title"));
                }
            }
        });
        ft.add(R.id.phr_activity_search_frame_container, mFragment);
        ft.commit();

        //case 3:搜索结果
        mResultContainer = (RelativeLayout) activity.findViewById(R.id.phr_activity_search_result_container);
        mResultCount = (TextView) activity.findViewById(R.id.phr_activity_search_result);
        RecyclerView result = (RecyclerView) activity.findViewById(R.id.phr_activity_search_result_recycler_view);
        result.setHasFixedSize(true);
        result.setLayoutManager(new LinearLayoutManager(getActivity()));
        mResultAdapter = new ResultRvAdapter(getActivity());
        mResultAdapter.setListener(new ResultRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                getListener().startArticleActivity(position);
            }
        });
        result.setAdapter(mResultAdapter);

        //case 4:搜索无结果
        mNone = (TextView) activity.findViewById(R.id.phr_activity_search_none);

        //search监听事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //搜索按钮点击后事件
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    mHistory.add(query);
                    //如果结果已经显示，就不进行搜索，避免重复搜索
                    if (mResultContainer.getVisibility() != View.VISIBLE || mNone.getVisibility() == View.VISIBLE) {
                        //显示加载条
                        showProgressDialog();
                        getListener().toFindAndSave(query);
                    }
                }
                return true;
            }

            //文字改变时的事件
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    //隐藏历史记录
                    if (mHistoryContainer.getVisibility() == View.VISIBLE) {
                        mHistoryContainer.setVisibility(View.INVISIBLE);
                    }
                    //时时更新
                    getListener().toFind(newText);
                } else {
                    //字符串空的时候显示历史记录，隐藏搜索框, 如果显示了结果，就不现实历史记录, 如果没有历史记录也不显示
                    if (mHistoryContainer.getVisibility() == View.INVISIBLE && size > 0 &&
                            mResultContainer.getVisibility() == View.INVISIBLE  && mNone.getVisibility() == View.INVISIBLE) {
                        getListener().toFindHistory();
                        mHistoryContainer.setVisibility(View.VISIBLE);
                    }
                    if (mSearchFrameContainer.getVisibility() == View.VISIBLE) {
                        mSearchFrameContainer.setVisibility(View.INVISIBLE);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void showHistory(List<String> history) {
        //保存历史记录消息
        this.mHistory = history;
        this.size = history.size();
        if (history.size() > 0) {
            //显示历史记录
            mHistoryContainer.setVisibility(View.VISIBLE);
            mHistoryAdapter.setList(history);
        } else {
            //隐藏历史记录
            if (mHistoryContainer.getVisibility() == View.VISIBLE) {
                mHistoryContainer.setVisibility(View.INVISIBLE);
                mHistoryAdapter.setList(history);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showResult(List<AVObject> list) {
        size++;
        if (list.size() == 0) {
            //显示无结果
            mNone.setVisibility(View.VISIBLE);
        } else {
            //关闭搜索框
            if (mSearchFrameContainer.getVisibility() == View.VISIBLE) {
                mSearchFrameContainer.setVisibility(View.INVISIBLE);
            }
            //显示结果
            mResultCount.setText(list.size() + "条结果");
            mResultAdapter.setList(list);
            mResultContainer.setVisibility(View.VISIBLE);
        }
        dismissProgressDialog();
    }

    @Override
    public void showSearch(List<AVObject> list) {
        //保存模糊查找的结果
        mSearch = list;
        if (list.size() > 0) {
            //显示搜索框
            //隐藏结果
            if (mResultContainer.getVisibility() == View.VISIBLE) {
                mResultContainer.setVisibility(View.INVISIBLE);
            }
            if (mNone.getVisibility() == View.VISIBLE) {
                mNone.setVisibility(View.INVISIBLE);
            }
            mFragment.setData(list);
            mSearchFrameContainer.setVisibility(View.VISIBLE);
        } else {
            //隐藏搜索框
            mFragment.setData(new ArrayList<AVObject>());
            mSearchFrameContainer.setVisibility(View.INVISIBLE);
        }
    }
}
