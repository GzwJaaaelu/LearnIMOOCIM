package com.google.jaaaule.gzw.italker.fragments.account;


import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.jaaaule.gzw.common.app.PresenterFragment;
import com.google.jaaaule.gzw.factory.presenter.account.RegisterContract;
import com.google.jaaaule.gzw.factory.presenter.account.RegisterPresenter;
import com.google.jaaaule.gzw.italker.R;
import com.google.jaaaule.gzw.italker.activities.MainActivity;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {
    @BindView(R.id.et_phone)
    EditText mPhone;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_name)
    EditText mName;
    @BindView(R.id.tv_go_login)
    TextView mGoLogin;
    @BindView(R.id.btn_submit)
    Button mSubmitBtn;
    @BindView(R.id.loading)
    Loading mLoading;
    private AccountTrigger mAccountTrigger;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    public void registerSuccess() {
        //  这时候已经注册成功，应该进行跳转操作
        MainActivity.show(getContext());
        //  关闭当前界面
        getActivity().finish();
    }

    @OnClick({R.id.tv_go_login, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go_login:
                showLogin();
                break;
            case R.id.btn_submit:
                onSubmitClick();
                break;
        }
    }

    private void showLogin() {
        //  切换界面
        mAccountTrigger.triggerView();
    }

    private void onSubmitClick() {
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        String name = mName.getText().toString();
        mPresenter.register(phone, password, name);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoading.start();
        //  界面不可操作
        setViewEnable(false);
    }

    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        //  停止 Loading
        mLoading.stop();
        //  界面恢复操作
        setViewEnable(true);
    }

    /**
     * 设置控件是否可用
     *
     * @param enable
     */
    private void setViewEnable(boolean enable) {
        mPhone.setEnabled(enable);
        mPassword.setEnabled(enable);
        mName.setEnabled(enable);
        mSubmitBtn.setEnabled(enable);
    }
}
