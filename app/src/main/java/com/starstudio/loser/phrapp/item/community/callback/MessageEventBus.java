package com.starstudio.loser.phrapp.item.community.callback;

/*
    create by:loser
    date:2018/8/1 16:08
*/

public class MessageEventBus {
    private boolean delete;

    public MessageEventBus(boolean delete) {
        this.delete = delete;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
