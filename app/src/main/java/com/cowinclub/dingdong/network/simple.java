package com.cowinclub.dingdong.network;

import com.cowinclub.dingdong.bean.TestBean;

import io.reactivex.Flowable;


/**
 * Created by Administrator on 2018-03-26.
 */

public class simple {

    public static  Flowable<TestBean> getSampleData(int type,int page) {
        Flowable<TestBean> observable= HttpClient.getInstance().getRetrofit().create(ISampleApi.class)
                .getSampleData(type,page);
        return HttpClient.toSubcribeTransaction(observable);
    }

}
