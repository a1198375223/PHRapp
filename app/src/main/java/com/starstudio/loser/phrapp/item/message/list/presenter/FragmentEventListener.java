package com.starstudio.loser.phrapp.item.message.list.presenter;

/*
    create by:loser
    date:2018/7/26 13:51
*/

import com.starstudio.loser.phrapp.common.base.BaseEventListener;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;

import java.util.List;

public interface FragmentEventListener extends BaseEventListener{
    void startWebActivity(String url);

    List<UsefulData> convertToUsefulData(BaseBean baseBean);
}
