package com.ifenghui.storybookapi.app.transaction.entity.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 单本兑换券
 */
@Entity
@Table(name="story_coupon_story_exchange")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CouponStoryExchange implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String name;

    String content;

    Integer status;


    Integer exceedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExceedTime() {
        return exceedTime;
    }

    public void setExceedTime(Integer exceedTime) {
        this.exceedTime = exceedTime;
    }
}
