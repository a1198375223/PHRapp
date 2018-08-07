package com.starstudio.loser.phrapp.item.community.child.view;

/*
    create by:loser
    date:2018/7/31 20:08
*/

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.avos.avoscloud.AVObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.item.community.callback.AFCallback;
import com.starstudio.loser.phrapp.item.community.child.adapter.CommunityRvAdapter;
import com.starstudio.loser.phrapp.item.community.child.contract.DynamicContract;
import com.starstudio.loser.phrapp.item.community.child.presenter.DynamicEventListener;

import java.util.List;

public class DynamicView extends PHRView<DynamicEventListener> implements DynamicContract.DynamicChildView , AFCallback{
    private CommunityRvAdapter mAdapter;
    private SmartRefreshLayout mLayout;
    private static final String TAG = "DynamicView";
    private View mRootView;

    public DynamicView(Activity activity) {
        super(activity);
        mRootView = LayoutInflater.from(activity).inflate(R.layout.phr_community_rv_layout, null);
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.phr_community_recycler_view);
        mLayout = (SmartRefreshLayout) mRootView.findViewById(R.id.phr_community_refresh_layout);

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

        mAdapter = new CommunityRvAdapter(activity);
        mAdapter.setListener(new CommunityRvAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClickListener(int position) {
                getListener().startArticleActivity(position);
            }

            @Override
            public void onMenuItemClickListener(int position, int index) {
                switch (index) {
                    case 0://分享
                        showProgressDialog();
                        getListener().toShare(position);
                        break;
                    case 1://转发
                        showProgressDialog();
                        getListener().toTransfer(position);
                        break;
                    case 2://举报
                        showProgressDialog();
                        getListener().toComplaints(position);
                        break;
                    case 3://收藏
                        showProgressDialog();
                        getListener().toCollect(position);
                        break;
                    default:
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }


    @Override
    public void setData(List<AVObject> list) {
        mAdapter.setList(list);
        mLayout.finishRefresh();
        dismissProgressDialog();
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

    @Override
    public void success(String success) {
        showSuccessToast(success);
        dismissProgressDialog();
    }

    @Override
    public void error(String error) {
        showErrorToast(error);
        dismissProgressDialog();
    }

    @Override
    public void warning(String warning) {
        showWarningToast(warning);
        dismissProgressDialog();
    }

    @Override
    public View getView() {
        return mRootView;
    }
}
