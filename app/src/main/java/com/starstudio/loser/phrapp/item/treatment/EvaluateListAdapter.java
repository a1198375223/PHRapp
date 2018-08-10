package com.starstudio.loser.phrapp.item.treatment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class EvaluateListAdapter extends BaseAdapter{
    private Context context;
    private List<EvaluateItem> evaluateList;

    public EvaluateListAdapter(Context context,List<EvaluateItem> evaluateList){
        this.context=context;
        this.evaluateList=evaluateList;
    }

    @Override
    public int getCount() {
        return evaluateList.size();
    }

    @Override
    public Object getItem(int position) {
        return evaluateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.phr_treatment_evaluate_list_adapter,parent,false);
        ImageView userHeadImage=convertView.findViewById(R.id.evaluate_head_image);
        TextView userNameTv=convertView.findViewById(R.id.evaluate_user_name_tv);
        TextView gradeTv=convertView.findViewById(R.id.evaluate_grade_tv);
        TextView contentTv=convertView.findViewById(R.id.evaluate_content_tv);
        TextView dateTv=convertView.findViewById(R.id.evaluate_date);

        String userName=evaluateList.get(position).getUserName();
        String grade=evaluateList.get(position).getGrade();
        String content=evaluateList.get(position).getEvaluation();
        String date=evaluateList.get(position).getEvaluateDate();
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.waiting)
                .error(R.mipmap.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context).load(evaluateList.get(position).getImageId()).apply(options).into(userHeadImage);
        userNameTv.setText(userName);
        gradeTv.setText(grade);
        contentTv.setText(content);
        dateTv.setText(date);

        return convertView;
    }
}
