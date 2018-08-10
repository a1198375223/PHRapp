package com.starstudio.loser.phrapp.item.treatment;

import android.content.Context;
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
 * Created by 11024 on 2018/8/1.
 */

public class ImageListAdapter extends BaseAdapter {
    private Context context;
    private List<HospitalItem> imageUrls;

    public ImageListAdapter(Context context,List<HospitalItem> imageUrls){
        this.context=context;
        this.imageUrls=imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.phr_hosp_choice_gridview_adapter,parent,false);
        TextView hospTv=convertView.findViewById(R.id.hosp_text);
        ImageView hospIv=convertView.findViewById(R.id.hosp_img);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.waiting)
                .error(R.mipmap.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context).load(imageUrls.get(position).getHospImagId()).apply(options).into(hospIv);
        hospTv.setText(imageUrls.get(position).getHospName());

        return convertView;
    }
}
