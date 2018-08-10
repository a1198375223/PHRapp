package com.starstudio.loser.phrapp.item.treatment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.starstudio.loser.phrapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 11024 on 2018/8/3.
 */

public class TabFragment extends Fragment {
    private LinearLayout linearLayout;
    List<EvaluateItem> evaluateList=new ArrayList<>();
    List<String> workTimeList=new ArrayList<>();

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
        String hosp=getArguments().getString("hosp");
        String dept=getArguments().getString("dept");
        String docName=getArguments().getString("docName");
        switch (index){
            case 0://简介
                String profile=getArguments().getString("profile");
                initProfileView(linearLayout,profile);
                break;
            case 1://评价
                initEvaluate(linearLayout,hosp,dept,docName);
                break;
            case 2://预约
                initAppointment(linearLayout,hosp,dept,docName);
                break;
                default:
                    break;
        }
        return view;
    }

    private void initAppointment(final LinearLayout linearLayout, final String hosp, final String dept, final String docName) {
        final AVQuery<AVObject> query1 = new AVQuery<>("_User");
        AVQuery<AVObject> query2 = new AVQuery<>("_User");
        AVQuery<AVObject> query3 = new AVQuery<>("_User");
        query1.whereEqualTo("hospName",hosp);
        query2.whereEqualTo("dept",dept);
        query3.whereEqualTo("username",docName);
        AVQuery<AVObject> query=AVQuery.and(Arrays.asList(query1,query2,query3));
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e==null){
                    AVQuery<AVObject> avQuery = new AVQuery<>("WorkTime");
                    avQuery.whereEqualTo("doctorID",avObject.getObjectId());
                    avQuery.getFirstInBackground(new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            try{
                                JSONArray jsonArray=avObject.getJSONArray("workTime");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String reserved=jsonObject.getString("reserved");
                                    String total=jsonObject.getString("total");
                                    String date=jsonObject.getString("date");
                                    String reminder=jsonObject.getString("reminder");
                                    workTimeList.add(date+"               预约数："+reserved+"/"+total);
                                }
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                            LinearLayout.LayoutParams lvParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
                            lvParams.setMargins(0,10,0,0);
                            ListView listView=new ListView(getActivity());
                            listView.setLayoutParams(lvParams);
                            ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,workTimeList);
                            listView.setAdapter(adapter);
                            linearLayout.addView(listView);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent=new Intent(getActivity(),AppointmentActivity.class);
                                    intent.putExtra("hospName",hosp);
                                    intent.putExtra("deptName",dept);
                                    intent.putExtra("docName",docName);
                                    intent.putExtra("time",workTimeList.get(position));
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private void initEvaluate(final LinearLayout linearLayout, String hosp, String dept, String docName) {
        //Toast.makeText(getActivity(),hosp+","+dept+","+docName,Toast.LENGTH_SHORT).show();

        AVQuery<AVObject> query1 = new AVQuery<>("_User");
        AVQuery<AVObject> query2 = new AVQuery<>("_User");
        AVQuery<AVObject> query3 = new AVQuery<>("_User");
        query1.whereEqualTo("hospName",hosp);
        query2.whereEqualTo("dept",dept);
        query3.whereEqualTo("username",docName);
        final AVQuery<AVObject> query = AVQuery.and(Arrays.asList(query1,query2,query3));
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e==null){
                    float sum=0;
                    float average=0;
                    DecimalFormat df = new DecimalFormat(".00");
                    try{
                        //Toast.makeText(getActivity(),"test::"+name,Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray=avObject.getJSONArray("evaluate");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            final String userName=jsonObject.getString("user");
                            final String content=jsonObject.getString("content");
                            final float grade=Float.valueOf(jsonObject.getString("grade"));
                            sum+=grade;
                            final String date=jsonObject.getString("date");
                            final String isAnonymous=jsonObject.getString("isAnonymous");
                            final String imageUrl=jsonObject.getString("imageUrl");
                            //Log.e("test:::",userName+","+content+","+grade+","+date+","+isAnonymous+","+imageUrl);
                            if(isAnonymous.equals("true")){
                                EvaluateItem item=new EvaluateItem("*****",String.valueOf(grade),content,date,imageUrl);
                                evaluateList.add(item);
                            }else{
                                EvaluateItem item=new EvaluateItem(userName,String.valueOf(grade),content,date,imageUrl);
                                evaluateList.add(item);
                            }
                        }
                        average=Float.valueOf(df.format(sum/jsonArray.length()));
                    }catch (Exception e1){
                        e1.printStackTrace();
                        Log.e("test:",Log.getStackTraceString(e1));
                    }
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,0,0,10);
                    LinearLayout scoreLayout=new LinearLayout(getActivity());
                    LinearLayout.LayoutParams tvParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
                    scoreLayout.setLayoutParams(layoutParams);
                    TextView textView=new TextView(getActivity());
                    textView.setLayoutParams(tvParams);
                    textView.setTextSize(25);
                    textView.setTextColor(getResources().getColor(R.color.phr_black));
                    textView.setText("综合评分");
                    TextView scoreTv=new TextView(getActivity());
                    tvParams.setMargins(90,0,0,0);
                    scoreTv.setLayoutParams(tvParams);
                    scoreTv.setTextSize(40);
                    scoreTv.setTextColor(getResources().getColor(R.color.phr_main_page_text_color));
                    scoreTv.setText(String.valueOf(average));

                    scoreLayout.addView(textView);
                    scoreLayout.addView(scoreTv);
                    linearLayout.addView(scoreLayout);

                    LinearLayout.LayoutParams lvParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
                    lvParams.setMargins(0,10,0,0);
                    ListView listView=new ListView(getActivity());
                    listView.setLayoutParams(lvParams);
                    EvaluateListAdapter adapter=new EvaluateListAdapter(getActivity(),evaluateList);
                    listView.setAdapter(adapter);

                    linearLayout.addView(listView);
                    //Toast.makeText(getActivity(),"test:"+"size:"+evaluateList.size(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initProfileView(LinearLayout linearLayout,String profile) {
        TextView profileTv=new TextView(getActivity());
        LinearLayout.LayoutParams tvParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
        profileTv.setLayoutParams(tvParams);
        profileTv.setTextSize(18);
        profileTv.setTextColor(getResources().getColor(R.color.phr_black));
        profileTv.setText("    "+profile);

        linearLayout.addView(profileTv);
    }
}
