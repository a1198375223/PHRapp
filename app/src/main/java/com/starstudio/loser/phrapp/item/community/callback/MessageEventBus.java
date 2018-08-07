package com.starstudio.loser.phrapp.item.community.callback;

/*
    create by:loser
    date:2018/8/1 16:08
*/

public class MessageEventBus {
    private boolean todo;

    public MessageEventBus(boolean todo) {
        this.todo = todo;
    }

    public boolean isTodo() {
        return todo;
    }

    public void setTodo(boolean todo) {
        this.todo = todo;
    }
}
