package com.google.jaaaule.gzw.italker.fragments.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.jaaaule.gzw.common.app.BaseFragment;
import com.google.jaaaule.gzw.common.app.ITalkerApplication;
import com.google.jaaaule.gzw.common.widget.PortraitView;
import com.google.jaaaule.gzw.italker.R;
import com.google.jaaaule.gzw.italker.fragments.media.GalleyFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends BaseFragment implements GalleyFragment.OnSelectedListener {
    @BindView(R.id.pv_portrait)
    PortraitView mPortrait;

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
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载头像
     * @param resultUri
     */
    private void loadPortrait(Uri resultUri) {
        Glide.with(this)
                .load(resultUri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
    }
}
