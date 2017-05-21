package jaaaelu.gzw.web.italker.push.service;

import jaaaelu.gzw.web.italker.push.bean.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by admin on 2017/5/18.
 */
//  实际路径 127.0.0.1/api/account/...
@Path("/account")
public class AccountService {

    //  实际路径 127.0.0.1/api/account/login    并且是 GET 形式访问
    @GET
    @Path("/login")
    public String get() {
        return "Jaaaelu gets the login.";
    }

    @POST
    @Path("/login")
    //  接收 JSON 返回也是 JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        return new User("Jaaaelu", 2);
    }
}
