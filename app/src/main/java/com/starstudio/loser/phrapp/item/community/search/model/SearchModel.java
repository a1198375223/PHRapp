package com.starstudio.loser.phrapp.item.community.search.model;

/*
    create by:loser
    date:2018/8/9 13:31
*/

import android.content.Context;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.community.search.contract.SearchContract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SearchModel extends PHRModel implements SearchContract.SearchContractModel {
    private SearchContract.SearchContractPresenter mPresenter;

    public SearchModel(SearchContract.SearchContractPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void find(String string) {
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.include("article_user").whereContains("title", string);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mPresenter.tellToShowSearch(list);
                } else {
                    mPresenter.tellToShowSearch(new ArrayList<AVObject>());
                }
            }
        });
    }

    @Override
    public void findAndSave(String string, Context context) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput("history.txt", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write("\n"+string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.include("article_user").whereContains("title", string);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mPresenter.tellToShowResult(list);
                } else {
                    mPresenter.tellToShowResult(new ArrayList<AVObject>());
                }
            }
        });
    }

    @Override
    public void loadHistory(Context context) {
        FileInputStream in = null;
        BufferedReader reader = null;
        List<String> list = new ArrayList<>();
        try {
            in = context.openFileInput("history.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("")) {
                    list.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mPresenter.tellToShowHistory(list);
    }

    @Override
    public void clearHistory(Context context) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput("history.txt", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mPresenter.tellToShowHistory(new ArrayList<String>());
    }
}
