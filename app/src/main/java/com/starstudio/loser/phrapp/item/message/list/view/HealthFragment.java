package com.starstudio.loser.phrapp.item.message.list.view;

/*
    create by:loser
    date:2018/7/25 14:09
*/

import android.content.Intent;
import android.os.Bundle;
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
import com.starstudio.loser.phrapp.item.message.list.presenter.CommonPresenter;
import com.starstudio.loser.phrapp.item.message.list.web.PHRWebActivity;

import static com.avos.avoscloud.AVAnalytics.TAG;

public class HealthFragment extends PHRFragment implements CommonContract.View{
    private RecyclerView mRecyclerView;
    private RvAdapter mAdapter;
    private CommonContract.Presenter mPresenter;
    private BaseBean mBaseBean;

    @Override
    public void initFragment(View view) {
        mPresenter = new CommonPresenter(this);
        mPresenter.setView(this);
        mPresenter.setModel(new HealthModel());
        mPresenter.attach();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.phr_message_fragment_recycler_view);
        mAdapter = new RvAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter.setListener(new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(getActivity(), PHRWebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", mBaseBean.getResult().getData().get(position).getUrl());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void load(BaseBean baseBean) {
        mBaseBean = baseBean;
        mAdapter.setDataList(baseBean);
    }
}
