package com.cowinclub.dingdong.common.util;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018-03-26.
 */

public class GsonFactory {

    private static volatile Gson instance = null;

    public static Gson getInstance() {
        if (instance == null) {
            synchronized (GsonFactory.class) {
                if (instance == null) {
                    instance = new Gson();
                }
            }
        }
        return instance;
    }
}
