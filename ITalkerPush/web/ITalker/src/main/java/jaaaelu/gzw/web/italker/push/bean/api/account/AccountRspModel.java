package jaaaelu.gzw.web.italker.push.bean.api.account;

import com.google.gson.annotations.Expose;
import jaaaelu.gzw.web.italker.push.bean.card.UserCard;
import jaaaelu.gzw.web.italker.push.bean.db.User;

/**
 * 账户部分返回的信息
 * Created by admin on 2017/7/4.
 */
public class AccountRspModel {
    //  用户基本信息
    @Expose
    private UserCard user;
    //  账号
    @Expose
    private String account;
    //  登录成功后获取的 Token
    //  可以通过其获取用户全部信息
    @Expose
    private String token;
    //  是否已经绑定了了设备的 PushId
    @Expose
    private boolean isBind;

    public AccountRspModel(User user, boolean isBind) {
        this.account = user.getPhone();
        this.token = user.getToken();
        this.isBind = isBind;
        this.user = new UserCard(user);
    }

    public AccountRspModel(User user) {
        this(user, false);
    }


    public UserCard getUser() {
        return user;
    }

    public void setUser(UserCard user) {
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
}
