package com.ifenghui.storybookapi.app.activity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="story_activity_view_count")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="活动页面浏览次数",addAble = false)
/**
 * 外部网页活动统计网页浏览次数
 */
@Deprecated
public class ActivityViewCount {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer count;


    String storyName;

    String channel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }



}
