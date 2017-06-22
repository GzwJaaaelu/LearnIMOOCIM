package jaaaelu.gzw.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户关系的 Model
 * 用于用户直接进行好友关系的实现
 * Created by admin on 2017/6/16.
 */
@Entity
@Table(name = "TB_USER_FOLLOW")
public class UserFollow {

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

    //  定义一个发起人，你关注某人，这里你就是发起人
    //  多对一，也就是说你可以关注很多人
    //  你的每一次关注都是一条记录
    //  你可以创建很多个关注信息，所以也就是多对一
    //  这里的多对一就是，一个 User 对应多个 UserFollow
    //  optional = false 表示一条关注记录一定要有一个关注记录
    //  也就是一定要有一个“你”
    @ManyToOne(optional = false)
    //  定义关联表的字段名为 originId
    //  对应 User 表中的 Id
    //  定义的是数据中的存储字段
    @JoinColumn(name = "originId")
    private User origin;
    //  把这个列提取到我们的 Model 中，便于后边使用
    @Column(nullable = false, updatable = false, insertable = false)
    private String originId;

    //  关注的目标，也就是被关注的人
    //  也是多对一，你可以被很多人关注，每次关注都是一条记录
    //  所以就是多个 Follow 对应 个 User
    @ManyToOne(optional = false)
    @JoinColumn(name = "targetId")
    private User target;
    //  把这个列提取到我们的 Model 中，便于后边使用
    //  无允许为空，无允许更新、插入
    @Column(nullable = false, updatable = false, insertable = false)
    private String targetId;

    //  对 Target 的备注
    @Column
    private String alias;

    //  定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
}
