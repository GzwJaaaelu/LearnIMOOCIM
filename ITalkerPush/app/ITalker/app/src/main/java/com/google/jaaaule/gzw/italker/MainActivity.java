package com.google.jaaaule.gzw.italker;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.jaaaule.gzw.common.app.BaseActivity;
import com.google.jaaaule.gzw.common.widget.PortraitView;
import com.google.jaaaule.gzw.italker.activities.AccountActivity;
import com.google.jaaaule.gzw.italker.fragments.main.ActiveFragment;
import com.google.jaaaule.gzw.italker.fragments.main.ContactFragment;
import com.google.jaaaule.gzw.italker.fragments.main.GroupFragment;
import com.google.jaaaule.gzw.italker.helper.NavHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, NavHelper.OnTabChangedListener<Integer> {
    @BindView(R.id.abl_appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.fl_container)
    FrameLayout mContainerLayout;
    @BindView(R.id.pv_portrait)
    PortraitView mPortrait;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.iv_search)
    ImageView mSearch;
    @BindView(R.id.bnv_navigation)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.btn_action)
    FloatActionButton mActionBtn;
    private NavHelper<Integer> mNavHelper;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        mNavHelper = new NavHelper<>(this,
                getSupportFragmentManager(),
                R.id.fl_container,
                this);

        mNavHelper.add(R.id.action_home,
                new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_group,
                        new NavHelper.Tab<>(GroupFragment.class, R.string.title_group))
                .add(R.id.action_contact,
                        new NavHelper.Tab<>(ContactFragment.class, R.string.action_contact));

        //  从底部导航栏拿到到 Menu
        Menu menu = mBottomNavigation.getMenu();
        //  手动触发第一次点击 选中 Home
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });

        mBottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @OnClick(R.id.iv_search)
    void onSearchMenuClick() {

    }

    @OnClick(R.id.btn_action)
    void onActionClick() {
        AccountActivity.show(this);
    }

    /**
     * 底部导航栏被点击时触发
     *
     * @param item
     * @return 返回 True 说明我们已经处理
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //  返回 True 切换换下面的被点击的导航
        //  将事件流转到工具类中
        return mNavHelper.performClickMenu(item.getItemId());
    }

    /**
     * NavHelper 处理后的回调
     *
     * @param oldTab
     * @param newTab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> oldTab, NavHelper.Tab<Integer> newTab) {
        //  从额外字段中取出 Title 资源 Id
        mTitle.setText(newTab.extra);

        //  对浮动按钮进行隐藏与显示的动画
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_home)) {
            //  主界面时隐藏
            transY = Ui.dipToPx(getResources(), 76);
        } else if (Objects.equals(newTab.extra, R.string.title_group)) {
            mActionBtn.setImageResource(R.drawable.ic_group_add);
            rotation = -360;
        } else {
            mActionBtn.setImageResource(R.drawable.ic_contact_add);
            rotation = 360;
        }

        // 开始动画
        // 旋转，Y轴位移，弹性插值器，时间
        mActionBtn.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(480)
                .start();

    }
}
