package com.google.jaaaule.gzw.italker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.jaaaule.gzw.factory.Factory;
import com.google.jaaaule.gzw.factory.data.helper.AccountHelper;
import com.google.jaaaule.gzw.factory.persistence.Account;
import com.igexin.sdk.PushConsts;

/**
 * 个推的消息接收器
 * Created by admin on 2017/7/7.
 */

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:
                //  获取设备 ID
                onClientInit(bundle.getString("clientid"));
                Log.i(TAG, "GET_CLIENTID:   " + bundle.getString("clientid"));
                break;
            case PushConsts.GET_MSG_DATA:
                //  常规消息
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                    Log.i(TAG, "GET_MSG_DATA:   " + message);
                    onMessageArrived(message);
                }
                break;
            default:
                Log.i(TAG, "OTHER:   " + bundle.toString());
                break;
        }
    }

    /**
     * 消息送达时
     */
    private void onMessageArrived(String message) {
        Factory.dispatchPush(message);
    }

    /**
     * 当 ID 初始化的时候
     *
     * @param cid
     */
    private void onClientInit(String cid) {
        Account.setPushId(cid);
        if (Account.isLogin()) {
            //  PushId 绑定
            //  没有登录不能绑定 PushId
            AccountHelper.bindPushId(null);
        }
    }
}
