package com.cowinclub.dingdong.bean;

import java.util.List;

public class TestBean {
    private String code;
    private String msg;
    private List<Data> data;


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data {
        public String text;
        public String profile_image;
    }
}
