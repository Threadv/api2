package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.util.Date;

/**
 * 课程中的视频介绍表
 */
@Entity
@Table(name="story_lesson_study_video")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LessonStudyVideo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String name;

    @JsonIgnore
    String imgPath;

    @JsonProperty("imgPath")
    String getImgPathUrl(){
        return MyEnv.env.getProperty("oss.url") + "lesson/" + imgPath;
    }

    @JsonIgnore
    String videoPath;

    @JsonProperty("videoPath")
    String getVideoPathUrl(){
        return MyEnv.env.getProperty("oss.url") + "lesson/" + videoPath;
    }

    @JsonIgnore
    String content;

    Integer status;

    Date createTime;

    @JsonIgnore
    Integer type;

    @JsonProperty("introUrl")
    String getIntroUrl(){
        return MyEnv.env.getProperty("cmsconfig.admin.url") + "/editor/lessonstudyvideo/pagemake.html?id=" + id;
    }

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
