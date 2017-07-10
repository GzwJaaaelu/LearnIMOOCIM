package com.google.jaaaule.gzw.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.jaaaule.gzw.factory.Factory;
import com.google.jaaaule.gzw.factory.model.api.AccountRspModel;
import com.google.jaaaule.gzw.factory.model.db.User;
import com.google.jaaaule.gzw.factory.model.db.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by admin on 2017/7/7.
 */

public class Account {
    private static final String KEY_PUSH_ID = "key_push_id";
    private static final String KEY_IS_BIND = "key_is_bind";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_USER_ID = "key_user_id";
    private static final String KEY_ACCOUNT = "key_account";
    //  设备推送 ID
    private static String sPushId;
    //  设备 ID 是否已经绑定到了服务器
    private static boolean sIsBind;
    private static String sToken;
    private static String sUserId;
    //  登录的账户
    private static String sAccount;

    public static String getPushId() {
        return sPushId;
    }

    public static void setPushId(String pushId) {
        Account.sPushId = pushId;
        //  存储
        save(Factory.getApplicationContext());
    }

    public static String getToken() {
        return sToken;
    }

    public static String getUserId() {
        return sUserId;
    }

    public static String getAccount() {
        return sAccount;
    }

    /**
     * 存储 PushId
     */
    private static void save(Context context) {
        SharedPreferences sp = getSp(context);
        sp.edit()
                .putString(KEY_PUSH_ID, sPushId)
                .putBoolean(KEY_IS_BIND, sIsBind)
                .apply();
    }

    public static void load(Context context) {
        SharedPreferences sp = getSp(context);
        sPushId = sp.getString(KEY_PUSH_ID, "");
        sIsBind = sp.getBoolean(KEY_IS_BIND, false);
        sToken = sp.getString(KEY_TOKEN, "");
        sUserId = sp.getString(KEY_USER_ID, "");
        sAccount = sp.getString(KEY_ACCOUNT, "");
    }

    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(Account.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    /**
     * 当前账号是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(sUserId) &&
                !TextUtils.isEmpty(sToken);
    }

    /**
     * 用户信息是否完善
     *
     * @return
     */
    public static boolean isComlete() {
        //  TODO
        return isLogin();
    }

    /**
     * 是否已经绑定到了服务器
     *
     * @return
     */
    public static boolean isBind() {
        return sIsBind;
    }

    /**
     * 设置绑定状态
     *
     * @param isBind
     */
    public static void setBind(boolean isBind) {
        Account.sIsBind = isBind;
        save(Factory.getApplicationContext());
    }

    /**
     * 保存自己的信息 ID、TOKEN
     *
     * @param user
     */
    public static void login(AccountRspModel user) {
        //  ID、Token
        Account.sToken = user.getToken();
        Account.sUserId = user.getUser().getId();
        Account.sAccount = user.getAccount();
        save(Factory.getApplicationContext());
    }

    /**
     * 当前登录的用户信息
     * @return
     */
    public static User getUser() {
        return TextUtils.isEmpty(sUserId) ?
                new User() :
                SQLite.select()
                        .from(User.class)
                        .where(User_Table.id.eq(sUserId))
                        .querySingle();
    }
}
