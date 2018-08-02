package com.starstudio.loser.phrapp.item.management;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.R;

import java.util.ArrayList;
import java.util.List;

public class ManageFragment extends Fragment {
    private static final String TAG = "ManageFragment";
    private Context context;
    private List<Record> recordList = new ArrayList<>();
    private RecordAdapter adapter;
    private String type;

    @Override
    public void onAttach(Context context) {
        type = getArguments().getString("type");
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_manage_fragment_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.phr_manage_fragment_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecordAdapter(recordList);
        recyclerView.setAdapter(adapter);
    }

    private void initRecord() {
        AVQuery<AVObject> query = new AVQuery<>("Record");
        query.whereEqualTo("owner", AVUser.getCurrentUser().getObjectId());
        query.whereEqualTo("type",type);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    for (AVObject avRecord : list){
                        Log.d(TAG, "done: ***************记录的类型" + type);
                        Record record = new Record(avRecord);
                        recordList.add(record);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Log.d(TAG, "done: *****************查询失败！");
                }
            }
        });
    }

    @Override
    public void onResume() {
        recordList.clear();
        initRecord();
        super.onResume();
    }

    public static ManageFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString("type",type);
        ManageFragment fragment = new ManageFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
