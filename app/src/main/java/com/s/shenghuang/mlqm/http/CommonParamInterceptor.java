package com.s.shenghuang.mlqm.http;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * AUTHOR : 颜文
 * TODO : 公共参数拦截器
 * DATE : 2020/3/30
 * VERSION : 1.0
 */
public class CommonParamInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        Request.Builder requestBuilder =builder.method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();

        HttpUrl.Builder hbuilder = request.url().newBuilder();
        hbuilder.addQueryParameter("XX-Device-Type", "Android");
//        hbuilder.addQueryParameter("long", String.valueOf(LocationUtils.getInstance2().getLongitude()));
//        hbuilder.addQueryParameter("lat", String.valueOf(LocationUtils.getInstance2().getLatitude()));
        request = request.newBuilder().url(hbuilder.build()).build();

        return chain.proceed(request);
    }
}
