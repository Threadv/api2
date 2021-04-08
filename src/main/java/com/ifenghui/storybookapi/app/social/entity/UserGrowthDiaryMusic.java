package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户成长记录音乐表
 */
@Entity
@Table(name="story_user_growth_diary_music")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserGrowthDiaryMusic implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String name;

    Integer orderBy;

    @JsonIgnore
    String icon;

    @JsonProperty("iconPath")
    String getIconPathUrl(){
        return MyEnv.env.getProperty("oss.url") + "backgroundMusic/" + icon;
    }

    @JsonIgnore
    String filePath;

    @JsonProperty("filePathUrl")
    String getFilePathUrl(){
        return MyEnv.env.getProperty("oss.url") + "backgroundMusic/" + filePath;
    }

    Integer status;

    Integer duration;

    Date createTime;

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

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
