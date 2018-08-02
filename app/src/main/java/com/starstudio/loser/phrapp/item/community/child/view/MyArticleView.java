package com.starstudio.loser.phrapp.item.community.child.view;

/*
    create by:loser
    date:2018/8/1 20:05
*/

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.base.BaseFragment;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.item.community.child.adapter.CommunityRvAdapter;
import com.starstudio.loser.phrapp.item.community.child.contract.MyArticleContract;
import com.starstudio.loser.phrapp.item.community.child.model.MyArticleModel;
import com.starstudio.loser.phrapp.item.community.child.presenter.ChildEventListener;
import com.starstudio.loser.phrapp.item.community.child.presenter.MyArticlePresenter;

import java.util.List;

public class MyArticleView extends BaseFragment<ChildEventListener> implements MyArticleContract.MyArticleContractView {
    private CommunityRvAdapter mAdapter;
    private SmartRefreshLayout mLayout;
    private MyArticleContract.MyArticleContractPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_my_article_rv_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.phr_my_article_recycler_view);
        mLayout = (SmartRefreshLayout) view.findViewById(R.id.phr_my_article_smart_refresh);
        mLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                if (success) {
                    showSuccessToast("刷新成功");
                } else {
                    showErrorToast("刷新失败");
                }
            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
                if (success) {
                    showSuccessToast("加载成功");
                } else {
                    showErrorToast("加载失败");
                }
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getListener().toLoadMore();
                refreshLayout.finishLoadMore(5000, false, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getListener().toRefresh();
                refreshLayout.finishRefresh(5000, false);
            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

            }
        });

//        FloatingActionButton floatingActionButton = (FloatingActionButton) mRootView.findViewById(R.id.phr_my_article_floating_button);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FragmentTransaction ft = ((CommunityActivity)activity).getSupportFragmentManager().beginTransaction();
////                WriteFragment fragment = new WriteFragment();
////                ft.add(R.id.phr_my_article_rv_container, fragment);
////                ft.commit();
//                getListener().startFragment();
//            }
//        });

        mAdapter = new CommunityRvAdapter(getContext());
        mAdapter.setListener(new CommunityRvAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClickListener(int position) {
                showSuccessToast("click no:" + position);
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        mPresenter = new MyArticlePresenter(this);
        mPresenter.setView(this);
        mPresenter.setModel(new MyArticleModel(mPresenter));
        mPresenter.attach();
        return view;
    }

    @Override
    public void setData(List<AVObject> list) {
        mAdapter.setList(list);
        mLayout.finishRefresh();
    }

    @Override
    public void load(List<AVObject> list, boolean hasMore) {
        mAdapter.addList(list);
        mLayout.finishLoadMore(0, true, !hasMore);
    }

    @Override
    public void tellToRefresh() {
        getListener().toRefresh();
    }
}
