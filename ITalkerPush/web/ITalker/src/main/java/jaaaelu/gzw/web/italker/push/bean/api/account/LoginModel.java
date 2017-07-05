package jaaaelu.gzw.web.italker.push.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * Created by admin on 2017/7/4.
 */
public class LoginModel {
    @Expose
    private String account;
    @Expose
    private String password;
    @Expose
    private String pushId;

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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    /**
     * 校验
     *
     * @param login
     * @return
     */
    public static boolean check(LoginModel login) {
        return login != null &&
                Strings.isNullOrEmpty(login.account) &&
                Strings.isNullOrEmpty(login.password);
    }
}
