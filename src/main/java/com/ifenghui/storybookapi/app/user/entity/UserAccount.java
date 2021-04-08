package com.ifenghui.storybookapi.app.user.entity;

/**
 * Created by jia on 2016/12/23.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.ifenghui.storybookapi.style.UserAccountStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户的第三方账户记录表
 */
@Entity
@Table(name="story_user_account")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="第三方登录账号",addAble = false,editAble = false,deleteAble = false)
public class UserAccount implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

//    Long userId;

//    @CmsColumn(name="三方id")
    String srcId;

//    @CmsColumn(name="来源",dataType = CmsDataType.MAP,enumClassName = AccountType.class)
    Integer srcType;

//    @CmsColumn(name="uionid")
//    String uionid;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;


    Long userId;


    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user!=null){
            this.userId=user.getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public Integer getSrcType() {
        return srcType;
    }


    public void setAccountStyle(UserAccountStyle accountStyle){
        this.srcType=accountStyle.getId();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
