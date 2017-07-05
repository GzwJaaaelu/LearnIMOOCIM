package jaaaelu.gzw.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by admin on 2017/6/23.
 */
@Entity
@Table(name = "TB_GROUP")
public class Group {

    //  主键
    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    //  定义 UUID 的生成器为 UUID2，即为 UUID to String
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //  不允许更改，不允许为 null
    @Column(updatable = false, nullable = false)
    private String id;

    //  群名称
    @Column(nullable = false)
    private String name;

    //  描述
    @Column(nullable = false)
    private String description;

    //  群图片
    @Column(nullable = false)
    private String picture;

    //  定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //  群的创建者
    //  optional = false 必须要有一个创建者
    //  fetch = FetchType.EAGER 加载方式为急加载
    //  意味着加载群的时候就必须加载创建者的信息
    //  cascade = CascadeType.ALL 联级级别为 ALL，所有的修改都会更新
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    private User owner;

    @Column(updatable = false, nullable = false, insertable = false)
    private String ownerId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
