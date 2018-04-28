package com.cowinclub.dingdong.network;

import com.cowinclub.dingdong.BuildConfig;
import com.cowinclub.dingdong.network.exception.ApiException;
import com.cowinclub.dingdong.network.reponse.HttpResult;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2018-03-26.
 */

public class HttpClient {

    private Retrofit mRetrofit;


    private static class HttpClientHolder {
        static HttpClient instance = new HttpClient();
    }


    public HttpClient getInstance() {
        return HttpClientHolder.instance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private HttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }


    public static <T> Observable<T> toSubcribe(Observable<HttpResult<T>> observable) {
        return observable.map(new HandleFuc<T>())
                .onErrorResumeNext(new HttpResponseFunc<T>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    /*对象转换*/
    public static class HandleFuc<T> implements Function<HttpResult<T>, T> {
        @Override
        public T apply(HttpResult<T> response) throws Exception {
            //response中code码不为200 出现错误
            if (!response.isOk())
                //抛出异常，把状态码及状态描述信息传入
                throw new ApiException(response.getCode(), response.getMsg());
            return response.getObject();
        }
    }

    /*添加异常处理*/
    public static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(throwable);
        }
    }

}
