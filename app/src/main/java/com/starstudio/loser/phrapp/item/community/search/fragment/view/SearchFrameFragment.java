package com.starstudio.loser.phrapp.item.community.search.fragment.view;

/*
    create by:loser
    date:2018/8/10 8:13
*/

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
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.base.BaseFragment;
import com.starstudio.loser.phrapp.item.community.search.fragment.adapter.SearchFrameRvAdapter;
import com.starstudio.loser.phrapp.item.community.search.fragment.contract.SearchFrameContract;
import com.starstudio.loser.phrapp.item.community.search.fragment.model.SearchFrameModel;
import com.starstudio.loser.phrapp.item.community.search.fragment.presenter.SearchFrameEventListener;
import com.starstudio.loser.phrapp.item.community.search.fragment.presenter.SearchFramePresenter;

import java.util.List;
import java.util.Objects;

public class SearchFrameFragment extends BaseFragment<SearchFrameEventListener> implements SearchFrameContract.SearchFrameContractView {
    private SearchFrameRvAdapter mAdapter;
    private SearchFrameContract.SearchFrameContractPresenter mPresenter;
    private OnItemClickListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_fragment_search_frame, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.phr_fragment_search_frame_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SearchFrameRvAdapter(getContext());
        mAdapter.setListener(new SearchFrameRvAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClickListener(int position) {
                if (mListener != null) {
                    mListener.onItemClickListener(position);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        mPresenter = new SearchFramePresenter(this);
        mPresenter.setView(this);
        mPresenter.setModel(new SearchFrameModel());
        mPresenter.attach();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void setData(List<AVObject> list) {
        mAdapter.setList(list);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void destroySelf() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }
}
