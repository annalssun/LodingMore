package com.cowinclub.dingdong.network;

import com.cowinclub.dingdong.BuildConfig;
import com.cowinclub.dingdong.mdpulltorefresh.MyLog;
import com.cowinclub.dingdong.network.exception.ApiException;
import com.cowinclub.dingdong.network.reponse.HttpResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2018-03-26.
 */

public class HttpClient {

    private Retrofit mRetrofit;


    private static class HttpClientHolder {
        static HttpClient instance = new HttpClient();
    }


    public static HttpClient getInstance() {
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
        Interceptor logInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                MyLog.i("请求内容========" + request.url());
          /*      RequestBody requestBody = request.body();
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = Charset.forName("utf-8");
                MyLog.i("请求内容========" + buffer.readString(charset));*/
                Response response = chain.proceed(request);
                ResponseBody responseBody = response.peekBody(1024 * 1024);
                MyLog.i("返回内容" + responseBody.string());
                return response;
            }
        };
        builder.addInterceptor(logInterceptor);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }



    public static <T> Flowable<T> toSubcribeTransaction(Flowable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static <T> Flowable<T> toSubcribe(Flowable<HttpResult<T>> observable) {
        return observable.map(new HandleFuc<T>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    /*对象转换*/
    public static class HandleFuc<T> implements Function<HttpResult<T>, T> {
        @Override
        public T apply(HttpResult<T> response) throws Exception {
            //response中code码不为200 出现错误
            if (!response.isOk())//抛出异常，把状态码及状态描述信息传入
                throw new ApiException(response.getCode(), response.getMsg());
            return response.getObject();
        }
    }


    /*添加异常处理*/
    public static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        //        @Override
//        public Observable<T> call(Throwable throwable) {
//            return Observable.error(throwable);
//        }
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(throwable);
        }
    }


}
