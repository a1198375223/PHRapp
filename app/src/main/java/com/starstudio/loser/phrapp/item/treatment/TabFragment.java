package com.starstudio.loser.phrapp.item.treatment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starstudio.loser.phrapp.R;

/**
 * Created by 11024 on 2018/8/3.
 */

public class TabFragment extends Fragment {
    private TextView textView;

    public static TabFragment newInstance(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phr_treatment_tab_fragment, null);
        textView = view.findViewById(R.id.content_tv);
        textView.setText(String.valueOf((char) getArguments().getInt("index")));
        return view;

    }
}
