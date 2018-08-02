package com.starstudio.loser.phrapp.item.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starstudio.loser.phrapp.R;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private List<Record> mRecordList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView RecordData;
        TextView RecordContent;
        TextView HospitalName;
        View RecordView;

        public ViewHolder(View view){
            super(view);
            RecordView = view;
            RecordData = (TextView) view.findViewById(R.id.phr_record_data);
            RecordContent = (TextView) view.findViewById(R.id.phr_record_content);
            HospitalName = (TextView) view.findViewById(R.id.phr_record_hospital_name);
        }
    }

    public RecordAdapter (List<Record> RecordList){
        mRecordList = RecordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phr_manage_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.RecordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Record record = mRecordList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("recordId",record.getRecordId());
                if (record.getType().equals("physical")) {
                    Intent intent = new Intent(parent.getContext(), PhysicalRecordActivity.class);
                    intent.putExtras(bundle);
                    parent.getContext().startActivity(intent);
                }else if (record.getType().equals("clinical")){
                    Intent intent = new Intent(parent.getContext(), ClinicalRecordActivity.class);
                    intent.putExtras(bundle);
                    parent.getContext().startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record record = mRecordList.get(position);
        holder.RecordData.setText(record.getDate());
        holder.RecordContent.setText(record.getContent());
        holder.HospitalName.setText(record.getHospitalName());
    }


    @Override
    public int getItemCount() {
        return mRecordList.size();
    }
}
