package com.starstudio.loser.phrapp.item.main.view;

/*
    create by:loser
    date:2018/7/23 13:29
*/

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.item.community.CommunityActivity;
import com.starstudio.loser.phrapp.item.immune.DoctorImmuneActivity;
import com.starstudio.loser.phrapp.item.main.PHRMainActivity;
import com.starstudio.loser.phrapp.item.main.contract.MainContract;
import com.starstudio.loser.phrapp.item.management.ManageMainActivity;
import com.starstudio.loser.phrapp.item.map.HospitalMapActivity;
import com.starstudio.loser.phrapp.item.medicine.MedicineMainActivity;
import com.starstudio.loser.phrapp.item.message.PHRMessageActivity;
import com.starstudio.loser.phrapp.item.modify.ModifyActivity;
import com.starstudio.loser.phrapp.item.treatment.TreatmentActiity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class MainViewImpl extends PHRView implements MainContract.MainView {
    private View mRootView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainViewImpl(final PHRMainActivity activity) {
        super(activity);

        mRootView = LayoutInflater.from(activity).inflate(R.layout.phr_main_layout, null);

        //navigationView
        final NavigationView navigationView = (NavigationView) activity.findViewById(R.id.phr_main_navigation_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.phr_main_navigation_view_menu_item1:
                        Toast.makeText(activity, "click item1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.phr_main_navigation_view_menu_item2:
                        Toast.makeText(activity, "click item2", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.phr_main_navigation_view_menu_item3:
                        activity.startActivity(new Intent(activity, ManageMainActivity.class));
                        Toast.makeText(activity, "click item3", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.phr_main_navigation_view_menu_item4:
                        activity.startActivity(new Intent(activity, ModifyActivity.class));
                        Toast.makeText(activity, "click item4", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.phr_main_navigation_view_menu_item5:
                        SharedPreferences pref_clean = activity.getSharedPreferences("user_data", MODE_PRIVATE );
                        pref_clean.edit().clear().commit();
                        activity.initView(navigationView);
                        navigationView.getMenu().findItem(R.id.phr_main_navigation_view_menu_item4).setVisible(false);
                        navigationView.getMenu().findItem(R.id.phr_main_navigation_view_menu_item3).setVisible(false);
                        item.setVisible(false);
                        AVUser.logOut();
                        Toast.makeText(activity, "已退出登录", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                return true;
            }
        });

        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> value = new HashMap<>();
        value.put("text", activity.getString(R.string.phr_main_gridlayout_text1));
        value.put("image", R.mipmap.phr_message_fl);
        list.add(value);
        value = new HashMap<>();
        value.put("text", activity.getString(R.string.phr_main_gridlayout_text2));
        value.put("image", R.mipmap.phr_treatment_fl);
        list.add(value);
        value = new HashMap<>();
        value.put("text", activity.getString(R.string.phr_main_gridlayout_text3));
        value.put("image", R.mipmap.phr_map_fl);
        list.add(value);
        value = new HashMap<>();
        value.put("text", activity.getString(R.string.phr_main_gridlayout_text4));
        value.put("image", R.mipmap.phr_registered_fl);
        list.add(value);
        value = new HashMap<>();
        value.put("text", activity.getString(R.string.phr_main_gridlayout_text5));
        value.put("image", R.mipmap.phr_community_fl);
        list.add(value);
        value = new HashMap<>();
        value.put("text",activity.getString(R.string.phr_main_gridlayout_text6));
        value.put("image",R.mipmap.phr_medicine_fl);
        list.add(value);


        GridView gridView = (GridView) activity.findViewById(R.id.phr_main_grid_view);
        gridView.setAdapter(new SimpleAdapter(activity,
                list,
                R.layout.phr_gridview_adapter,
                new String[]{"text", "image"},
                new int[]{R.id.phr_main_grid_view_text, R.id.phr_main_grid_view_image}));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent((PHRMainActivity) activity, PHRMessageActivity.class);
                        activity.startActivity(intent);
                        break;
                    case 1:
                        activity.startActivity(new Intent((PHRMainActivity) activity, DoctorImmuneActivity.class));
                        break;
                    case 2:
                        activity.startActivity(new Intent((PHRMainActivity) activity, HospitalMapActivity.class));
                        break;
                    case 3:
                        Intent intent4 = new Intent((PHRMainActivity) activity, TreatmentActiity.class);
                        activity.startActivity(intent4);
                        break;
                    case 4:
                        intent = new Intent((PHRMainActivity) activity, CommunityActivity.class);
                        activity.startActivity(intent);
                        break;
                    case 5:
                        activity.startActivity(new Intent((PHRMainActivity) activity, MedicineMainActivity.class));
                        break;
                    default:
                }
            }
        });
    }


    @Override
    public View getView() {
        return mRootView;
    }

}
