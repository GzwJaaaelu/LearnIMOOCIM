package com.google.jaaaule.gzw.italker;

import com.google.jaaaule.gzw.common.app.BaseActivity;
import com.google.jaaaule.gzw.italker.activities.MainActivity;
import com.google.jaaaule.gzw.italker.fragments.assist.PermissionFragment;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionFragment.haveAllPerm(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_launch;
    }
}
