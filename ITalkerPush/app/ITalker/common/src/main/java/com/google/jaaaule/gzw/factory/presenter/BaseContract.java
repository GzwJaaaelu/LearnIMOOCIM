package com.google.jaaaule.gzw.factory.presenter;

import android.support.annotation.StringRes;

/**
 * MVP 中公共的基本契约
 * Created by admin on 2017/7/6.
 */

public interface BaseContract {

    interface View<T extends Presenter> {

        /**
         * 错误界面显示
         *
         * @param str
         */
        void showError(@StringRes int str);

        /**
         * 显示进度条
         */
        void showLoading();

        /**
         * 设置一个 PresenterFragment
         *
         * @param presenter
         */
        void setPresenter(T presenter);
    }

    interface Presenter {

        /**
         * 开始触发
         */
        void start();

        /**
         * 销毁触发
         */
        void destroy();
    }
}
