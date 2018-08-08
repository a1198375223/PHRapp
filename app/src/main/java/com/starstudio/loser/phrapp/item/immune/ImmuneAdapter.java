package com.starstudio.loser.phrapp.item.immune;

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
import android.widget.Toast;

import com.starstudio.loser.phrapp.R;

import java.util.List;


public class ImmuneAdapter extends RecyclerView.Adapter<ImmuneAdapter.ViewHolder> {

    private List<Immune> mImmuneList;
    private boolean isDoctor;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView immuneName;
        TextView immuneContent;
        TextView immuneDoctor;
        TextView immuneDate;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view;
            immuneName = (TextView) view.findViewById(R.id.phr_immune_name);
            immuneContent = (TextView) view.findViewById(R.id.phr_immune_content);
            immuneDoctor = (TextView) view.findViewById(R.id.phr_immune_doctor);
            immuneDate = (TextView) view.findViewById(R.id.phr_immune_date);
        }
    }

    public ImmuneAdapter (List<Immune> immuneList,boolean isDoctor){
        mImmuneList = immuneList;
        this.isDoctor = isDoctor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phr_immune_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        mContext = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImmuneAdapter.ViewHolder holder, int position) {
        final Immune immune = mImmuneList.get(position);
        holder.immuneName.setText(immune.getName());
        holder.immuneContent.setText(immune.getContent());
        holder.immuneDoctor.setText("编辑医生：" + immune.getDoctor());
        holder.immuneDate.setText("最后更新于：" + immune.getDate());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDoctor){
                    Intent intent = new Intent(mContext,UpdateImmuneActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",immune.getId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext,ImmuneDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",immune.getId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImmuneList.size();
    }
}
