package com.google.jaaaule.gzw.factory;

import android.app.Application;
import android.support.annotation.StringRes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.jaaaule.gzw.common.app.ITalkerApplication;
import com.google.jaaaule.gzw.factory.data.DataSource;
import com.google.jaaaule.gzw.factory.model.api.RspModel;
import com.google.jaaaule.gzw.factory.persistence.Account;
import com.google.jaaaule.gzw.factory.utils.DBFlowExclusionStrategies;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2017/6/14.
 */

public class Factory {
    private static final Factory sInstance;
    private final Executor mExecutor;
    private final Gson mGson;

    static {
        sInstance = new Factory();
    }

    private Factory() {
        //  新建一个 4 个线程的线程池
        mExecutor = Executors.newFixedThreadPool(4);

        mGson = new GsonBuilder()
                //  设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .setExclusionStrategies(new DBFlowExclusionStrategies())
                .create();
    }

    public static Application getApplicationContext() {
        return ITalkerApplication.getInstance();
    }

    /**
     * 异步运行的方法
     */
    public static void runOnAsync(Runnable runnable) {
        sInstance.mExecutor.execute(runnable);
    }

    public static Gson getGson() {
        return sInstance.mGson;
    }

    /**
     * 进行错误数据的解析
     *
     * @param model
     * @param failedCallback
     */
    public static void decodeRepModel(RspModel model, DataSource.FailedCallback failedCallback) {
        if (model == null) {
            return;
        }
        switch (model.getCode()) {
            case RspModel.SUCCEED:
                return;
            case RspModel.ERROR_SERVICE:
                decodeRspErrorCode(R.string.data_rsp_error_service, failedCallback);
                break;
            case RspModel.ERROR_NOT_FOUND_USER:
                decodeRspErrorCode(R.string.data_rsp_error_not_found_user, failedCallback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP:
                decodeRspErrorCode(R.string.data_rsp_error_not_found_group, failedCallback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP_MEMBER:
                decodeRspErrorCode(R.string.data_rsp_error_not_found_group_member, failedCallback);
                break;
            case RspModel.ERROR_CREATE_USER:
                decodeRspErrorCode(R.string.data_rsp_error_create_user, failedCallback);
                break;
            case RspModel.ERROR_CREATE_GROUP:
                decodeRspErrorCode(R.string.data_rsp_error_create_group, failedCallback);
                break;
            case RspModel.ERROR_CREATE_MESSAGE:
                decodeRspErrorCode(R.string.data_rsp_error_create_message, failedCallback);
                break;
            case RspModel.ERROR_PARAMETERS:
                decodeRspErrorCode(R.string.data_rsp_error_parameters, failedCallback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_ACCOUNT:
                decodeRspErrorCode(R.string.data_rsp_error_parameters_exist_account, failedCallback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_NAME:
                decodeRspErrorCode(R.string.data_rsp_error_parameters_exist_name, failedCallback);
                break;
            case RspModel.ERROR_ACCOUNT_TOKEN:
                ITalkerApplication.showToast(R.string.data_rsp_error_account_token);
                sInstance.loginOut();
                break;
            case RspModel.ERROR_ACCOUNT_LOGIN:
                decodeRspErrorCode(R.string.data_rsp_error_account_login, failedCallback);
                break;
            case RspModel.ERROR_ACCOUNT_REGISTER:
                decodeRspErrorCode(R.string.data_rsp_error_account_register, failedCallback);
                break;
            case RspModel.ERROR_ACCOUNT_NO_PERMISSION:
                decodeRspErrorCode(R.string.data_rsp_error_account_no_permission, failedCallback);
                break;
            case RspModel.ERROR_UNKNOWN:
            default:
                decodeRspErrorCode(R.string.data_rsp_error_unknown, failedCallback);
                break;
        }
    }

    /**
     * 账号退出登录
     */
    private void loginOut() {

    }

    /**
     * 进行回调与判空
     *
     * @param str
     * @param failedCallback
     */
    public static void decodeRspErrorCode(@StringRes int str, DataSource.FailedCallback failedCallback) {
        if (failedCallback != null) {
            failedCallback.onDataNotAvailable(R.string.data_rsp_error_service);
        }
    }

    /**
     * 处理推送来的消息
     *
     * @param message
     */
    public static void dispatchPush(String message) {

    }

    /**
     * Factory 的初始化
     */
    public static void setUp() {
        //  初始化数据库
        FlowManager.init(new FlowConfig.Builder(getApplicationContext())
                //  数据库初始化的时候就打开数据库
                .openDatabasesOnInit(true)
                .build());
        //  获取 PushId
        Account.load(getApplicationContext());
    }
}
