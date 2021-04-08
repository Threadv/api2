package com.ifenghui.storybookapi.app.transaction.entity.subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 订阅记录（旧 已不错产生记录）
 */
@Entity
@Table(name="story_subscription_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="订阅记录",addAble = false,editAble = false,deleteAble = false)
/**
 * Created by wml on 2017/2/16.
 */
public class SubscriptionRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
//    Long storyId;
//    Long userId;

//    @CmsColumn(name="状态",enumClassName = OrderStatusType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    Long userId;

    @JsonIgnore
    Integer status;
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;
//    @CmsColumn(name="开始时间",inputType = CmsInputType.DATE)
    Date startTime;
//    @CmsColumn(name="结束时间",inputType = CmsInputType.DATE)
    Date endTime;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    @JsonIgnore
    Date createTime;
    @Transient
    String intro;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user != null){
            this.userId = user.getId();
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
