package jaaaelu.gzw.web.italker.push.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2017/6/15.
 */
@Entity
@Table(name = "TB_USER")
public class User implements Principal {

    //  主键
    @Id
    @PrimaryKeyJoinColumn
    //  主键生成的存储类型为 UUID
    @GeneratedValue(generator = "uuid")
    //  定义 UUID 的生成器为 UUID2，即为 UUID to String
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //  不允许更改，不允许为 null
    @Column(updatable = false, nullable = false)
    private String id;

    //  用户名唯一
    @Column(nullable = false, length = 128, unique = true)
    private String name;

    //  电话唯一
    @Column(length = 62, unique = true)
    private String phone;

    //  密码不为空
    @Column(nullable = false)
    private String password;

    @Column
    private String portrait;

    @Column
    private String description;

    //  性别有初始值，所以不为空
    @Column(nullable = false)
    private int sex = 0;

    //  token 可以拉去用户信息，所以必须为宜
    @Column(unique = true)
    private String token;

    //  用于推送的设备 ID
    @Column
    private String pushId;

    //  定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //  最后一次收到消息的时间
    @Column(nullable = false)
    private LocalDateTime lastReceiveAt = LocalDateTime.now();

    //  我关注的人的列表
    //  对应的数据库表中的字段为 TB_USER_FOLLOW.originId
    @JoinColumn(name = "originId")
    //  因为数据可能很多，不想在拿到 User 信息时就拿到它，所以采用懒加载
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> following = new HashSet<>();

    //  关注我的人
    //  对应的数据库表中的字段为 TB_USER_FOLLOW.targetId
    @JoinColumn(name = "targetId")
    //  因为数据可能很多，不想在拿到 User 信息时就拿到它，所以采用懒加载
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<>();

    //  自己所有创建的群
    //  对应字段为 Group.ownerId
    //  懒加载，加载用户时，不加载这个集合
    @JoinColumn(name = "ownerId")
    //  尽可能不加载具体数据，只访问 groups.size() 仅仅查询数量，不加载具体集合
    //  只有当遍历集合的时候才加载具体的数据
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getLastReceiveAt() {
        return lastReceiveAt;
    }

    public void setLastReceiveAt(LocalDateTime lastReceiveAt) {
        this.lastReceiveAt = lastReceiveAt;
    }

    public Set<UserFollow> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserFollow> following) {
        this.following = following;
    }

    public Set<UserFollow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollow> followers) {
        this.followers = followers;
    }
}
