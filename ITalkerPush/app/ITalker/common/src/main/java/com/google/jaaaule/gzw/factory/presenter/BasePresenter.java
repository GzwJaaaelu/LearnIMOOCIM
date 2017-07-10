package com.google.jaaaule.gzw.factory.presenter;

/**
 * Created by admin on 2017/7/6.
 */

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {

    private T mView;

    public BasePresenter (T view) {
        setView(view);
        view.setPresenter(this);
    }

    protected void setView(T view) {
        mView = view;
    }

    protected final T getView() {
        return mView;
    }

    @Override
    public void start() {
        T view = mView;
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void destroy() {
        T view = mView;
        mView = null;
        if (view != null) {
            view.setPresenter(null);
        }
    }
}
