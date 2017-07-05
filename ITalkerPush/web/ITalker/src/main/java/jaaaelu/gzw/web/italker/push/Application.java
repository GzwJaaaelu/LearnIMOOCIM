package jaaaelu.gzw.web.italker.push;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import jaaaelu.gzw.web.italker.push.provider.GsonProvider;
import jaaaelu.gzw.web.italker.push.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * Created by admin on 2017/5/18.
 */
public class Application extends ResourceConfig{

    public Application() {
        //  注册 package
        packages(AccountService.class.getPackage().getName());
        //  注册逻辑处理的包名
        //  包下的
        //  packages("jaaaelu.gzw.web.italker.push.service");
        //  注册 Json 解析器
        //  register(JacksonJsonProvider.class);
        //  替换解析器为 Gson
        register(GsonProvider.class);
        //  注册日志打印器
        register(Logger.class);
    }
}
