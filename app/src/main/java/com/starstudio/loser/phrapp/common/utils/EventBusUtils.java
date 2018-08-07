package com.starstudio.loser.phrapp.common.utils;

/*
    create by:loser
    date:2018/8/7 23:20
*/

import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
