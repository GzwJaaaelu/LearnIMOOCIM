package jaaaelu.gzw.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by admin on 2017/6/24.
 */
@Entity
@Table(name = "TB_PUSH_HISTORY")
public class PushHistory {

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

    //  推送的具体实体，存储都是 Json 字符串
    //  BLOB 是比 TEXT 更大一个大字段类型
    @Lob
    @Column(nullable = false, columnDefinition = "BLOB")
    private String entity;

    //  实体类型
    @Column(nullable = false)
    private int entityType;

    //  接收者
    //  一个接受者可以接收多条消息
    //  加载一条推送消息的时候直接加载
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "receiverId")
    private User receiver;

    @Column(nullable = false, updatable = false, insertable = false)
    private String receiverId;

    //  接受者当前状态下的设备推送 ID
    //  对应 User.pushId
    @Column
    private String receiverPushId;

    //  发送者可为空，因为可能是系统发来的消息
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "senderId")
    private User sender;

    @Column(updatable = false, insertable = false)
    private String senderId;

    //  定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column()
    private LocalDateTime arriveAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverPushId() {
        return receiverPushId;
    }

    public void setReceiverPushId(String receiverPushId) {
        this.receiverPushId = receiverPushId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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

    public LocalDateTime getArriveAt() {
        return arriveAt;
    }

    public void setArriveAt(LocalDateTime arriveAt) {
        this.arriveAt = arriveAt;
    }
}
