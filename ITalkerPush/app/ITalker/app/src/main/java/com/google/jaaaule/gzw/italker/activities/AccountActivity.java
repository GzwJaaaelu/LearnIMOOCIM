package com.google.jaaaule.gzw.italker.activities;

import android.content.Context;
import android.content.Intent;

import com.google.jaaaule.gzw.common.app.BaseActivity;
import com.google.jaaaule.gzw.italker.R;
import com.google.jaaaule.gzw.italker.fragments.account.UpdateInfoFragment;

public class AccountActivity extends BaseActivity {
    private UpdateInfoFragment mUpdateInfoFragment;

    /**
     * 跳转到当前 Activity
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_account;
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
