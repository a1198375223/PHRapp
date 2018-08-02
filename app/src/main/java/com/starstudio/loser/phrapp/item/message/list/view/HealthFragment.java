package com.starstudio.loser.phrapp.item.message.list.view;

/*
    create by:loser
    date:2018/7/25 14:09
*/

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.item.message.list.adapter.RvAdapter;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.HealthModel;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;
import com.starstudio.loser.phrapp.item.message.list.presenter.CommonPresenter;
import com.starstudio.loser.phrapp.item.message.list.presenter.FragmentEventListener;
import com.starstudio.loser.phrapp.item.message.list.presenter.HealthPresenter;
import com.starstudio.loser.phrapp.item.message.list.web.PHRWebActivity;

import java.util.List;

import static com.avos.avoscloud.AVAnalytics.TAG;

public class HealthFragment extends PHRFragment<FragmentEventListener> implements CommonContract.View{
    private RecyclerView mRecyclerView;
    private RvAdapter mAdapter;
    private CommonContract.SimplePresenter mPresenter;
    private List<UsefulData> mData;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initFragment(View view) {
        mPresenter = new HealthPresenter(this);
        mPresenter.setView(this);
        mPresenter.setModel(new HealthModel(mPresenter));
        mPresenter.attach();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.phr_message_fragment_recycler_view);
        mAdapter = new RvAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter.setListener(new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                getListener().startWebActivity(mData.get(position).getUrl());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void loadRecyclerView(List<UsefulData> list) {
        mData = list;
        mAdapter.setDataList(list);
    }
}
