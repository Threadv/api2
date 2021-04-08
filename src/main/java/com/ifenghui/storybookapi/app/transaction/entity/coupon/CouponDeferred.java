package com.ifenghui.storybookapi.app.transaction.entity.coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

/**
 * 赠阅券表
 */
@Entity
@Table(name="story_coupon_deferred")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="赠阅券管理",addAble = false)
/**
 * Created by wml on 2017/5/18
 */
public class CouponDeferred {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;


    @Column
//    @CmsColumn(name="名称")
    String name;

    @Column
//    @CmsColumn(name="内容描述")
    String content;

    @Column
//    @CmsColumn(name="共有多少张")
    Integer count;

    @Column
//    @CmsColumn(name="有效期限（类型1单位天，类型2单位月")
    Integer validTime;
    @Column
//    @CmsColumn(name="有效期限（类型1单位天，类型2单位月")
    Integer exceedTime;

    @JsonIgnore
//    @CmsColumn(name="状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;

    @Column
//    @CmsColumn(name="每个用户允许领几次")
    Integer getCount;

    @Column
//    @CmsColumn(name="订阅多少时间赠送此券 单位月")
    Integer subscibeTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Integer getExceedTime() {
        return exceedTime;
    }

    public void setExceedTime(Integer exceedTime) {
        this.exceedTime = exceedTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGetCount() {
        return getCount;
    }

    public void setGetCount(Integer getCount) {
        this.getCount = getCount;
    }

    public Integer getSubscibeTime() {
        return subscibeTime;
    }

    public void setSubscibeTime(Integer subscibeTime) {
        this.subscibeTime = subscibeTime;
    }
}
