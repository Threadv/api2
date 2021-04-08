package com.ifenghui.storybookapi.app.transaction.entity.coupon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_coupon_get_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="领取优惠券记录",addAble = false,editAble = false,deleteAble = false)
/**
 * app上线优惠券领取活动
 * 分享大使的记录，用于保存手机号
 * share分享大使
 * Created by wml on 2017/5/19.
 */
public class CouponGetRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
//    @CmsColumn(name="用户id")
    Long userId;
//    @CmsColumn(name="领取者手机")
    String phone;
//    @JsonIgnore
//    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId",insertable = true, updatable = true)
//    @NotFound(action= NotFoundAction.IGNORE)
//    User user;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

    Integer status;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
