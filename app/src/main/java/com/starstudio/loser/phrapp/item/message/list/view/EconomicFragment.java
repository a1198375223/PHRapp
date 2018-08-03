package com.starstudio.loser.phrapp.item.message.list.view;

/*
    create by:loser
    date:2018/7/25 14:10
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.item.message.list.adapter.RvAdapter;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.EconomicModel;
import com.starstudio.loser.phrapp.item.message.list.model.HealthModel;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;
import com.starstudio.loser.phrapp.item.message.list.presenter.CommonPresenter;
import com.starstudio.loser.phrapp.item.message.list.presenter.FragmentEventListener;
import com.starstudio.loser.phrapp.item.message.list.web.PHRWebActivity;

import java.util.List;

public class EconomicFragment extends PHRFragment<FragmentEventListener> implements CommonContract.View{
    private RecyclerView mRecyclerView;
    private RvAdapter mAdapter;
    private CommonContract.Presenter mPresenter;
    private List<UsefulData> mData;

    @Override
    public void initFragment(View view) {
        mPresenter = new CommonPresenter(this);
        mPresenter.setModel(new EconomicModel(mPresenter));
        mPresenter.setView(this);
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
