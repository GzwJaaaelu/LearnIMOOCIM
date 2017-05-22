package com.google.jaaaule.gzw.italker;

import android.widget.EditText;
import android.widget.TextView;

import com.google.jaaaule.gzw.common.app.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_result)
    TextView mResultView;
    @BindView(R.id.et_query)
    EditText mQuery;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_submit)
    void onSubmit() {
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
