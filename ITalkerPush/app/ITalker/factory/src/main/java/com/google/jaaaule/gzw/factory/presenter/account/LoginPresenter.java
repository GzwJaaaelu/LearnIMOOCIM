package com.google.jaaaule.gzw.factory.presenter.account;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.google.jaaaule.gzw.factory.data.DataSource;
import com.google.jaaaule.gzw.factory.data.helper.AccountHelper;
import com.google.jaaaule.gzw.factory.model.api.LoginModel;
import com.google.jaaaule.gzw.factory.model.db.User;
import com.google.jaaaule.gzw.factory.persistence.Account;
import com.google.jaaaule.gzw.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by admin on 2017/7/6.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter, DataSource.Callback<User> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(User data) {
        //  请求成功
        //  通知即可
        final LoginContract.View view = getView();
        if (view == null) {
            return;
        }
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(@StringRes final int str) {
        final LoginContract.View view = getView();
        if (view == null) {
            return;
        }
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(str);
            }
        });
    }

    @Override
    public void login(String phone, String password) {
        //  启动 Loading
        start();
        LoginContract.View view = getView();
        //  数据校验
        if (TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(password)) {
            view.showError(com.google.jaaaule.gzw.lang.R.string.data_account_login_invalid_parameter);
        } else {
            LoginModel login = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(login, this);
        }
    }
}
