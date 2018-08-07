package com.starstudio.loser.phrapp.item.message.list.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.management.RecordAdapter;
import com.starstudio.loser.phrapp.item.message.list.adapter.MentalAdapter;
import com.starstudio.loser.phrapp.item.message.list.adapter.RvAdapter;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MentalFragment extends Fragment {
    private static final String TAG = "MentalFragment";
    private RecyclerView mRecyclerView;
    private MentalAdapter mAdapter;
    private List<UsefulData> mData = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_fragment_layout,container,false);
        initData();
        try{
            Thread.sleep(1200);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        initView(view);
        return view;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetNews();
            }
        }).start();
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.phr_message_fragment_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MentalAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void GetNews() {
        String url="http://47.254.30.49:6699";
        //List<UsefulData> datas = new ArrayList<>();
        try{
            OkHttpClient client=new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
            Request request=new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Response response=client.newCall(request).execute();
            String responseData=response.body().string();
            //Log.d(TAG, "新闻信息列表 "+responseData);
            Gson gson=new Gson();
            mData=gson.fromJson(responseData,new TypeToken<List<UsefulData>>(){}.getType());
//            datas=gson.fromJson(responseData,new TypeToken<List<UsefulData>>(){}.getType());
//            for (UsefulData i:datas){
//                Log.d(TAG, "GetNews: title:"+i.getTitle());
//                Log.d(TAG, "GetNews: url:"+i.getUrl());
//                Log.d(TAG, "GetNews: img_url:"+i.getImage1());
//            }
            //return datas;
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "GetNews: 获取新闻信息失败！");
            //return datas;
        }
    }

//    @Override
//    public void onResume() {
//        mData.clear();
//        mData.addAll(GetNews());
//        super.onResume();
//    }

}
