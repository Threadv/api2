package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户成长记录
 */
@Entity
@Table(name="story_user_growth_diary")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserGrowthDiary implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String content;

    @JsonIgnore
    Integer userId;

    //区分是否用户添加的测试数据
    Integer isTest;

    @JsonIgnore
    Date createTime;

    Date recordDate;

    @JsonIgnore
    Integer status;

    Integer crossYear;

    @Transient
    Integer birthToRecord;

    Integer width;

    Integer height;

    @JsonIgnore
    Integer monthDate;

    @Transient
    Integer imgSize;

    @JsonIgnore
    String imgPath;

    @JsonProperty("path")
    String getImgPathUrl(){
        return MyEnv.env.getProperty("oss.url")+imgPath;
    }

    @JsonProperty("smallPath")
    String getSmallPathUrl(){
        return MyEnv.env.getProperty("oss.url")+imgPath+MyEnv.env.getProperty("cmsconfig.small.url");
    }

    @Transient
    List<UserGrowthDiaryImg> diaryImgList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public List<UserGrowthDiaryImg> getDiaryImgList() {
        return diaryImgList;
    }

    public void setDiaryImgList(List<UserGrowthDiaryImg> diaryImgList) {
        this.diaryImgList = diaryImgList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCrossYear() {
        return crossYear;
    }

    public void setCrossYear(Integer crossYear) {
        this.crossYear = crossYear;
    }

    public Integer getBirthToRecord() {
        return birthToRecord;
    }

    public void setBirthToRecord(Integer birthToRecord) {
        this.birthToRecord = birthToRecord;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getImgSize() {
        return imgSize;
    }

    public void setImgSize(Integer imgSize) {
        this.imgSize = imgSize;
    }

    public Integer getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(Integer monthDate) {
        this.monthDate = monthDate;
    }
}
