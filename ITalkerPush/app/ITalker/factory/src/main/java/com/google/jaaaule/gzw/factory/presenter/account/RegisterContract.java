package com.google.jaaaule.gzw.factory.presenter.account;

import com.google.jaaaule.gzw.factory.presenter.BaseContract;

/**
 * Created by admin on 2017/7/6.
 */

public interface RegisterContract {

    interface View extends BaseContract.View<Presenter> {

        /**
         * 注册成功
         */
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter {

        /**
         * 發起註冊
         *
         * @param phone
         * @param password
         * @param name
         */
        void register(String phone,
                      String password,
                      String name);

        /**
         * 检查手机号是否正确
         *
         * @return
         */
        boolean checkPhone(String phone);
    }
}
