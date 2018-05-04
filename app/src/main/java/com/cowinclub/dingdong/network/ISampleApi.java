package com.cowinclub.dingdong.network;

import com.cowinclub.dingdong.bean.TestBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ISampleApi {

    @GET("/satinApi")
    Flowable<TestBean> getSampleData(@Query("type") int type,@Query("page") int page);
}
