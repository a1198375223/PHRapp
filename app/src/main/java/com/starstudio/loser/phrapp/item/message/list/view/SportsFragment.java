package com.starstudio.loser.phrapp.item.message.list.view;

/*
    create by:loser
    date:2018/7/25 14:11
*/

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.item.message.list.adapter.RvAdapter;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.HealthModel;
import com.starstudio.loser.phrapp.item.message.list.model.SportsModel;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.presenter.CommonPresenter;

public class SportsFragment extends PHRFragment implements CommonContract.View{
    private RecyclerView mRecyclerView;
    private RvAdapter mAdapter;
    private CommonContract.Presenter mPresenter;

    @Override
    public void initFragment(View view) {
        mPresenter = new CommonPresenter(getParentFragment());
        mPresenter.setModel(new SportsModel());
        mPresenter.setView(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.phr_message_fragment_recycler_view);
        mAdapter = new RvAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter.setListener(new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void load(BaseBean baseBean) {
        mAdapter.setDataList(baseBean);
    }
}
