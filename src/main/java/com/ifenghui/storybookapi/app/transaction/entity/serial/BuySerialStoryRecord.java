package com.ifenghui.storybookapi.app.transaction.entity.serial;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 故事集购买记录
 */
@Entity
@Table(name="story_buy_serial_story_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BuySerialStoryRecord implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer serialStoryId;

    Integer isTest;

    Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSerialStoryId() {
        return serialStoryId;
    }

    public void setSerialStoryId(Integer serialStoryId) {
        this.serialStoryId = serialStoryId;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
