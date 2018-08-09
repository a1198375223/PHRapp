package com.starstudio.loser.phrapp.item.medicine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starstudio.loser.phrapp.R;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private List<Medicine> mMedicineList;
    private boolean isDoctor;
    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView medicineName;
        TextView medicineContent;
        TextView medicineDoctor;
        TextView medicineDate;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view;
            medicineName = (TextView) view.findViewById(R.id.phr_medicine_name);
            medicineContent = (TextView) view.findViewById(R.id.phr_medicine_content);
            medicineDoctor = (TextView) view.findViewById(R.id.phr_medicine_doctor);
            medicineDate = (TextView) view.findViewById(R.id.phr_medicine_date);
        }
    }

    public MedicineAdapter(List<Medicine> medicineList,Boolean isDoctor){
        mMedicineList = medicineList;
        this.isDoctor = isDoctor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phr_medicine_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        mContext = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.ViewHolder holder, int position) {
        final Medicine medicine = mMedicineList.get(position);
        holder.medicineName.setText(medicine.getName());
        holder.medicineContent.setText(medicine.getContent());
        holder.medicineDoctor.setText("编辑医生：" + medicine.getDoctor());
        holder.medicineDate.setText("最后更新于：" + medicine.getDate());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",medicine.getId());
                if (isDoctor){
                    Intent intent = new Intent(mContext,UpdateMedicineActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext,MedicineDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMedicineList.size();
    }
}
