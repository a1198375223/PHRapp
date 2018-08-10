package com.starstudio.loser.phrapp.item.treatment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.starstudio.loser.phrapp.R;

import java.util.List;

/**
 * Created by 11024 on 2018/8/2.
 */

public class DoctorListAdapter extends BaseAdapter{
    private Context context;
    private List<DoctorItem> doctorList;

    public DoctorListAdapter(Context context,List<DoctorItem> doctorList){
        this.context=context;
        this.doctorList=doctorList;
    }

    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.phr_doctor_list_adapter,parent,false);
        ImageView doctorImage=convertView.findViewById(R.id.doctor_image);
        TextView doctorName=convertView.findViewById(R.id.doctor_name_tv);
        final TextView doctorTitle=convertView.findViewById(R.id.doctor_title_tv);
        TextView doctorProfile=convertView.findViewById(R.id.doctor_profile_tv);
        final String hospName=doctorList.get(position).getHosp();
        final String docName=doctorList.get(position).getDoctorName();
        final String deptName=doctorList.get(position).getDept();
        final String title=doctorList.get(position).getDoctorTitle();
        final String profile=doctorList.get(position).getProfile();
        final String imageUrl=doctorList.get(position).getDoctorImagId();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context).load(imageUrl).apply(options).into(doctorImage);
        doctorName.setText(docName);
        doctorTitle.setText(doctorList.get(position).getDoctorTitle());
        doctorProfile.setText(doctorList.get(position).getProfile());

        doctorImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //根据docnName 跳转到医生主页
                Intent intent=new Intent(context,DoctorPage.class);
                intent.putExtra("hosp",hospName);
                intent.putExtra("dept",deptName);
                intent.putExtra("docName",docName);
                intent.putExtra("title",title);
                intent.putExtra("profile",profile);
                intent.putExtra("imageUrl",imageUrl);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
