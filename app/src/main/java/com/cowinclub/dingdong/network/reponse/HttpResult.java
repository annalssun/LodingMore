package com.cowinclub.dingdong.network.reponse;

/**
 * Created by Administrator on 2018-03-26.
 */

public class HttpResult<T> {
    private int code;
    private T object;
    private String msg;


    public boolean isOk() {
        return this.code == 200;
    }

    public T getObject(){
        return this.object;
    }

    public String getMsg(){
        return this.msg;
    }

    public int getCode(){
        return this.code;
    }

}
