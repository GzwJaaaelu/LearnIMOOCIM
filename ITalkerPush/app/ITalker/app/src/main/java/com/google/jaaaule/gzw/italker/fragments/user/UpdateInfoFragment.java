package com.google.jaaaule.gzw.italker.fragments.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.jaaaule.gzw.common.app.ITalkerApplication;
import com.google.jaaaule.gzw.common.app.PresenterFragment;
import com.google.jaaaule.gzw.common.widget.PortraitView;
import com.google.jaaaule.gzw.factory.presenter.user.UpdateInfoContract;
import com.google.jaaaule.gzw.factory.presenter.user.UpdateInfoPresenter;
import com.google.jaaaule.gzw.italker.App;
import com.google.jaaaule.gzw.italker.R;
import com.google.jaaaule.gzw.italker.activities.MainActivity;
import com.google.jaaaule.gzw.italker.fragments.media.GalleyFragment;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.EditText;
import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends PresenterFragment<UpdateInfoContract.Presenter>
        implements GalleyFragment.OnSelectedListener, UpdateInfoContract.View {
    private static final String TAG = UpdateInfoFragment.class.getSimpleName();
    @BindView(R.id.pv_portrait)
    PortraitView mPortrait;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.iv_sex)
    ImageView mSex;
    @BindView(R.id.et_desc)
    EditText mDesc;
    @BindView(R.id.btn_submit)
    Button mSubmitBtn;
    @BindView(R.id.loading)
    Loading mLoading;
    private String mPortraitFilePath;
    private boolean mIsMan;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.pv_portrait)
    public void onViewClicked() {
        onPortraitClick();
    }

    private void onPortraitClick() {
        GalleyFragment fragment = new GalleyFragment();
        fragment.setListener(this)
                //  建议使用 getChildFragmentManager()，因为在 Fragment 中
                .show(getChildFragmentManager(), GalleyFragment.class.getName());
    }

    @Override
    public void onSelectedImage(String path) {
        UCrop.Options options = new UCrop.Options();
        //  设置图片处理格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //  设置图片压缩后的图片精度
        options.setCompressionQuality(96);
        //  图片缓存地址
        File dPath = ITalkerApplication.getPortraitTmpFile();
        //  发起剪切
        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                //  1:1 的比例
                .withAspectRatio(1, 1)
                //  最大的尺寸
                .withMaxResultSize(520, 520)
                //  相关参数
                .withOptions(options)
                .start(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  并不会直接回调到这里，而不是回调到 Activity 的 onActivityResult
        //  收到 Activity 传递过来的回调，然后进行处理
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            App.showToast(R.string.data_rsp_error_unknown);
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载头像
     *
     * @param resultUri
     */
    private void loadPortrait(Uri resultUri) {
        //  得到头像地址
        mPortraitFilePath = resultUri.getPath();

        Glide.with(this)
                .load(resultUri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
    }

    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        return new UpdateInfoPresenter(this);
    }

    @Override
    public void updateSucceed() {
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @OnClick({R.id.btn_submit, R.id.iv_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                onSubmitClick();
                break;
            case R.id.iv_sex:
                onSexClick();
                break;
        }
    }

    /**
     * 修改性别
     */
    private void onSexClick() {
        mIsMan = !mIsMan;

        Drawable drawable = getResources().getDrawable(mIsMan ? R.drawable.ic_sex_man : R.drawable.ic_sex_woman);
        mSex.setImageDrawable(drawable);
        //  设置背景层级
        mSex.getBackground().setLevel(mIsMan ? 0 : 1);
    }

    private void onSubmitClick() {
        String desc = mDesc.getText().toString();
        mPresenter.update(mPortraitFilePath, desc, mIsMan);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoading.start();
        //  界面不可操作
        setViewEnable(false);
    }

    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        //  停止 Loading
        mLoading.stop();
        //  界面恢复操作
        setViewEnable(true);
    }

    /**
     * 设置控件是否可用
     *
     * @param enable
     */
    private void setViewEnable(boolean enable) {
        mPortrait.setEnabled(enable);
        mDesc.setEnabled(enable);
        mSex.setEnabled(enable);
        mSubmitBtn.setEnabled(enable);
    }
}
