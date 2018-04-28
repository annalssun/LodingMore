package com.cowinclub.dingdong.network.exception;

/**
 * Created by Administrator on 2018-03-27.
 */

public class ApiException extends RuntimeException {
    public ApiException(int code, String message) {
        this(getApiExceptionMessage(code, message));
    }

    private ApiException(String message) {
        super(message);
    }

    private static String getApiExceptionMessage(int code, String message) {
        StringBuilder result = new StringBuilder();
        switch (code) {
            case 101:
                result.append("这只是一个栗子");
                break;
            default:
                result.append(message);
        }

        return result.toString();
    }
}
