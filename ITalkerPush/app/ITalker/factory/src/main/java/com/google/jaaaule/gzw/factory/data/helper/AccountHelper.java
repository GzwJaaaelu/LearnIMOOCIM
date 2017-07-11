package com.google.jaaaule.gzw.factory.data.helper;

import android.text.TextUtils;

import com.google.jaaaule.gzw.factory.Factory;
import com.google.jaaaule.gzw.factory.R;
import com.google.jaaaule.gzw.factory.data.DataSource;
import com.google.jaaaule.gzw.factory.model.api.RspModel;
import com.google.jaaaule.gzw.factory.model.api.account.AccountRspModel;
import com.google.jaaaule.gzw.factory.model.api.account.LoginModel;
import com.google.jaaaule.gzw.factory.model.api.account.RegisterModel;
import com.google.jaaaule.gzw.factory.model.db.User;
import com.google.jaaaule.gzw.factory.net.Network;
import com.google.jaaaule.gzw.factory.net.RemoteService;
import com.google.jaaaule.gzw.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/7/6.
 */

public class AccountHelper {

    /**
     * 注册，异步调用
     *
     * @param register
     * @param callback
     */
    public static void register(RegisterModel register, final DataSource.Callback<User> callback) {
        RemoteService service = Network.getRemoteService();
        service.register(register).enqueue(new AccountCallBack<AccountRspModel>(callback));
    }

    /**
     * 登录，异步调用
     *
     * @param login
     * @param callback
     */
    public static void login(LoginModel login, final DataSource.Callback<User> callback) {
        RemoteService service = Network.getRemoteService();
        service.login(login).enqueue(new AccountCallBack<AccountRspModel>(callback));
    }

    /**
     * 绑定
     * @param callback
     */
    public static void bindPushId(final DataSource.Callback<User> callback) {
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId)) {
            return;
        }
        RemoteService service = Network.getRemoteService();
        service.bindPushId(pushId).enqueue(new AccountCallBack<AccountRspModel>(callback));
        Account.setBind(true);
    }

    /**
     * 请求回调
     *
     * @param <T>
     */
    private static class AccountCallBack<T> implements Callback<RspModel<T>> {
        final DataSource.Callback<User> callback;

        private AccountCallBack(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<T>> call, Response<RspModel<T>> response) {
            RspModel<AccountRspModel> rspModel = (RspModel<AccountRspModel>) response.body();
            if (rspModel.success()) {
                AccountRspModel accountRsp = rspModel.getResult();
                User user = accountRsp.getUser();
                //  存储到数据库
                user.save();
                //  同步到 XML 持久化
                Account.login(accountRsp);
                //  判断是否绑定过 PushId
                if (accountRsp.isBind()) {
                    Account.setBind(true);
                    if (callback != null) {
                        callback.onDataLoaded(user);
                    }
                } else {
                    bindPushId(callback);
                }
            } else {
                Factory.decodeRepModel(rspModel, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<T>> call, Throwable t) {
            if (callback != null) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
            t.printStackTrace();
        }
    }
}
