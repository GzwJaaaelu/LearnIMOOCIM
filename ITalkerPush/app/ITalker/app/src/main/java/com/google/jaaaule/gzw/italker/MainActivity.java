package com.google.jaaaule.gzw.italker;

import android.widget.TextView;

import com.google.jaaaule.gzw.common.app.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_test)
    TextView mTestView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        mTestView.setText("Hello World! \n Test");
    }
}
