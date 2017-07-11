package com.google.jaaaule.gzw.factory.presenter.user;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;

import com.google.jaaaule.gzw.factory.Factory;
import com.google.jaaaule.gzw.factory.R;
import com.google.jaaaule.gzw.factory.data.DataSource;
import com.google.jaaaule.gzw.factory.data.helper.UserHelper;
import com.google.jaaaule.gzw.factory.model.api.user.UserUpdateModel;
import com.google.jaaaule.gzw.factory.model.card.UserCard;
import com.google.jaaaule.gzw.factory.model.db.User;
import com.google.jaaaule.gzw.factory.net.UploadHelper;
import com.google.jaaaule.gzw.factory.presenter.BasePresenter;


/**
 * Created by admin on 2017/7/10.
 */

public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View> implements UpdateInfoContract.Presenter, DataSource.Callback<UserCard> {
    private static final String TAG = UpdateInfoPresenter.class.getSimpleName();

    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(String photoFilePath, String desc, boolean isMan) {
        start();

        UpdateInfoContract.View view = getView();

        if (TextUtils.isEmpty(photoFilePath) || TextUtils.isEmpty(desc)) {
            view.showError(R.string.data_account_update_invalid_parameter);
        } else {
            UserUpdateModel update = new UserUpdateModel("", photoFilePath,
                    desc,
                    isMan ? User.SEX_MAN : User.SEX_WOMAN);
            //  先上传头像
            uploadPortraitAndUpdateInfo(photoFilePath, update);
        }
    }

    /**
     * 上传头像和更新信息
     * @param photoFilePath
     * @param updateInfo
     */
    private void uploadPortraitAndUpdateInfo(final String photoFilePath, final UserUpdateModel updateInfo) {
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                String url = UploadHelper.uploadPortrait(photoFilePath);
                if (TextUtils.isEmpty(url)) {
                    getView().showError(R.string.data_upload_error);
                } else {
                    UserHelper.update(updateInfo, UpdateInfoPresenter.this);
                }
                Log.e(TAG, url);
            }
        });
    }

    @Override
    public void onDataLoaded(UserCard data) {
        getView().updateSucceed();
    }

    @Override
    public void onDataNotAvailable(@StringRes int str) {
        getView().showError(R.string.data_network_error);
    }
}
