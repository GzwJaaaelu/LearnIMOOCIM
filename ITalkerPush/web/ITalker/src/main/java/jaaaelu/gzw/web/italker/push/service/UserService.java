package jaaaelu.gzw.web.italker.push.service;

import com.google.common.base.Strings;
import jaaaelu.gzw.web.italker.push.bean.api.account.AccountRspModel;
import jaaaelu.gzw.web.italker.push.bean.api.base.ResponseModel;
import jaaaelu.gzw.web.italker.push.bean.api.user.UpdateInfoModel;
import jaaaelu.gzw.web.italker.push.bean.card.UserCard;
import jaaaelu.gzw.web.italker.push.bean.db.User;
import jaaaelu.gzw.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import static jaaaelu.gzw.web.italker.push.factory.UserFactory.findByToken;


/**
 * Created by admin on 2017/7/5.
 */
//  实际路径 127.0.0.1/api/user/...
@Path("/user")
public class UserService extends BaseService {

    @PUT
    //  @Path("/update")
    //  实际路径 127.0.0.1/api/user  就是当前目录
    //  接收 JSON 返回也是 JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel updateInfo) {
        if (!UpdateInfoModel.check(updateInfo)) {
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        //  更新用户信息
        self = updateInfo.updateToUser(self);
        //  更新到数据库
        self = UserFactory.update(self);
        return ResponseModel.buildOk(new UserCard(self, true));

    }
}
