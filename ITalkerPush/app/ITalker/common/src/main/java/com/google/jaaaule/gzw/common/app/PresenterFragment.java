package com.google.jaaaule.gzw.common.app;

import android.content.Context;
import android.support.annotation.StringRes;

import com.google.jaaaule.gzw.factory.presenter.BaseContract;

/**
 * Created by admin on 2017/7/6.
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends BaseFragment
        implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    /**
     * 初始化 Presenter
     * @return
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(@StringRes int str) {
        ITalkerApplication.showToast(str);
    }

    @Override
    public void showLoading() {
        //  TODO
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }
}
