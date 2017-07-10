package jaaaelu.gzw.web.italker.push.provider;

import com.google.common.base.Strings;
import jaaaelu.gzw.web.italker.push.bean.api.base.ResponseModel;
import jaaaelu.gzw.web.italker.push.bean.db.User;
import jaaaelu.gzw.web.italker.push.service.AccountService;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

import static jaaaelu.gzw.web.italker.push.factory.UserFactory.findByToken;

/**
 * 用于所有请求的拦截
 * Created by admin on 2017/7/5.
 */
@Provider
public class AuthRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //  检测是否是登录 / 注册
        String relationPath = ((ContainerRequest) requestContext).getPath(false);
        if (relationPath.startsWith("account/login") ||
                relationPath.startsWith("account/register")) {
            return;
        }

        //  获取 Token
        String token = requestContext.getHeaders().getFirst("token");
        if (!Strings.isNullOrEmpty(token)) {
            //  查询自己的信息
            final User self = findByToken(token);

            if (self != null) {
                //  给当前请求添加一个上下文
                requestContext.setSecurityContext(new SecurityContext() {
                    //   主体部分
                    @Override
                    public Principal getUserPrincipal() {
                        return self;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        //   可以在这里写入用户的权限
                        //   可以管理管理员权限等等
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        //  默认 false 即可
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        //  不管这里
                        return null;
                    }
                });
                return;
            }
        }

        Response response = Response.status(Response.Status.OK)
                .entity(ResponseModel.buildAccountError())
                .build();

        //  停止一个请求继续下发，不会走到 Sevice 中
        requestContext.abortWith(response);
    }
}
