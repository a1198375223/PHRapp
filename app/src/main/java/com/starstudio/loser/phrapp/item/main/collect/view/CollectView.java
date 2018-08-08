package com.starstudio.loser.phrapp.item.main.collect.view;

/*
    create by:loser
    date:2018/8/8 14:48
*/

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.item.main.collect.adapter.CollectRvAdapter;
import com.starstudio.loser.phrapp.item.main.collect.contract.CollectContract;
import com.starstudio.loser.phrapp.item.main.collect.presenter.CollectEventListener;

import java.util.List;
import java.util.Objects;

public class CollectView extends PHRView<CollectEventListener> implements CollectContract.CollectContractView {
    private CollectRvAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CollectView(Activity activity) {
        super(activity);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.phr_activity_collect_toolbar);
        ((PHRActivity) getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((PHRActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.phr_activity_collect_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CollectRvAdapter(getActivity());
        mAdapter.setListener(new CollectRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                getListener().startArticleActivity(position);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setData(List<AVObject> list) {
        mAdapter.setList(list);
        dismissProgressDialog();
    }
}
