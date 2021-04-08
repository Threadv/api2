package com.ifenghui.storybookapi.app.transaction.entity.subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.config.MyEnv;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wml on 2017/2/15.
 */
@Entity
@Table(name="story_pay_subscription_price")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/**
 * 期刊价格
 */
public class PaySubscriptionPrice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    Integer price;

    Integer month;


    Integer maxAmount;

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }


    Integer orderBy;


    Integer status;

    @Transient

    Integer isRecommend;

    String name;

    String icon;
    Date createTime;

    @Transient
    Integer isCanBuy;

    String intro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsRecommend() {
        if(this.getMonth()==12){
            this.isRecommend=1;
        }else{
            this.isRecommend=0;
        }
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return MyEnv.env.getProperty("oss.url")+"icon/"+this.icon;
//        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsCanBuy() {
        if(this.isCanBuy == null){
            this.isCanBuy = 1;
        }
        return isCanBuy;
    }

    public void setIsCanBuy(Integer isCanBuy) {
        this.isCanBuy = isCanBuy;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
