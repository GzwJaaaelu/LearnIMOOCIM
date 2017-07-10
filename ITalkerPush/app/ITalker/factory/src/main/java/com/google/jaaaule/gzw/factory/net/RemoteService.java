package com.google.jaaaule.gzw.factory.net;

import com.google.jaaaule.gzw.factory.RspModel;
import com.google.jaaaule.gzw.factory.model.api.AccountRspModel;
import com.google.jaaaule.gzw.factory.model.api.LoginModel;
import com.google.jaaaule.gzw.factory.model.api.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络请求的所有接口
 * Created by admin on 2017/7/7.
 */

public interface RemoteService {

    /**
     * 注册接口
     *
     * @param register
     * @return
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> register(@Body RegisterModel register);

    /**
     * 登录接口
     *
     * @param login
     * @return
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> login(@Body LoginModel login);

    /**
     * 绑定 PushId
     *
     * @param pushId
     * @return
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> bindPushId(@Path(encoded = true, value = "pushId") String pushId);
}
