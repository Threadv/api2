package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * 课程视频的介绍表（单独保存课程视频的描述）
 */
@Entity
@Table(name="story_story_video_intro")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoryVideoIntro {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String intro;

    Integer storyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }
}
