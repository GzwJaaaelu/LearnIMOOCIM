package jaaaelu.gzw.web.italker.push.factory;

import com.google.common.base.Strings;
import jaaaelu.gzw.web.italker.push.bean.db.User;
import jaaaelu.gzw.web.italker.push.utils.Hib;
import jaaaelu.gzw.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2017/7/4.
 */
public class UserFactory {


    /**
     * 按照 Token 查询
     *
     * @param token
     * @return
     */
    public static User findByToken(String token) {
        return Hib.query(session -> (User) session.createQuery("from User where token = :inToken")
                .setParameter("inToken", token)
                .uniqueResult());
    }

    /**
     * 按照手机号查询
     *
     * @param phone
     * @return
     */
    public static User findByPhone(String phone) {
        return Hib.query(session -> (User) session.createQuery("from User where phone = :inPhone")
                .setParameter("inPhone", phone)
                .uniqueResult());
    }

    /**
     * 按照姓名查询
     *
     * @param name
     * @return
     */
    public static User findByName(String name) {
        return Hib.query(session -> (User) session.createQuery("from User where name = :inName")
                .setParameter("inName", name)
                .uniqueResult());
    }

    /**
     * 用户注册
     * 注册的操作需要写入数据库，并返回数据库的 User
     *
     * @param account
     * @param password
     * @param name
     * @return
     */
    public static User register(String account, String password, String name) {
        password = encdoePassword(password);
        User user = createuser(account.trim(), password, name);

        if (user != null) {
            user = login(user);
        }

        return user;
    }

    /**
     * 注册部分的新建用户逻辑
     *
     * @param account
     * @param password
     * @param name
     * @return
     */
    private static User createuser(String account, String password, String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhone(account.trim());

        //  数据库存储
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }

    private static String encdoePassword(String password) {
        password = password.trim();
        //  进行 MD5 非对称加密
        password = TextUtil.getMD5(password);
        //  进行一次对称的 Base64 加密
        return TextUtil.encodeBase64(password);
    }

    /**
     * 用户登录
     * 本质上就是对 Token 进行操作
     *
     * @param user
     * @return
     */
    public static User login(User user) {
        String newToken = UUID.randomUUID().toString();
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);
        return update(user);
    }

    /**
     * @param account
     * @param password
     * @return
     */
    public static User login(String account, String password) {
        String encodePassword = encdoePassword(password);
        //  按照手机号和密码进行匹配
        User user = Hib.query(session -> (User) session
                .createQuery("from User where phone = :inPhone and password = :inPassword")
                .setParameter("inPhone", account.trim())
                .setParameter("inPassword", encodePassword)
                .uniqueResult());

        if (user != null) {
            user = login(user);
        }
        return user;
    }


    /**
     * 更新用户到信息到数据库
     *
     * @param user
     * @return
     */
    public static User update(User user) {
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }


    /**
     * 给当前用户绑定 PushId
     *
     * @param user
     * @param pushId
     * @return
     */
    public static User bindPushId(User user, String pushId) {
        if (Strings.isNullOrEmpty(pushId)) {
            return null;
        }
        //  第一步，查询是否有其他用户绑定了这个设备
        //  取消绑定，避免推送混乱
        //  查询列表不包括自己
        Hib.queryOnly(session -> {
            List<User> users = session
                    .createQuery("from User where lower(pushId) = :inPushId and id != :userId")
                    .setParameter("inPushId", pushId.toLowerCase())
                    .setParameter("userId", user.getId())
                    .list();
            for (User u : users) {
                //  更新为 null
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });
        if (pushId.equalsIgnoreCase(user.getPushId())) {
            //  如果当前需要绑定的设备 Id，之前已经绑了
            //  那么就不需要额外绑定
            return user;
        } else {
            //  如果当前账户之前的设备 Id 和需要绑定的不同
            //  那么需要单点登录，让之前的设备退出账户
            //  给之前的设备推送一条退出消息
            if (Strings.isNullOrEmpty(user.getPushId())) {
                //  TODO 推送一个退出消息
            }
            user.setPushId(pushId);
        }
        return update(user);
    }
}
