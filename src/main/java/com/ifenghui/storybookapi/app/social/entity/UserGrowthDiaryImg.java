package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户成长记录的小图片表
 */
@Entity
@Table(name="story_user_growth_diary_img")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserGrowthDiaryImg implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    @JsonIgnore
    Integer userId;

    Integer diaryId;

    Integer width;

    Integer height;

    Integer weekNum;

    @JsonProperty("smallPath")
    String getSmallPathUrl(){
        return MyEnv.env.getProperty("oss.url")+imgPath+MyEnv.env.getProperty("cmsconfig.small.url");
    }

    @JsonIgnore
    Date createTime;

    Date recordDate;

    @JsonIgnore
    String imgPath;

    @JsonProperty("path")
    String getImgPathUrl(){
        return MyEnv.env.getProperty("oss.url")+imgPath;
    }

    @JsonIgnore
    Integer status;

    @Transient
    String content;

    Integer isVideo;

    @JsonProperty("shareTitle")
    public String getShareTitle(){
        return "给娃拍了一段趣萌视频，你来看看~";
    }

    @JsonProperty("shareSubTitle")
    public String getShareSubTitle(){
        return "";
    }

    @JsonIgnore
    String videoPath;

    @JsonProperty("videoPath")
    String getVideoPathUrl(){
        return MyEnv.env.getProperty("oss.url")+videoPath;
    }

    @Transient
    @JsonProperty("recordKey")
    public Integer getRecordKey(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String recordDateString = simpleDateFormat.format(this.recordDate);
        Integer recordDateInteger = Integer.parseInt(recordDateString);
        return recordDateInteger;
    }

    @JsonProperty("videoShareUrl")
    String getVideoShareUrl() {
        if(this.isVideo == 1){
            return MyEnv.env.getProperty("local.url") + "api/userGrowthDiary/videoShare.action?videoId=" + id;
        } else {
            return "";
        }
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

    public Integer getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Integer diaryId) {
        this.diaryId = diaryId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Integer isVideo) {
        this.isVideo = isVideo;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
