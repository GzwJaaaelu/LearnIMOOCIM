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
import com.google.jaaaule.gzw.italker.R;
import com.google.jaaaule.gzw.italker.fragments.user.UpdateInfoFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

public class UserActivity extends BaseActivity {
    private UpdateInfoFragment mUpdateInfoFragment;
    @BindView(R.id.iv_account_bg)
    ImageView mAccountBg;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mUpdateInfoFragment.onActivityResult(requestCode, resultCode, data);
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }
}
