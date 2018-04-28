package com.cowinclub.dingdong.bean;

public class TestBean {

    private String text; //标题
    private String profile_image;//图标

    public String getText() {
        return text != null ? text : "";
    }

    public String getProfile_image() {
        return profile_image != null ? profile_image : "";
    }
}
