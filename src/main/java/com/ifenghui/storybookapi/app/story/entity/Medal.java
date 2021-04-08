package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * SerialStory 每日任务
 * Created by wml on 2017/11/07.
 */
@Entity
@Table(name="story_medal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "每日任务",intro = "每日任务")
@Deprecated
//勋章不再更新
public class Medal {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    Long storyId;

    String name;

    @JsonIgnore
    String imgPath;

    @JsonProperty("imgPath")
    String getImgPathUrl(){
        return MyEnv.env.getProperty("oss.url")+"medal/"+imgPath;
    }
    @JsonIgnore
    String filePath;
    @JsonProperty("filePath")
    String getFilePathUrl(){
        return MyEnv.env.getProperty("oss.url")+"medal/"+filePath;
    }

    @JsonIgnore
    Integer status;//是1否0

    @Transient
    Integer isCollect;

    @JsonIgnore
    Integer orderBy;

    @JsonIgnore
    Date createTime;

    @Transient
    List<Parts> partsList;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }
}
