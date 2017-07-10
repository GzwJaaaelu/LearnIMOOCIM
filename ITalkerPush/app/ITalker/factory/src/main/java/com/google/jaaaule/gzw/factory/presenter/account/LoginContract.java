package com.google.jaaaule.gzw.factory.presenter.account;

import com.google.jaaaule.gzw.factory.presenter.BaseContract;

/**
 * Created by admin on 2017/7/6.
 */

public interface LoginContract {

    interface View extends BaseContract.View<Presenter> {

        /**
         * 登录成功
         */
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter {

        /**
         * 发起登录
         *
         * @param phone
         * @param password
         */
        void login(String phone,
                   String password);
    }
}
