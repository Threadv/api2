package com.ifenghui.storybookapi.app.transaction.entity.vip;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.SvipStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by jia on 2016/12/23.
 * 限定时间用户，可以让用户在一段时间内查看某个类型的作品，无需单独购买。
 */
@Entity
@Table(name="story_user_svip")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="Token",addAble = false,editAble = false,deleteAble = false)
public class UserSvip {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    Long userId;

    Integer type;

    @Transient
    Integer days;

    //    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date startTime;
    //    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date endTime;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
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

    public Integer getType() {
        return type;
    }

    public void setSvipStyle(SvipStyle type){
        this.type = type.getId();
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
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
}
