package com.google.jaaaule.gzw.italker;

import android.widget.EditText;
import android.widget.TextView;

import com.google.jaaaule.gzw.common.app.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IView {
    @BindView(R.id.tv_result)
    TextView mResultView;
    @BindView(R.id.et_query)
    EditText mQuery;
    private IPresenter mPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_submit)
    void onSubmit() {
        mPresenter.submit();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new Presenter(this);
    }

    @Override
    public String getInputString() {
        return mQuery.getText().toString();
    }

    @Override
    public void setResult(String str) {
        mResultView.setText(str);
    }
}
