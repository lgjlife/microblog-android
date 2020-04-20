package com.example.microblog.http;

import okhttp3.Response;

public interface HttpClientCallback {

    void call(Response response);
}
