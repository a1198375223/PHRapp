package com.starstudio.loser.phrapp.item.message.list.view;

/*
    create by:loser
    date:2018/7/24 9:53
*/

import android.support.v7.widget.RecyclerView;

import com.starstudio.loser.phrapp.common.base.PHRFragment;
import com.starstudio.loser.phrapp.item.message.list.adapter.RvAdapter;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;

public class HealthFragment extends PHRFragment implements CommonContract.View{
    private RvAdapter mAdapter;

    @Override
    public void load(BaseBean baseBean) {
        mAdapter = new RvAdapter();
        initRecyclerView();
    }

    @Override
    public void initRecyclerView() {
        getRecyclerView().setAdapter(mAdapter);
    }


}
