package com.google.jaaaule.gzw.italker.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.jaaaule.gzw.common.app.BaseActivity;
import com.google.jaaaule.gzw.common.app.BaseFragment;
import com.google.jaaaule.gzw.italker.R;
import com.google.jaaaule.gzw.italker.fragments.account.AccountTrigger;
import com.google.jaaaule.gzw.italker.fragments.account.LoginFragment;
import com.google.jaaaule.gzw.italker.fragments.account.RegisterFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

public class AccountActivity extends BaseActivity implements AccountTrigger {
    @BindView(R.id.iv_account_bg)
    ImageView mAccountBg;
    private BaseFragment mCurrFragment;
    private BaseFragment mLoginFragment;
    private BaseFragment mRegisterFragment;

    /**
     * 跳转到当前 Activity
     *
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
        mCurrFragment = mLoginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, mCurrFragment)
                .commit();

          Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new ViewTarget<ImageView, GlideDrawable>(mAccountBg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Drawable drawable = resource.getCurrent();
                        //  使用适配包进行包装
                        drawable = DrawableCompat.wrap(drawable);
                        //  设置着色效果和蒙版
                        drawable.setColorFilter(UiCompat.getColor(getResources(), R.color.colorAccent),
                                PorterDuff.Mode.SCREEN);

                        this.view.setImageDrawable(drawable);
                    }
                });
    }

    @Override
    public void triggerView() {
        BaseFragment fragment = null;
        if (mCurrFragment == mLoginFragment) {
            if (mRegisterFragment == null) {
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        } else {
            //  已经赋值无需判空
            fragment = mLoginFragment;
        }

        mCurrFragment = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, mCurrFragment)
                .commit();
    }
}
