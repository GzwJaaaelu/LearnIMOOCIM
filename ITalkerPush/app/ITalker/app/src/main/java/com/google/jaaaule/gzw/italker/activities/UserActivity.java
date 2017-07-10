package com.google.jaaaule.gzw.italker.activities;

import android.content.Intent;

import com.google.jaaaule.gzw.common.app.BaseActivity;
import com.google.jaaaule.gzw.italker.R;
import com.google.jaaaule.gzw.italker.fragments.user.UpdateInfoFragment;

public class UserActivity extends BaseActivity {
    private UpdateInfoFragment mUpdateInfoFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user;
    }


    @Override
    protected void initView() {
        super.initView();
        mUpdateInfoFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, mUpdateInfoFragment)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mUpdateInfoFragment.onActivityResult(requestCode, resultCode, data);
    }
}
