package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Date: 2018/11/23 16:37
 * @Description:
 */
@Entity
@Table(name = "story_audio_content")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoryAudioContent implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    Integer storyId;
    String content;
    Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getContent() {

        return "\u3000\u3000"+content.replace("\n","\n\u3000\u3000");
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
