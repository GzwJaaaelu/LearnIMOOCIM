package com.google.jaaaule.gzw.factory.presenter.account;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.google.jaaaule.gzw.common.Common;
import com.google.jaaaule.gzw.factory.R;
import com.google.jaaaule.gzw.factory.data.DataSource;
import com.google.jaaaule.gzw.factory.data.helper.AccountHelper;
import com.google.jaaaule.gzw.factory.model.api.account.RegisterModel;
import com.google.jaaaule.gzw.factory.model.db.User;
import com.google.jaaaule.gzw.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

/**
 * Created by admin on 2017/7/6.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter, DataSource.Callback<User> {

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String password, String name) {
        //  启动 Loading
        start();

        RegisterContract.View view = getView();

        //  数据校验
        if (!checkPhone(phone)) {
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (name.length() < 2) {
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else if (password.length() < 6) {
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else {
            //  请求调用
            RegisterModel register = new RegisterModel(phone, password, name);
            AccountHelper.register(register, this);
        }
    }

    @Override
    public boolean checkPhone(String phone) {
        return !TextUtils.isEmpty(phone) &&
                Pattern.matches(Common.Constance.REGEX_PHONE, phone);
    }

    @Override
    public void onDataLoaded(User data) {
        //  请求成功
        //  通知即可
        final RegisterContract.View view = getView();
        if (view == null) {
            return;
        }
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(@StringRes final int str) {
        final RegisterContract.View view = getView();
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
}
