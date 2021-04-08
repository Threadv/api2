package com.ifenghui.storybookapi.app.user.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户信息（8月活动）
 */
@Entity
@Table(name="story_user_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="用户信息（8月活动）",addAble = false)
/**
 * Created by wml on 2017/8/8
 */
public class UserInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;


    Long userId;

    //    @CmsColumn(name="用户名称")
    String name;

//    @CmsColumn(name="手机")
    String phone;

    //    @CmsColumn(name="地址")
    String addr;

//    @CmsColumn(name="创建时间")
    Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
