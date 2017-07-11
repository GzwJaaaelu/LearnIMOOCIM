package com.google.jaaaule.gzw.factory.model.api.account;

import com.google.jaaaule.gzw.factory.model.db.User;

/**
 * Created by admin on 2017/7/7.
 */

public class AccountRspModel {
    //  用户基本信息
    private User user;
    //  账号
    private String account;
    //  登录成功后获取的 Token
    //  可以通过其获取用户全部信息
    private String token;
    //  是否已经绑定了了设备的 PushId
    private boolean isBind;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }

    @Override
    public String toString() {
        return "AccountRspModel{" +
                "user=" + user +
                ", account='" + account + '\'' +
                ", token='" + token + '\'' +
                ", isBind=" + isBind +
                '}';
    }
}
