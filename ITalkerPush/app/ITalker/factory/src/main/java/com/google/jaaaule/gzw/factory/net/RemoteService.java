package com.google.jaaaule.gzw.factory.net;

import com.google.jaaaule.gzw.factory.model.api.RspModel;
import com.google.jaaaule.gzw.factory.model.api.account.AccountRspModel;
import com.google.jaaaule.gzw.factory.model.api.account.LoginModel;
import com.google.jaaaule.gzw.factory.model.api.account.RegisterModel;
import com.google.jaaaule.gzw.factory.model.api.user.UserUpdateModel;
import com.google.jaaaule.gzw.factory.model.card.UserCard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    /**
     * 登录接口
     *
     * @param login
     * @return
     */
    @PUT("user")
    Call<RspModel<UserCard>> updateUserInfo(@Body UserUpdateModel login);
}
