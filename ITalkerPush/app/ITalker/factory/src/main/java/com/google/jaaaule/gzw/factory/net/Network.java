package com.google.jaaaule.gzw.factory.net;

import android.text.TextUtils;

import com.google.jaaaule.gzw.common.Common;
import com.google.jaaaule.gzw.factory.Factory;
import com.google.jaaaule.gzw.factory.persistence.Account;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/7/7.
 */

public class Network {
    private static Network sInstance;
    private Retrofit mRetrofit;

    static {
        sInstance = new Network();
    }

    private Network() {

    }

    public static Retrofit getRetrofit() {
        if (sInstance.mRetrofit != null) {
            return sInstance.mRetrofit;
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                //  给所有的请求添加一个拦截器用于添加 Token
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //  拿到请求
                        Request original = chain.request();
                        //  重新
                        Request.Builder builder = original.newBuilder();
                        if (!TextUtils.isEmpty(Account.getToken())) {
                            builder.addHeader("token", Account.getToken());
                        }
                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        sInstance.mRetrofit = builder.baseUrl(Common.Constance.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return sInstance.mRetrofit;
    }

    public static RemoteService getRemoteService() {
        return getRetrofit().create(RemoteService.class);
    }
}
