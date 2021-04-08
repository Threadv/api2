package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.util.Date;

/**
 * 道具
 * Created by wml on 2017/11/07.
 */
@Entity
@Table(name="story_parts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "道具",intro = "道具")
@Deprecated
//道具不再更新
public class Parts {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    Long storyId;

    String name;
    @JsonIgnore
    String imgPathLight;
    @JsonProperty("imgPathLight")
    String getImgPathLightUrl(){
        return MyEnv.env.getProperty("oss.url")+"medal/"+imgPathLight;
    }

    @JsonIgnore
    String imgPathGrey;
    @JsonProperty("imgPathGrey")
    String getImgPathGreyUrl(){
        return MyEnv.env.getProperty("oss.url")+"medal/"+imgPathGrey;
    }

    String keyName;

    @Transient
    Integer status;//是否已获取

    @JsonIgnore
    Integer orderBy;

    @JsonIgnore
    Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPathLight() {
        return imgPathLight;
    }

    public void setImgPathLight(String imgPathLight) {
        this.imgPathLight = imgPathLight;
    }

    public String getImgPathGrey() {
        return imgPathGrey;
    }

    public void setImgPathGrey(String imgPathGrey) {
        this.imgPathGrey = imgPathGrey;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
