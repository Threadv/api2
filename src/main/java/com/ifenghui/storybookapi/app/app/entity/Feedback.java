package com.ifenghui.storybookapi.app.app.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.user.entity.User;
//import com.weisiliang.cms.annotation.CmsColumn;
//import com.weisiliang.cms.annotation.CmsTable;
//import com.weisiliang.cms.table.CmsInputType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
/**
 * Created by jia on 2016/12/22.
 * 用户反馈表
 */
@Entity
@Table(name="story_feedback")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
//@CmsTable(name="用户反馈记录",addAble = false,editAble = false,deleteAble = false)
public class Feedback {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

//    @CmsColumn(name="内容")
    String content;

    //回复
    String contact;

    //阅读状态：0 未读；1 已读
    Integer readType;

    //处理状态 0 未处理 1已处理 ;2 删除
    Integer status;

    //回复
    String remark;

    @JsonIgnore
    Long userId;
//    @CmsColumn(name="联系方式")
    @Column(name="relation")
    String contactInfo;
//    @CmsColumn(name="版本")
    String ver;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRemark() {
        return remark;
    }

    public void setReadType(String remark) {
        this.remark = remark;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTimeFormat(){
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getReadType() {
        return readType;
    }

    public void setReadType(Integer readType) {
        this.readType = readType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
