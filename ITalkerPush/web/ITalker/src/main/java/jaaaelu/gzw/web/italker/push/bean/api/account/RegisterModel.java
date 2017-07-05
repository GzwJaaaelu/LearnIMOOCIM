package jaaaelu.gzw.web.italker.push.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import jaaaelu.gzw.web.italker.push.bean.api.base.ResponseModel;

/**
 * Created by admin on 2017/7/4.
 */
public class RegisterModel {
    @Expose
    private String account;
    @Expose
    private String password;
    @Expose
    private String name;
    @Expose
    private String pushId;

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 校验
     *
     * @param register
     * @return
     */
    public static boolean check(RegisterModel register) {
        return register != null &&
                Strings.isNullOrEmpty(register.account) &&
                Strings.isNullOrEmpty(register.password)&&
                Strings.isNullOrEmpty(register.name);
    }
}
