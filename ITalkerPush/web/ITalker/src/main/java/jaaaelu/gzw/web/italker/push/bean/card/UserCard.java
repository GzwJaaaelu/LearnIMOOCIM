package jaaaelu.gzw.web.italker.push.bean.card;

import com.google.gson.annotations.Expose;
import jaaaelu.gzw.web.italker.push.bean.db.Group;
import jaaaelu.gzw.web.italker.push.bean.db.User;
import jaaaelu.gzw.web.italker.push.bean.db.UserFollow;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2017/7/4.
 */
public class UserCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String phone;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
    private int sex = 0;
    @Expose
    //  用户关注人的数量
    private int follws;
    @Expose
    //  用户粉丝的数量
    private int follwing;
    @Expose
    //  我与当前用户关系状态
    private boolean isFollow;
    //  用户信息的最后更新时间
    private LocalDateTime modifyAt = LocalDateTime.now();

    public UserCard(final User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.portrait = user.getPortrait();
        this.desc = user.getDescription();
        this.sex = user.getSex();
        this.modifyAt = user.getUpdateAt();

        //  TODO 得到关注人和粉丝的数量
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    public int getFollws() {
        return follws;
    }

    public void setFollws(int follws) {
        this.follws = follws;
    }

    public int getFollwing() {
        return follwing;
    }

    public void setFollwing(int follwing) {
        this.follwing = follwing;
    }

    public boolean getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }
}
