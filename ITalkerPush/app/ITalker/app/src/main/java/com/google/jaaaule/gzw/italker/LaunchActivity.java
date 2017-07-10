package com.google.jaaaule.gzw.italker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Property;
import android.widget.LinearLayout;

import com.google.jaaaule.gzw.common.app.BaseActivity;
import com.google.jaaaule.gzw.factory.persistence.Account;
import com.google.jaaaule.gzw.italker.activities.AccountActivity;
import com.google.jaaaule.gzw.italker.activities.MainActivity;
import com.google.jaaaule.gzw.italker.fragments.assist.PermissionFragment;

import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;

public class LaunchActivity extends BaseActivity implements PermissionFragment.OnPermissionCompleteListener {
    @BindView(R.id.ll_activity_launch)
    LinearLayout mLlActivityLaunch;
    private ColorDrawable mColorDrawable;
    @BindView(R.id.loading)
    Loading mLoading;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initView() {
        super.initView();
        //  获取颜色
        int color = UiCompat.getColor(getResources(), R.color.colorPrimary);
        ColorDrawable drawable = new ColorDrawable(color);
        //  设置背景
        mLlActivityLaunch.setBackground(drawable);
        mColorDrawable = drawable;
    }

    @Override
    protected void initData() {
        super.initData();
        //  动画进入到 50% 等待 PushId 获取
        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                waitPushReceiverId();
            }
        });
    }

    /**
     * 等待个推对我们的 PushId 设置好值
     */
    private void waitPushReceiverId() {
        //  是否登录
        if (Account.isLogin()) {
            //  是否绑定
            //  如果没有绑定则等到广播接收器进行绑定
            if (Account.isBind()) {
                skip();
                return;
            }
        } else {
            //  没有登录
            //  如果拿到了 PushId，没有登录不能绑定 PushId
            if (!TextUtils.isEmpty(Account.getPushId())) {
                skip();
                return;
            }
        }
        //  循环等待
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                waitPushReceiverId();
            }
        }, 500);
    }

    /**
     * 页面跳转和完成剩余动画
     */
    private void skip() {
        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                reallySkipAndCheckPerm();
            }
        });
    }

    /**
     * 跳转和检查权限
     */
    private void reallySkipAndCheckPerm() {
        if (PermissionFragment.haveAllPerm(LaunchActivity.this, getSupportFragmentManager(), this)) {
           reallySkip();
        }
    }

    /**
     * 真正的跳转
     */
    private void reallySkip() {
        //  检查跳转主页
        if (Account.isLogin()) {
            MainActivity.show(LaunchActivity.this);
        } else {
            AccountActivity.show(LaunchActivity.this);
        }
        finish();
    }

    /**
     * 根布局的动画
     *
     * @param endProgress
     * @param endCallback
     */
    private void startAnim(float endProgress, final Runnable endCallback) {
        //  最终颜色值
        int finalColor = UiCompat.getColor(getResources(), R.color.white);
        //  运算当前颜色值
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int endColor = (int) evaluator.evaluate(endProgress, mColorDrawable.getColor(), finalColor);
        ValueAnimator animator = ObjectAnimator.ofObject(this, mProperty, evaluator, endColor);
        animator.setDuration(1800);
        animator.setIntValues(mColorDrawable.getColor(), endColor);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                endCallback.run();
            }
        });
        animator.start();
    }

    private final Property<LaunchActivity, Object> mProperty = new Property<LaunchActivity, Object>(Object.class, "color") {
        @Override
        public Object get(LaunchActivity object) {
            return object.mColorDrawable.getColor();
        }

        @Override
        public void set(LaunchActivity object, Object value) {
            object.mColorDrawable.setColor((Integer) value);
        }
    };

    @Override
    public void onPermissionComplete() {
        reallySkip();
    }
}
