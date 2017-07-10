package jaaaelu.gzw.web.italker.push.service;

import jaaaelu.gzw.web.italker.push.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by admin on 2017/7/5.
 */
public class BaseService {
    //  添加一个上下文注解，自动赋值
    @Context
    protected SecurityContext securityContext;

    protected User getSelf() {
        return (User) securityContext.getUserPrincipal();
    }
}
