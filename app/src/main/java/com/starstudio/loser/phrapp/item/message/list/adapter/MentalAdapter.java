package com.starstudio.loser.phrapp.item.message.list.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.starstudio.loser.phrapp.item.management.RecordAdapter;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;

import java.util.List;

public class MentalAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private List<UsefulData> mRecordList;

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
