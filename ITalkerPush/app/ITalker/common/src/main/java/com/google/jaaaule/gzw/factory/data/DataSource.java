package com.google.jaaaule.gzw.factory.data;

import android.support.annotation.StringRes;

/**
 * 数据源接口定义
 * Created by admin on 2017/7/6.
 */

public interface DataSource {

    /**
     * 两个回调都需要
     *
     * @param <T>
     */
    interface Callback<T> extends SuccessCallback<T>, FailedCallback {

    }

    /**
     * 只关注成功的接口
     *
     * @param <T>
     */
    interface SuccessCallback<T> {

        void onDataLoaded(T data);
    }

    /**
     * 只关注成功的接口
     */
    interface FailedCallback {

        void onDataNotAvailable(@StringRes int str);
    }
}
