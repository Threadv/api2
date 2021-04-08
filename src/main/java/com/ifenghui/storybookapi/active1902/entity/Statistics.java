package com.ifenghui.storybookapi.active1902.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * @Date: 2019/2/19 13:52
 * @Description:
 */
@Entity
@Table(name = "activity1902_statistics")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Statistics {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer  id;

    Integer type;
    Integer userId;
    Integer scheduleId;
    Date createTime;
    String name;

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
