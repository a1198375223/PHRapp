package com.starstudio.loser.phrapp.item.message.list.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.management.RecordAdapter;
import com.starstudio.loser.phrapp.item.message.list.adapter.RvAdapter;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;

import java.util.List;

public class MentalFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RvAdapter mAdapter;
    private List<UsefulData> mData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_fragment_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }

}
