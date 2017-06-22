package jaaaelu.gzw.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by admin on 2017/6/16.
 */
@Entity
@Table(name = "TB_MESSAGE")
public class Message {
    //  字符串类型
    public static final int TYPE_STR = 1;
    //  图片类型
    public static final int TYPE_PIC = 2;
    //  普通文件类型
    public static final int TYPE_FILE = 3;
    //  语音类型
    public static final int TYPE_AUDIO = 4;

    //  主键
    @Id
    @PrimaryKeyJoinColumn
    //  这里不自动生成，由代码写入，也就是由客户端生成
    //  避免复杂的服务端和客户端的映射关系
    //  @GeneratedValue(generator = "uuid")
    //  定义 UUID 的生成器为 UUID2，即为 UUID to String
    //  @GenericGenerator(name = "uuid", strategy = "uuid2")
    //  不允许更改，不允许为 null
    @Column(updatable = false, nullable = false)
    private String id;

    //  内容不允许为空，类型为 TEXT
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    //  附件
    @Column()
    private String attach;

    //  消息类型
    @Column(nullable = false)
    private int type;

    //  发送者不为空
    //  多个消息对应一个发送者
    @JoinColumn(name = "senderId")
    @ManyToOne(optional = false)
    private User sender;

    //  这个字段静静只是为了对应 sender 的数据库字段 senderId
    //  所以不允许手动更新和插入
    @Column(updatable = false, insertable = false)
    private String senderId;

    //  接收者可为空
    @JoinColumn(name = "receiveId")
    @ManyToOne
    private User receive;

    @Column(updatable = false, insertable = false)
    private String receiveId;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceive() {
        return receive;
    }

    public void setReceive(User receive) {
        this.receive = receive;
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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }
}

