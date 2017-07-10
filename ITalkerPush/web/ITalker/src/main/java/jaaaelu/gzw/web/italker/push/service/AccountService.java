package jaaaelu.gzw.web.italker.push.service;

import com.google.common.base.Strings;
import jaaaelu.gzw.web.italker.push.bean.api.account.AccountRspModel;
import jaaaelu.gzw.web.italker.push.bean.api.account.LoginModel;
import jaaaelu.gzw.web.italker.push.bean.api.account.RegisterModel;
import jaaaelu.gzw.web.italker.push.bean.api.base.ResponseModel;
import jaaaelu.gzw.web.italker.push.bean.card.UserCard;
import jaaaelu.gzw.web.italker.push.bean.db.User;
import jaaaelu.gzw.web.italker.push.factory.UserFactory;
import jaaaelu.gzw.web.italker.push.utils.Hib;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static jaaaelu.gzw.web.italker.push.factory.UserFactory.findByName;
import static jaaaelu.gzw.web.italker.push.factory.UserFactory.findByPhone;
import static jaaaelu.gzw.web.italker.push.factory.UserFactory.findByToken;

/**
 * Created by admin on 2017/5/18.
 */
//  实际路径 127.0.0.1/api/account/...
@Path("/account")
public class AccountService extends BaseService {

    @POST
    @Path("/login")
    //  接收 JSON 返回也是 JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel login) {
        if (LoginModel.check(login)) {
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.login(login.getAccount(), login.getPassword());
        if (user != null) {
            if (!Strings.isNullOrEmpty(user.getPushId())) {
                return bind(user, login.getPushId());
            }
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            return ResponseModel.buildLoginError();
        }
    }

    @POST
    @Path("/bind/{pushId}")
    //  接收 JSON 返回也是 JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * 从请求头中获取 token 字段
     * pushId 从 Url 地址获取
     */
    public ResponseModel<AccountRspModel> bind(@HeaderParam("token") String token,
                                               @PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(token) ||
                Strings.isNullOrEmpty(pushId)) {
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        return bind(self, pushId);
    }

    @POST
    @Path("/register")
    //  接收 JSON 返回也是 JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel register) {
        if (RegisterModel.check(register)) {
            return ResponseModel.buildParameterError();
        }
        User user = findByPhone(register.getAccount().trim());

        if (user != null) {
            //  已有账户
            return ResponseModel.buildHaveAccountError();
        }
        user = findByName(register.getName());
        if (user != null) {
            //  已有姓名
            return ResponseModel.buildHaveNameError();
        }

        //  开始注册逻辑
        user = UserFactory.register(register.getAccount(),
                register.getPassword(),
                register.getName());

        if (user != null) {
            //  如果携带 PushId
            if (!Strings.isNullOrEmpty(user.getPushId())) {
                return bind(user, register.getPushId());
            }
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            return ResponseModel.buildRegisterError();
        }
    }

    private ResponseModel<AccountRspModel> bind(User user, String pushId) {
        //  进行 PushId 绑定的操操作
        user = UserFactory.bindPushId(user, pushId);
        if (user != null) {
            AccountRspModel rspModel = new AccountRspModel(user, true);
            return ResponseModel.buildOk(rspModel);
        }
        //  绑定失败
        return ResponseModel.buildServiceError();
    }
}
