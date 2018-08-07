package com.starstudio.loser.phrapp.item.treatment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11024 on 2018/8/3.
 */

public class TabFragment extends Fragment {
    private LinearLayout linearLayout;


    public static TabFragment newInstance(int index,String hosp,String dept,String docName,String title,String profile){
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("hosp",hosp);
        bundle.putString("dept",dept);
        bundle.putString("docName",docName);
        bundle.putString("title",title);
        bundle.putString("profile",profile);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_treatment_tab_fragment, null);
        /*textView = view.findViewById(R.id.content_tv);
        textView.setText(String.valueOf((char) getArguments().getInt("index")));*/
        linearLayout=view.findViewById(R.id.content_layout);
        int index=getArguments().getInt("index");
        switch (index){
            case 0://简介
                String profile=getArguments().getString("profile");
                initProfileView(linearLayout,profile);
                break;
            case 1://评价
                String hosp=getArguments().getString("hosp");
                String dept=getArguments().getString("dept");
                String docName=getArguments().getString("hosp");
                initEvaluate(linearLayout,hosp,dept,docName);
                break;
            case 2://预约
                initAppointment();
                break;
                default:
                    break;

        }
        return view;

    }

    private void initAppointment() {
    }

    private void initEvaluate(final LinearLayout linearLayout, String hosp, String dept, String docName) {
        AVQuery<AVObject> query=new AVQuery<>("_User");
        query.whereEqualTo("hospName",hosp);
        query.whereEqualTo("dept",dept);
        query.whereEqualTo("username",docName);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e==null){
                    JSONArray jsonArray=avObject.getJSONArray("evaluate");
                    int sum=0;
                    float average=0;
                    DecimalFormat df = new DecimalFormat(".00");
                    try{
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            String userName=jsonObject.getString("user");
                            String content=jsonObject.getString("content");
                            int grade=Integer.valueOf(jsonObject.getString("grade"));
                            sum+=grade;
                            String date=jsonObject.getString("date");
                            boolean isAnonymous=Boolean.valueOf(jsonObject.getString("isAnonymous"));

                        }
                        average=Float.valueOf(df.format(sum/jsonArray.length()));
                    }catch (Exception e1){
                        e1.printStackTrace();
                        Log.e("test:",Log.getStackTraceString(e1));
                    }
                    LinearLayout scoreLayout=new LinearLayout(getActivity());
                    LinearLayout.LayoutParams tvParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
                    scoreLayout.setLayoutParams(tvParams);
                    TextView textView=new TextView(getActivity());
                    textView.setLayoutParams(tvParams);
                    textView.setTextSize(getResources().getDimension(R.dimen.phr_text_size_15));
                    textView.setTextColor(getResources().getColor(R.color.phr_black));
                    textView.setText("综合评分   ");
                    TextView scoreTv=new TextView(getActivity());
                    scoreTv.setLayoutParams(tvParams);
                    scoreTv.setTextSize(getResources().getDimension(R.dimen.phr_text_size_17));
                    scoreTv.setTextColor(getResources().getColor(R.color.phr_main_page_text_color));
                    scoreTv.setText(String.valueOf(average));

                    scoreLayout.addView(textView);
                    scoreLayout.addView(scoreTv);
                    linearLayout.addView(scoreLayout);

                    ListView listView=new ListView(getActivity());
                    List<EvaluateItem> evaluateList=new ArrayList<>();
                    EvaluateListAdapter adapter=new EvaluateListAdapter(getActivity(),evaluateList);
                    listView.setAdapter(adapter);
                    listView.setLayoutParams(tvParams);
                    linearLayout.addView(listView);
                }
            }
        });
    }

    private void initProfileView(LinearLayout linearLayout,String profile) {
        TextView profileTv=new TextView(getActivity());
        LinearLayout.LayoutParams tvParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
        profileTv.setLayoutParams(tvParams);
        profileTv.setTextSize(getResources().getDimension(R.dimen.phr_text_size_14));
        profileTv.setTextColor(getResources().getColor(R.color.phr_black));
        profileTv.setText("    "+profile);

        linearLayout.addView(profileTv);
    }
}
