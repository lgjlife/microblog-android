package com.example.microblog.http;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {

    private static OkHttpClient httpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public static void get(String url, Callback clientCallback){
        Request request = new Request.Builder().url(url).build();
        try {
            httpClient.newCall(request).enqueue(clientCallback);

        }
        catch (Exception ex){

        }

    }

    public static void post(String url,RequestBody requestBody,Callback clientCallback){
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            httpClient.newCall(request).enqueue(clientCallback);
        }
        catch (Exception ex){

        }
    }


}
