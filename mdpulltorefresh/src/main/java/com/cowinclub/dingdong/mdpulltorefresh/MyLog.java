package com.cowinclub.dingdong.mdpulltorefresh;

import android.util.Log;

import java.util.Locale;

/**
 * Created by Administrator on 2017/7/3.
 */

public class MyLog {
    private static String tag = "叮咚钱包";

    /**
     * 获取到调用者的类名
     * @return 调用者的类名
     */
    private static String getTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(MyLog.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }

    /**
     * 获取线程ID，方法名和输出信息
     * @param msg
     * @return
     */
    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(MyLog.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "**%s** %s: %s", tag, caller, msg);
    }

    //不需要再在类中定义TAG，打印线程ID，方法名和输出信息

    public static void i(String mess) {
         Log.v(getTag(), buildMessage(mess));
    }
    public static void e() {
//        if (BuildConfig.LogDebug) { Log.v(getTag(), buildMessage("===========================================出错了")); }
    }


    /**
     * 获取相关数据:类名,方法名,行号等.用来定位行
     * @return
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts != null) {
            for (StackTraceElement st : sts) {
                if (st.isNativeMethod()) {
                    continue;
                }
                if (st.getClassName().equals(Thread.class.getName())) {
                    continue;
                }
                if (st.getClassName().equals(MyLog.class.getName())) {
                    continue;
                }
                return "[ Thread:" + Thread.currentThread().getName() + ", at " + st.getClassName() + "." + st.getMethodName()
                        + "(" + st.getFileName() + ":" + st.getLineNumber() + ")" + " ]";
            }
        }
        return null;
    }

    /**
     * 输出格式定义
     * @param msg
     * @return
     */
    private static String getMsgFormat(String msg) {
        return msg + " ;" + getFunctionName();
    }
}
