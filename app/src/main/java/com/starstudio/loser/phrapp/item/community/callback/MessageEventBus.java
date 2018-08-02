package com.starstudio.loser.phrapp.item.community.callback;

/*
    create by:loser
    date:2018/8/1 16:08
*/

public class MessageEventBus {
    private boolean ifRefresh;

    public MessageEventBus(boolean ifRefresh) {
        this.ifRefresh = ifRefresh;
    }

    public boolean isIfRefresh() {
        return ifRefresh;
    }

    public void setIfRefresh(boolean ifRefresh) {
        this.ifRefresh = ifRefresh;
    }
}
