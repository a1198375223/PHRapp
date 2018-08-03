package com.starstudio.loser.phrapp.item.treatment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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
        TextView doctorTitle=convertView.findViewById(R.id.doctor_title_tv);
        TextView doctorProfile=convertView.findViewById(R.id.doctor_profile_tv);
        final GridView doctorWorkTime=convertView.findViewById(R.id.doctor_work_time_grid_view);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context).load(doctorList.get(position).getDoctorImagId()).apply(options).into(doctorImage);
        doctorName.setText(doctorList.get(position).getDoctorName());
        doctorTitle.setText(doctorList.get(position).getDoctorTitle());
        doctorProfile.setText(doctorList.get(position).getProfile());

        try {
            JSONArray jsonArray = doctorList.get(position).getWorkArray();
            List<String> workList=new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String date=jsonObject.getString("date");
                String total=jsonObject.getString("total");
                String reserved=jsonObject.getString("reserved");
                workList.add(date+"\n预约数："+reserved+"/"+total);
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,workList);
            doctorWorkTime.setAdapter(adapter);
        }catch (Exception e1){
            e1.printStackTrace();
        }

        return convertView;
    }
}
