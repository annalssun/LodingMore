package com.cowinclub.dingdong.lru.recyclelru;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class DownLoadHttpClient {
    private OkHttpClient client;

    private static class HttpClientHolder {
        static DownLoadHttpClient instance = new DownLoadHttpClient();
    }

    private DownLoadHttpClient() {
        client = new OkHttpClient();
    }

    public static DownLoadHttpClient getInstance() {
        return HttpClientHolder.instance;
    }

    public boolean saveData(String url, OutputStream outputStream) {
        ResponseBody body = null;
        InputStream inputStream = null;
        try {
            Request request = new Request.Builder().url(url).build();
            //获取响应体
            body = client.newCall(request).execute().body();
            assert body != null;
            inputStream = body.byteStream();
            int b;
            while ((b = inputStream.read()) != -1) {
                outputStream.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }
}
