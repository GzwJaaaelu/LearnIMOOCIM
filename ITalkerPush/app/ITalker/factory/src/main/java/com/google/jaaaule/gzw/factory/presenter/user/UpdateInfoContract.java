package com.google.jaaaule.gzw.factory.presenter.user;

import com.google.jaaaule.gzw.factory.presenter.BaseContract;

/**
 * Created by admin on 2017/7/10.
 */

/**
 * 更新用户信息的基本契约
 */
public interface UpdateInfoContract {

    interface Presenter extends BaseContract.Presenter {
        /**
         * 更新信息
         *
         * @param photoFilePath
         * @param desc
         * @param isMan
         */
        void update(String photoFilePath,
                    String desc,
                    boolean isMan);
    }

    interface View extends BaseContract.View<Presenter> {
        /**
         * 更新成功
         */
        void updateSucceed();
    }
}
