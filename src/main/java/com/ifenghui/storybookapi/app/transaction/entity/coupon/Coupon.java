package com.ifenghui.storybookapi.app.transaction.entity.coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 优惠券
 */
@Entity
@Table(name="story_coupon")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="优惠券管理",addAble = false)
/**
 * Created by wml on 2017/5/18
 */
public class Coupon {

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
//    @CmsColumn(name="最大使用次数0为可无限使用")
    Integer count;
    @Column
//    @CmsColumn(name="面值（单位分）")
    Integer amount;

    @Column
//    @CmsColumn(name="有效期限（类型1单位天，类型2单位月")
    Integer validTime;

    @Column
//    @CmsColumn(name="有效期限（类型1单位天，类型2单位月")
    Integer timeType;

    @JsonIgnore
//    @CmsColumn(name="状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;

    @JsonIgnore
//    @CmsColumn(name="每个用户允许领几次")
    Integer getCount;

    @JsonIgnore
    Integer maxCount;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
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

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }
}
